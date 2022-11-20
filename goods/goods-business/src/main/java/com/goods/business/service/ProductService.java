package com.goods.business.service;

import com.goods.common.model.business.Product;
import com.goods.common.vo.business.ProductStockVO;
import com.goods.common.vo.business.ProductVO;
import com.goods.common.vo.system.PageVO;

import java.util.Map;

/**
 * @author: ProductService
 * @Desc:
 * @create: 2022-11-19 15:58
 **/
public interface ProductService {
    PageVO<Product> findProductList(Integer pageNum, Integer pageSize, ProductVO productVO);

    void add(ProductVO productVO);

    void delete(Long id);

    void save(Product product);

    Product getById(Long id);

    /**
     * 更新物资状态
     * @param id
     */
    void publish(Long id);

    void updateStutas(Long id,Integer status);


    public PageVO<ProductStockVO> findAllStocks(Integer pageNum, Integer pageSize, ProductVO productVO);

}
