package com.goods.business.service;

import com.goods.common.model.business.ProductCategory;
import com.goods.common.model.business.Supplier;
import com.goods.common.vo.business.SupplierVO;
import com.goods.common.vo.system.PageVO;

import java.util.List;

/**
 * @author: SupplierService
 * @Desc:
 * @create: 2022-11-19 14:55
 **/
public interface SupplierService {
    PageVO<Supplier> findSupplierList(Integer pageNum, Integer pageSize, SupplierVO supplierVO);

    /**
     * 添加物资来源
     * @param supplier
     */
    void add(Supplier supplier);

    /**
     * 删除物资来源
     * @param id
     */
    void delete(Long id);

    /**
     * 保存物资来源
     * @param supplier
     */
    void save(Supplier supplier);


    Supplier getById(Long id);

    /**
     * 获取所有
     * @return
     */
    List<Supplier> findAll();
}
