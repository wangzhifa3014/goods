package com.goods.controller.business;

import com.goods.business.service.SupplierService;
import com.goods.common.model.business.ProductCategory;
import com.goods.common.model.business.Supplier;
import com.goods.common.response.ResponseBean;
import com.goods.common.vo.business.ProductCategoryTreeNodeVO;
import com.goods.common.vo.business.SupplierVO;
import com.goods.common.vo.system.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: SupplierController
 * @Desc:
 * @create: 2022-11-19 14:51
 **/
@Api(tags = "物资来源")
@RestController
@RequestMapping("/business/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    /**
     * business/supplier/findSupplierList?pageNum=1&pageSize=10&name=1&contact=w &address=
     *
     * @param pageNum
     * @param pageSize
     * @param supplierVO
     * @return
     */
    @GetMapping("/findSupplierList")
    public ResponseBean findSupplierList(Integer pageNum, Integer pageSize, SupplierVO supplierVO) {
        PageVO<Supplier> supplierPageVO = supplierService.findSupplierList(pageNum, pageSize, supplierVO);
        return ResponseBean.success(supplierPageVO);
    }
    @GetMapping("/findAll")
    public ResponseBean findAll(Integer pageNum, Integer pageSize, SupplierVO supplierVO) {
        List<Supplier> supplierPageVO = supplierService.findAll();
        return ResponseBean.success(supplierPageVO);
    }

    @ApiOperation(value = "添加物资来源")
    @PostMapping("add")
    public ResponseBean add(@RequestBody Supplier supplier) {
        supplierService.add(supplier);
        return ResponseBean.success();
    }

    @ApiOperation(value = "删除物资来源")
    @DeleteMapping("delete/{Id}")
    public ResponseBean delete(@PathVariable Long Id) {
        supplierService.delete(Id);
        return ResponseBean.success();
    }

    @ApiOperation(value = "更新物资来源")
    @PutMapping("update/{Id}")
    public ResponseBean update(@PathVariable Long Id, @RequestBody Supplier supplier) {
        supplierService.save(supplier);
        return ResponseBean.success();
    }

    @ApiOperation(value = "获取指定Id的物资来源")
    @GetMapping("/edit/{Id}")
    public ResponseBean edit(@PathVariable Long Id) {
        Supplier supplier = supplierService.getById(Id);
        return ResponseBean.success(supplier);
    }
}
