package com.goods.controller.business;

import com.goods.business.service.InStockService;
import com.goods.common.model.business.InStock;
import com.goods.common.response.ResponseBean;
import com.goods.common.utils.JWTUtils;
import com.goods.common.vo.business.InStockDetailVO;
import com.goods.common.vo.business.InStockVO;
import com.goods.common.vo.system.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: InStockController
 * @Desc:
 * @create: 2022-11-19 17:09
 **/
@Api(tags = "物资入库")
@RestController
@RequestMapping(value = "business/inStock/")
public class InStockController {
    @Autowired
    private InStockService inStockService;

    @ApiOperation(value = "物资入库条件查询")
    @GetMapping("/findInStockList")
    public ResponseBean findInStockList(Integer pageNum, Integer pageSize, InStockVO inStockVO) {
        PageVO<InStockVO> inStockPageVO = inStockService.findInStockList(pageNum, pageSize, inStockVO);

        return ResponseBean.success(inStockPageVO);
    }
    @ApiOperation(value = "物资入库添加")
    @PostMapping("/addIntoStock")
    public ResponseBean addIntoStock(@RequestBody InStockVO inStockVO) {
        inStockService.addIntoStock(inStockVO);
        return ResponseBean.success();
    }
    @ApiOperation(value = "物资入库明细")
    @GetMapping("/detail/{inStockId}")
    public ResponseBean detail(@PathVariable Long inStockId, Integer pageNum) {
        InStockDetailVO inStockDetailVO = inStockService.detail(inStockId, pageNum);
        return ResponseBean.success(inStockDetailVO);
    }
    @ApiOperation(value = "物资入库 审核通过")
    @PutMapping("/publish/{inStockId}")
    public ResponseBean publish(@PathVariable Long inStockId) {
         inStockService.inStockStutasUpdate(inStockId, 0);
        return ResponseBean.success();
    }
    @ApiOperation(value = "物资入库 移入回收站")
    @PutMapping("/remove/{inStockId}")
    public ResponseBean remove(@PathVariable Long inStockId) {
         inStockService.inStockStutasUpdate(inStockId, 1);
        return ResponseBean.success();
    }
    @ApiOperation(value = "物资入库 还原回收站")
    @PutMapping("/back/{inStockId}")
    public ResponseBean back(@PathVariable Long inStockId) {
         inStockService.inStockStutasUpdate(inStockId, 0);
        return ResponseBean.success();
    }
    @ApiOperation(value = "物资入库 删除")
    @GetMapping("/delete/{inStockId}")
    public ResponseBean delete(@PathVariable Long inStockId) {
         inStockService.deleteInStock(inStockId);
        return ResponseBean.success();
    }


}
