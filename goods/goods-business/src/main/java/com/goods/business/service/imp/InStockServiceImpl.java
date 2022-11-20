package com.goods.business.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.mapper.InStockInfoMapper;
import com.goods.business.mapper.InStockMapper;
import com.goods.business.mapper.ProductMapper;
import com.goods.business.mapper.SupplierMapper;
import com.goods.business.service.InStockService;
import com.goods.common.model.business.*;
import com.goods.common.vo.business.*;
import com.goods.common.vo.system.PageVO;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: InStockServiceImpl
 * @Desc:
 * @create: 2022-11-19 17:10
 **/
@Service
public class InStockServiceImpl implements InStockService {
    @Autowired
    private InStockMapper inStockMapper;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private InStockInfoMapper inStockInfoMapper;
    @Autowired
    private ProductMapper productMapper;

    /**
     * status=0&type=1&inNum=22&startTime=2022-11-14 00:00:00&endTime=2022-12-21 00:00:00
     *
     * @param pageNum
     * @param pageSize
     * @param inStockVO
     * @return
     */
    @Override
    public PageVO<InStockVO> findInStockList(Integer pageNum, Integer pageSize, InStockVO inStockVO) {
        PageHelper.startPage(pageNum, pageSize);

        Example o=new Example(InStock.class);
//            o.createCriteria().andEqualTo("status", inStockVO.getStatus());
//        }
//        if (inStockVO.getType()!=null) {
//            o.createCriteria().andEqualTo("type", inStockVO.getType());
//        }
//        if (!StringUtils.isEmpty(inStockVO.getInNum())) {
//            o.createCriteria().andLike("in_num", "%"+inStockVO.getInNum()+"%");
//        }
//        if(inStockVO.getStartTime()!=null)
//        {
//            System.out.println(1);
//        }
        Map<String, Object> map = new HashMap<>();
        if(inStockVO!=null) {
            Integer status = inStockVO.getStatus();
            Integer type = inStockVO.getType();
            String inNum = inStockVO.getInNum();
            Date startTime = inStockVO.getStartTime();
            Date endTime = inStockVO.getEndTime();
            map.put("status", status);
            map.put("type", type);
            map.put("inNum", inNum);
            map.put("startTime", startTime);
            map.put("endTime", endTime);
        }
        List<InStock> inStocks = inStockMapper.findInStockList(map);
        List<InStockVO> inStockVOS = converterInStockVO(inStocks);

        PageInfo<InStock> info = new PageInfo<>(inStocks);
        return new PageVO<>(info.getTotal(), inStockVOS);
    }

    public void packegProductMap(List<InStockVO> inStockVOS) {
        for (InStockVO inStockVOdate : inStockVOS) {
            List<InStockItemVO> inStockItemVOS = inStockInfoMapper.GetInStockItemVO(inStockVOdate.getInNum());
            List<Object> collect = inStockItemVOS.stream().map(inStockItemVO -> {
                Map<String, Object> ProductsMap = new HashMap<>();
                ProductsMap.put("name", inStockItemVO.getName());
                ProductsMap.put("stock", inStockItemVO.getCount());
                return (Object) ProductsMap;
            }).collect(Collectors.toList());
            inStockVOdate.setProducts(collect);
        }
    }

    public List<InStockVO> converterInStockVO( List<InStock> inStocks)
    {
        List<InStockVO> inStockVOS=new ArrayList<>();
        for (InStock inStock : inStocks) {
            InStockVO inStockVO = converterStockVO(inStock);
            inStockVOS.add(inStockVO);
        }
        return inStockVOS;
    }

    public InStockVO converterStockVO(InStock inStock) {
        InStockVO inStockVO=new InStockVO();
        BeanUtils.copyProperties(inStock,inStockVO);
        if(inStockVO.getSupplierId()!=null)
        {
            Supplier supplier = supplierMapper.selectByPrimaryKey(inStockVO.getSupplierId());
            inStockVO.setSupplierName(supplier.getName());
            inStockVO.setAddress(supplier.getAddress());
            inStockVO.setEmail(supplier.getEmail());
            inStockVO.setPhone(supplier.getPhone());
            inStockVO.setSort(supplier.getSort());
            inStockVO.setContact(supplier.getContact());

        }
        return inStockVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addIntoStock(InStockVO inStockVO) {
        InStock inStock = new InStock();
        Supplier supplier = null;
        BeanUtils.copyProperties(inStockVO, inStock);
        if (inStockVO.getSupplierId() == null) {
            supplier = new Supplier();
            BeanUtils.copyProperties(inStockVO, supplier);
            supplier.setId(null);
            supplier.setModifiedTime(new Date());
            supplier.setCreateTime(new Date());
            supplierMapper.insert(supplier);
            inStock.setSupplierId(supplier.getId());
        }
        inStock.setStatus(2);
        inStock.setCreateTime(new Date());
        inStock.setModified(new Date());
        inStock.setInNum(UUID.randomUUID().toString().replace("-", ""));

        int i=0;
        List<Object> products = inStockVO.getProducts();
        for (Object product : products) {
            LinkedHashMap item = (LinkedHashMap) product;
            Product primaryKey = productMapper.selectByPrimaryKey(item.get("productId"));
            InStockInfo inStockInfo=new InStockInfo();
            inStockInfo.setPNum(primaryKey.getPNum());
            inStockInfo.setInNum(inStock.getInNum());
            i=i+(Integer) item.get("productNumber");
            inStockInfo.setProductNumber((Integer) item.get("productNumber"));
            inStockInfo.setCreateTime(new Date());
            inStockInfo.setModifiedTime(new Date());
            inStockInfoMapper.insert(inStockInfo);
        }
        inStock.setProductNumber(i);
        inStockMapper.insert(inStock);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void MyaddIntoStock(InStockVO inStockVO) {
        InStock inStock = new InStock();
        Supplier supplier = null;
        BeanUtils.copyProperties(inStockVO, inStock);
        if (inStockVO.getSupplierId() == null) {
            supplier = new Supplier();
            BeanUtils.copyProperties(inStockVO, supplier);
            supplier.setId(null);
            supplier.setModifiedTime(new Date());
            supplier.setCreateTime(new Date());
            supplierMapper.insert(supplier);
            inStock.setSupplierId(supplier.getId());
        }
        inStock.setStatus(2);
        inStock.setCreateTime(new Date());
        inStock.setModified(new Date());
        inStock.setInNum(UUID.randomUUID().toString().replace("-", ""));

        int i=0;
        List<Object> products = inStockVO.getProducts();
        for (Object product : products) {
            InStockInfo inStockInfo=new InStockInfo();
                LinkedHashMap map = (LinkedHashMap) product;
                Product primaryKey = productMapper.selectByPrimaryKey(map.get("productId"));
                inStockInfo.setPNum(primaryKey.getPNum());
                Integer productNumber=(Integer) map.get("productNumber");
                inStockInfo.setProductNumber(productNumber);
                i=i+productNumber;

            inStockInfo.setInNum(inStock.getInNum());
            inStockInfo.setCreateTime(new Date());
            inStockInfo.setModifiedTime(new Date());
            inStockInfoMapper.insert(inStockInfo);
        }
        inStock.setProductNumber(i);
        inStockMapper.insert(inStock);
    }

    @Override
    public InStockDetailVO detail(Long inStockId, Integer pageNum) {
        InStockDetailVO inStockDetailVO=new InStockDetailVO();
        InStock inStock = inStockMapper.selectByPrimaryKey(inStockId);
        if(inStock!=null) {
            BeanUtils.copyProperties(inStock, inStockDetailVO);
            Supplier supplier = supplierMapper.selectByPrimaryKey(inStock.getSupplierId());
            SupplierVO supplierVO=new SupplierVO();
            BeanUtils.copyProperties(supplier,supplierVO);
            inStockDetailVO.setSupplierVO(supplierVO);
//            Example example=new Example(InStockInfo.class);
//            List<InStockInfo> inStockInfos = inStockInfoMapper.selectByExample(example);
            String inNum = inStock.getInNum();
            PageHelper.startPage(pageNum,3);
            List<InStockItemVO> inStockItemVOS = inStockInfoMapper.GetInStockItemVO(inNum);
            PageInfo<InStockItemVO> info = new PageInfo<>(inStockItemVOS);
            inStockDetailVO.setItemVOS(inStockItemVOS);
            inStockDetailVO.setTotal(info.getTotal());

        }


        return inStockDetailVO;
    }

    @Override
    public void inStockStutasUpdate(Long inStockId, Integer stutas) {
        InStock inStock = new InStock();
        inStock.setStatus(stutas);
        inStock.setId(inStockId);
        inStockMapper.updateByPrimaryKeySelective(inStock);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInStock(Long inStockId) {
        InStock inStock = inStockMapper.selectByPrimaryKey(inStockId);
        String inNum = inStock.getInNum();
        Example example=new Example(InStockInfo.class);
        example.createCriteria().andLike("inNum",inNum);
        inStockInfoMapper.deleteByExample(example);
        inStockMapper.deleteByPrimaryKey(inStockId);
    }




    /**
     * 用于把List&lt;Object>转换成Map&lt;String,Object>形式，便于存入缓存
     *
     * @param keyName 主键属性
     * @param list    集合
     * @return 返回对象
     * @author zhang_bo
     */
    private <T> Map<String, T> listToMap(String keyName, List<T> list) {
        Map<String, T> m = new HashMap<String, T>();
        try {
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(keyName,
                        t.getClass());
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o = getMethod.invoke(t);// 执行get方法返回一个Object
                m.put(o.toString(), t);
            }
            return m;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

}