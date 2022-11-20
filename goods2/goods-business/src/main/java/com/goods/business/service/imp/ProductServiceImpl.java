package com.goods.business.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.mapper.ProductMapper;
import com.goods.business.mapper.ProductStockMapper;
import com.goods.business.service.ProductService;
import com.goods.common.model.business.Product;
import com.goods.common.vo.business.ProductStockVO;
import com.goods.common.vo.business.ProductVO;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author: ProductServiceImpl
 * @Desc:
 * @create: 2022-11-19 15:58
 **/
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductStockMapper productStockMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageVO<Product> findProductList(Integer pageNum, Integer pageSize,ProductVO productVO) {
        PageHelper.startPage(pageNum, pageSize);

        Example o = new Example(Product.class);
        Example.Criteria criteria = o.createCriteria();
        if (!StringUtils.isEmpty(productVO.getName())) {
            criteria.andLike("name", "%" + productVO.getName() + "%");
        }
        if (!StringUtils.isEmpty(productVO.getStatus())) {
            criteria.andEqualTo("status", productVO.getStatus());
        }
         Long[] categoryKeys = productVO.getCategoryKeys();

        if (categoryKeys!= null&&categoryKeys.length>0) {
            criteria.andEqualTo("oneCategoryId", categoryKeys[0]);
            if (categoryKeys.length>1&&categoryKeys[1] != null) {
                criteria.andEqualTo("twoCategoryId", categoryKeys[1]);
                if (categoryKeys.length>2&&categoryKeys[2] != null) {
                    criteria.andEqualTo("threeCategoryId", categoryKeys[2]);
                }
            }
        }
        List<Product> products = productMapper.selectByExample(o);
        PageInfo<Product> info = new PageInfo<>(products);
        return new PageVO<>(info.getTotal(), products);
    }

    @Override
    public void add(ProductVO productVO) {
        Product product=new Product();
        BeanUtils.copyProperties(productVO,product);
        String uuId = UUID.randomUUID().toString();
        product.setPNum(uuId);
        Long[] categoryKeys = productVO.getCategoryKeys();
        product.setStatus(2);
        product.setOneCategoryId(categoryKeys[0]);
        product.setTwoCategoryId(categoryKeys[1]);
        product.setThreeCategoryId(categoryKeys[2]);
        Date date=new Date();
        product.setCreateTime(date);
        product.setModifiedTime(date);
        productMapper.insert(product);
    }

    @Override
    public void delete(Long id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Product product) {
        product.setModifiedTime(new Date());
        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public Product getById(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public void publish(Long id) {
        updateStutas(id,0);
    }

    @Override
    public void updateStutas(Long id, Integer status) {
        Product product=getById(id);
        product.setStatus(status);
        product.setModifiedTime(new Date());
        productMapper.updateByPrimaryKey(product);
    }
    @Override
    public PageVO<ProductStockVO> findAllStocks(Integer pageNum, Integer pageSize, ProductVO productVO) {

        PageHelper.startPage(pageNum,pageSize);
        Map<String,Object> map=new HashMap<>();
        if (productVO!= null) {
            if(!StringUtils.isEmpty(productVO.getName()))
            {
                map.put("name",productVO.getName());
            }
            Long[] categoryKeys = productVO.getCategoryKeys();
            if (categoryKeys!= null&&categoryKeys.length>0) {
                map.put("oneCategoryId", categoryKeys[0]);
                if (categoryKeys.length>1&&categoryKeys[1] != null) {
                    map.put("twoCategoryId", categoryKeys[1]);
                    if (categoryKeys.length>2&&categoryKeys[2] != null) {
                        map.put("threeCategoryId", categoryKeys[2]);
                    }

                }
            }
        }
        List<ProductStockVO> productStockVOS = productMapper.findAllStocks(map);
        PageInfo<ProductStockVO> info = new PageInfo<>(productStockVOS);
        return new PageVO<>(info.getTotal(), productStockVOS);
    }


}
