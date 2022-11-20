package com.goods.business.mapper;

import com.goods.common.model.business.InStock;
import com.goods.common.vo.business.InStockVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface InStockMapper extends Mapper<InStock> {
    List<InStock> findInStockList(Map<String,Object> map);
}
