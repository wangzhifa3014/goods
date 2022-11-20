package com.goods.controller.business;

import com.goods.business.service.OutStockService;
import com.goods.common.response.ResponseBean;
import com.goods.common.vo.business.OutStockDetailVO;
import com.goods.common.vo.business.OutStockVO;
import com.goods.common.vo.system.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: OutStockController
 * @Desc:
 * @create: 2022-11-20 16:38
 **/
@Api(tags = "发放记录")
@RestController
@RequestMapping(value = "business/outStock/")
public class OutStockController {
    @Autowired
    private OutStockService outStockService;

    @ApiOperation(value = "发放记录查询")
    @GetMapping("/findOutStockList")
    public ResponseBean findOutStockList(Integer pageNum, Integer pageSize, OutStockVO outStockVO) {
        PageVO<OutStockVO> outStockVOPageVO = outStockService.findOutStockList(pageNum, pageSize, outStockVO);
        return ResponseBean.success(outStockVOPageVO);
    }
    @ApiOperation(value = "发放记录添加")
    @PostMapping("/addOutStock")
    public ResponseBean addOutStock(@RequestBody OutStockVO outStockVO) {
        outStockService.addOutStock(outStockVO);
        return ResponseBean.success();
    }
    @ApiOperation(value = "发放记录明细")
    @GetMapping("detail/{outStockId}")
    public ResponseBean detail(@PathVariable Long outStockId,Integer pageNum) {
        OutStockDetailVO outStockDetailVO=outStockService.detail(outStockId,pageNum);
        return ResponseBean.success(outStockDetailVO);
    }
    @ApiOperation(value = "发放记录 审核通过")
    @PutMapping("/publish/{inStockId}")
    public ResponseBean publish(@PathVariable Long inStockId) {
        outStockService.outStockStutasUpdate(inStockId, 0);
        return ResponseBean.success();
    }
    @ApiOperation(value = "发放记录 移入回收站")
    @PutMapping("/remove/{inStockId}")
    public ResponseBean remove(@PathVariable Long inStockId) {
        outStockService.outStockStutasUpdate(inStockId, 1);
        return ResponseBean.success();
    }
    @ApiOperation(value = "发放记录 还原回收站")
    @PutMapping("/back/{inStockId}")
    public ResponseBean back(@PathVariable Long inStockId) {
        outStockService.outStockStutasUpdate(inStockId, 0);
        return ResponseBean.success();
    }
    @ApiOperation(value = "发放记录 删除")
    @GetMapping("/delete/{inStockId}")
    public ResponseBean delete(@PathVariable Long inStockId) {
        outStockService.deleteOutStock(inStockId);
        return ResponseBean.success();
    }

}
