package com.goods.business.mapper;

import com.goods.common.model.business.OutStockInfo;
import com.goods.common.vo.business.OutStockItemVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: OutStockInfoMapper
 * @Desc:
 * @create: 2022-11-20 17:17
 **/
public interface OutStockInfoMapper extends Mapper<OutStockInfo> {
    List<OutStockItemVO> GetOutStockItemVO(String outNum);
}
