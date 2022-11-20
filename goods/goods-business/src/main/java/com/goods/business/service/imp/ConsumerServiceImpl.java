package com.goods.business.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.mapper.ConsumerMapper;
import com.goods.business.service.ConsumerService;
import com.goods.common.model.business.Consumer;
import com.goods.common.vo.business.ConsumerVO;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author: ConsumerServiceImpl
 * @Desc:
 * @create: 2022-11-20 16:03
 **/
@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private ConsumerMapper consumerMapper;
    @Override
    public PageVO<Consumer> findConsumerList(Integer pageNum, Integer pageSize, ConsumerVO consumerVO) {
        PageHelper.startPage(pageNum,pageSize);
        Example example=new Example(Consumer.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(consumerVO.getAddress()))
        {
            criteria.andLike("address",consumerVO.getAddress());
        }        if(!StringUtils.isEmpty(consumerVO.getAddress()))
        {
            criteria.andLike("name",consumerVO.getName());
        }        if(!StringUtils.isEmpty(consumerVO.getPhone()))
        {
            criteria.andLike("phone",consumerVO.getPhone());
        }
        List<Consumer> consumers = consumerMapper.selectByExample(example);
        PageInfo<Consumer> info = new PageInfo<>(consumers);
        return new PageVO<>(info.getTotal(), consumers);

    }

    @Override
    public void add(Consumer consumer) {
        consumer.setCreateTime(new Date());
        consumer.setModifiedTime(new Date());
        consumerMapper.insert(consumer);
    }

    @Override
    public void save(Consumer consumer) {
        consumerMapper.updateByPrimaryKeySelective(consumer);
    }

    @Override
    public Consumer edit(Long consumerId) {
        return consumerMapper.selectByPrimaryKey(consumerId);
    }

    @Override
    public void delete(Long consumerId) {
        consumerMapper.deleteByPrimaryKey(consumerId);
    }

    @Override
    public  List<Consumer> findAll() {
        List<Consumer> consumers = consumerMapper.selectAll();
        return consumers;
    }
}
