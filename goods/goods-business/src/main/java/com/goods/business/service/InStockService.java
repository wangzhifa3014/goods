package com.goods.business.service;

import com.goods.common.model.business.InStock;
import com.goods.common.vo.business.InStockDetailVO;
import com.goods.common.vo.business.InStockVO;
import com.goods.common.vo.business.ProductVO;
import com.goods.common.vo.system.PageVO;

import java.util.Map;

/**
 * @author: InStockService
 * @Desc:
 * @create: 2022-11-19 17:10
 **/
public interface InStockService {
    PageVO<InStockVO> findInStockList(Integer pageNum, Integer pageSize, InStockVO inStockVO);

    void addIntoStock(InStockVO inStockVO);

    void MyaddIntoStock(InStockVO inStockVO);

    InStockDetailVO detail(Long inStockId, Integer pageNum);


    void inStockStutasUpdate(Long inStockId, Integer stutas);

    void deleteInStock(Long inStockId);
}
