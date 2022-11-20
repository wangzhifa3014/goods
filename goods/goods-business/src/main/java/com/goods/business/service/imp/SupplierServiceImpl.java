package com.goods.business.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.mapper.SupplierMapper;
import com.goods.business.service.SupplierService;
import com.goods.common.model.business.ProductCategory;
import com.goods.common.model.business.Supplier;
import com.goods.common.vo.business.ProductCategoryTreeNodeVO;
import com.goods.common.vo.business.SupplierVO;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author: SupplierServiceImpl
 * @Desc:
 * @create: 2022-11-19 14:55
 **/
@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public PageVO<Supplier> findSupplierList(Integer pageNum, Integer pageSize, SupplierVO supplierVO) {
        PageHelper.startPage(pageNum, pageSize);
        Example o = new Example(Supplier.class);
        Example.Criteria criteria = o.createCriteria();
        if (!StringUtils.isEmpty(supplierVO.getName())) {
            criteria.andLike("name", "%"+supplierVO.getName()+"%");
        }
        if (!StringUtils.isEmpty(supplierVO.getContact())) {
            criteria.andLike("contact", "%"+supplierVO.getContact()+"%");
        }
        if (!StringUtils.isEmpty(supplierVO.getAddress())) {
            criteria.andLike("address", "%"+supplierVO.getAddress()+"%");
        }
        List<Supplier> suppliers = supplierMapper.selectByExample(o);
        PageInfo<Supplier> info = new PageInfo<>(suppliers);
        return new PageVO<>(info.getTotal(), suppliers);
    }

    @Override
    public void add(Supplier supplier) {
        Date date=new Date();
        supplier.setCreateTime(date);
        supplier.setModifiedTime(date);
        supplierMapper.insert(supplier);
    }

    @Override
    public void delete(Long id) {
        supplierMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Supplier supplier) {
        Date date=new Date();
        supplier.setModifiedTime(date);
        supplierMapper.updateByPrimaryKey(supplier);
    }

    @Override
    public Supplier getById(Long id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Supplier> findAll() {
        return supplierMapper.selectAll();
    }
}
