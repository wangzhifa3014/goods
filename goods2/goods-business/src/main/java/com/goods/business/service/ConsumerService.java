package com.goods.business.service;

import com.goods.common.model.business.Consumer;
import com.goods.common.vo.business.ConsumerVO;
import com.goods.common.vo.system.PageVO;

import java.util.List;

public interface ConsumerService {
    PageVO<Consumer> findConsumerList(Integer pageNum, Integer pageSize, ConsumerVO consumerVO);

    void add(Consumer consumer);

    void save(Consumer consumer);

    Consumer edit(Long consumerId);

    void delete(Long consumerId);

    List<Consumer> findAll();
}
