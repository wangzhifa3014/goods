package com.goods.business.mapper;

import com.goods.common.model.business.Product;
import com.goods.common.vo.business.ProductStockVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author: ProductMapper
 * @Desc:
 * @create: 2022-11-19 15:59
 **/
public interface ProductMapper extends Mapper<Product> {
    List<ProductStockVO> findAllStocks(Map<String, Object> map);
}
