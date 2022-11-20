package com.goods.controller.business;

import com.goods.business.service.ConsumerService;
import com.goods.common.model.business.Consumer;
import com.goods.common.response.ResponseBean;
import com.goods.common.vo.business.ConsumerVO;
import com.goods.common.vo.system.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: ConsumerController
 * @Desc:
 * @create: 2022-11-20 15:59
 **/
@Api(tags = "物资去处")
@RestController
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    @ApiOperation("查询去处")
    @GetMapping("business/consumer/findConsumerList")
    public ResponseBean findConsumerList(Integer pageNum, Integer pageSize, ConsumerVO consumerVO) {
        PageVO<Consumer> consumerVOPageVO = consumerService.findConsumerList(pageNum, pageSize, consumerVO);
        return ResponseBean.success(consumerVOPageVO);
    }
    @ApiOperation("去处添加")
    @PostMapping("/consumer/add")
    public ResponseBean add(@RequestBody Consumer consumer) {
        consumerService.add(consumer);
        return ResponseBean.success();
    }
    @ApiOperation("去处修改 后保存")
    @PutMapping("business/consumer/update/{ConsumerId}")
    public ResponseBean update(@PathVariable Long ConsumerId,@RequestBody Consumer consumer) {
        consumerService.save(consumer);
        return ResponseBean.success();
    }
    @ApiOperation("去处修改")
    @GetMapping("business/consumer/edit/{ConsumerId}")
    public ResponseBean edit(@PathVariable Long ConsumerId) {
        Consumer consumer=consumerService.edit(ConsumerId);
        return ResponseBean.success(consumer);
    }
    @ApiOperation("去处删除")
    @DeleteMapping("business/consumer/delete/{ConsumerId}")
    public ResponseBean delete(@PathVariable Long ConsumerId) {
        consumerService.delete(ConsumerId);
        return ResponseBean.success();
    }
    @ApiOperation("去处所有地址")
    @GetMapping("business/consumer/findAll")
    public ResponseBean findAll() {
        List<Consumer> consumerList =consumerService.findAll();
        return ResponseBean.success(consumerList);
    }
}
