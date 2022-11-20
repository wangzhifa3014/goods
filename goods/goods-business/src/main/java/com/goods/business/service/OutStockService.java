package com.goods.business.service;

import com.goods.common.vo.business.OutStockDetailVO;
import com.goods.common.vo.business.OutStockVO;
import com.goods.common.vo.system.PageVO;

public interface OutStockService {
    PageVO<OutStockVO> findOutStockList(Integer pageNum, Integer pageSize, OutStockVO outStockVO);

    void addOutStock(OutStockVO outStockVO);

    OutStockDetailVO detail(Long outStockId, Integer pageNum);

    void outStockStutasUpdate(Long inStockId, Integer stutas);

    void deleteOutStock(Long inStockId);
}
