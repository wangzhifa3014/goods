package com.goods.business.mapper;

import com.goods.common.model.business.InStockInfo;
import com.goods.common.vo.business.InStockItemVO;
import com.goods.common.vo.business.InStockVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;


public interface InStockInfoMapper extends Mapper<InStockInfo> {
    List<InStockItemVO>  GetInStockItemVO(String inNum);

    List<Map<String, Object>> findAllStocks(Map<String, Object> map);
}
