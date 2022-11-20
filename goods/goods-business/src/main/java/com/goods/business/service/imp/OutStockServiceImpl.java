package com.goods.business.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.mapper.ConsumerMapper;
import com.goods.business.mapper.OutStockInfoMapper;
import com.goods.business.mapper.OutStockMapper;
import com.goods.business.mapper.ProductMapper;
import com.goods.business.service.OutStockService;
import com.goods.common.model.business.*;
import com.goods.common.vo.business.*;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author: OutStockServiceImpl
 * @Desc:
 * @create: 2022-11-20 16:43
 **/
@Service
public class OutStockServiceImpl implements OutStockService {
    @Autowired
    private OutStockMapper outStockMapper;
    @Autowired
    private ConsumerMapper consumerMapper;
    @Autowired
    private OutStockInfoMapper outStockInfoMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public PageVO<OutStockVO> findOutStockList(Integer pageNum, Integer pageSize, OutStockVO outStockVO) {
        PageHelper.startPage(pageNum,pageSize);

        Example example=new Example(OutStock.class);
        Example.Criteria criteria = example.createCriteria();
        if(outStockVO!=null)
        {
            if(!StringUtils.isEmpty(outStockVO.getOutNum()))
            {
                criteria.andLike("outNum",outStockVO.getOutNum());
            }
            if(outStockVO.getStatus()!=null)
            {
                criteria.andEqualTo("status",outStockVO.getStatus());
            }
            if(outStockVO.getType()!=null)
            {
                criteria.andEqualTo("type",outStockVO.getType());
            }
        }


        List<OutStock> outStocks = outStockMapper.selectByExample(example);
        List<OutStockVO> collect = outStocks.stream().map(outStock -> {
            OutStockVO outStockVOData = new OutStockVO();
            Long consumerId = outStock.getConsumerId();
            BeanUtils.copyProperties(outStock, outStockVOData);
            Consumer consumer = consumerMapper.selectByPrimaryKey(consumerId);
            outStockVOData.setConsumerId(consumer.getId());
            outStockVOData.setName(consumer.getName());
            outStockVOData.setAddress(consumer.getAddress());
            outStockVOData.setPhone(consumer.getPhone());
            outStockVOData.setContact(consumer.getContact());
            outStockVOData.setSort(consumer.getSort());
            return outStockVOData;
        }).collect(Collectors.toList());

        PageInfo<OutStock> info = new PageInfo<>(outStocks);
        return new PageVO<>(info.getTotal(), collect);
    }

    @Override
    public void addOutStock(OutStockVO outStockVO) {
        OutStock outStock = new OutStock();
        Consumer consumer =null;
        BeanUtils.copyProperties(outStockVO, outStock);
        if (outStock.getConsumerId() == null) {
            consumer=new Consumer();
            BeanUtils.copyProperties(outStockVO, consumer);
            consumer.setId(null);
            consumer.setModifiedTime(new Date());
            consumer.setCreateTime(new Date());
            consumerMapper.insert(consumer);
            outStock.setConsumerId(consumer.getId());
        }
        outStock.setStatus(2);
        outStock.setCreateTime(new Date());
        outStock.setOutNum(UUID.randomUUID().toString().replace("-", ""));

        int i=0;
        List<Object> products = outStockVO.getProducts();
        for (Object product : products) {
            LinkedHashMap item = (LinkedHashMap) product;
            Product primaryKey = productMapper.selectByPrimaryKey(item.get("productId"));
            OutStockInfo outStockInfo=new OutStockInfo();
            outStockInfo.setPNum(primaryKey.getPNum());
            outStockInfo.setOutNum(outStock.getOutNum());
            i=i+(Integer) item.get("productNumber");
            outStockInfo.setProductNumber((Integer) item.get("productNumber"));
            outStockInfo.setCreateTime(new Date());
            outStockInfo.setModifiedTime(new Date());
            outStockInfoMapper.insert(outStockInfo);
        }
        outStock.setProductNumber(i);
        outStockMapper.insert(outStock);
    }

    @Override
    public OutStockDetailVO detail(Long outStockId, Integer pageNum) {
        OutStockDetailVO outStockDetailVO=new OutStockDetailVO();
        OutStock outStock = outStockMapper.selectByPrimaryKey(outStockId);
        if(outStock!=null) {
            BeanUtils.copyProperties(outStock, outStockDetailVO);
            Consumer consumer = consumerMapper.selectByPrimaryKey(outStock.getConsumerId());
            ConsumerVO consumerVO=new ConsumerVO();
            BeanUtils.copyProperties(consumer,consumerVO);
            outStockDetailVO.setConsumerVO(consumerVO);
            String outNum = outStock.getOutNum();
            PageHelper.startPage(pageNum,3);
            List<OutStockItemVO> inStockItemVOS = outStockInfoMapper.GetOutStockItemVO(outNum);
            PageInfo<OutStockItemVO> info = new PageInfo<>(inStockItemVOS);
            outStockDetailVO.setItemVOS(inStockItemVOS);
            outStockDetailVO.setTotal(info.getTotal());

        }
        return outStockDetailVO;

    }

    @Override
    public void outStockStutasUpdate(Long inStockId, Integer stutas) {
        OutStock outStock=new OutStock();
        outStock.setId(inStockId);
        outStock.setStatus(stutas);
        outStockMapper.updateByPrimaryKeySelective(outStock);
    }

    @Override
    public void deleteOutStock(Long inStockId) {
        outStockMapper.deleteByPrimaryKey(inStockId);
    }

}
