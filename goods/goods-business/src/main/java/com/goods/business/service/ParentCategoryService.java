package com.goods.business.service;

import com.goods.common.model.business.ProductCategory;
import com.goods.common.vo.business.ProductCategoryTreeNodeVO;
import com.goods.common.vo.system.PageVO;

import java.util.List;

public interface ParentCategoryService {
     /**
      * 0L 获取所有物资分类
      * @param pageNum
      * @param pageSize
      * @return
      */
     PageVO<ProductCategoryTreeNodeVO> categoryTree(Integer pageNum, Integer pageSize);

     /**
      * 根据父类ID查询物资分类
      * @param pid
      * @return
      */
     List<ProductCategory> getByPidCategoryTree(Long pid);

     /**
      * 获取所有物资分类
      * @return
      */
     List<ProductCategoryTreeNodeVO> getParentCategoryTree();

     /**
      * 添加
      * @param productCategory
      */
     void add(ProductCategory productCategory);

     /**
      * 删除
      * @param id
      */
     void delete(Long id);

     /**
      *
      * @param id
      * @return
      */
     ProductCategory getById(Long id);

     /**
      * 保存
      * @param productCategory
      */
     void save(ProductCategory productCategory);
}
