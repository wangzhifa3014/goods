package com.goods.controller.business;

import com.github.pagehelper.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.service.ParentCategoryService;
import com.goods.common.model.business.ProductCategory;
import com.goods.common.model.system.ImageAttachment;
import com.goods.common.response.ResponseBean;
import com.goods.common.vo.business.ProductCategoryTreeNodeVO;
import com.goods.common.vo.system.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author: ParentCategoryController
 * @Desc:
 * @create: 2022-11-19 11:05
 **/

@Api(tags = "物资分类")
@RestController
@RequestMapping(value = "business/productCategory/")
public class ParentCategoryController {
    @Autowired
    private ParentCategoryService parentCategoryService;

@ApiOperation(value = "获取所有分类")
    @GetMapping("/categoryTree")
    public ResponseBean categoryTree(Integer pageNum, Integer pageSize) {

        PageVO<ProductCategoryTreeNodeVO> productCategoryPageVO = parentCategoryService.categoryTree(pageNum,pageSize);
        return ResponseBean.success(productCategoryPageVO);
    }
    @ApiOperation(value = "获取指定Id的分类信息")
    @GetMapping("/edit/{Id}")
    public ResponseBean edit(@PathVariable Long Id) {
        ProductCategory productCategory=parentCategoryService.getById(Id);
        return ResponseBean.success(productCategory);
    }
    @ApiOperation(value = "获取所有分类")
    @GetMapping("/getParentCategoryTree")
    public ResponseBean getParentCategoryTree() {
        List<ProductCategoryTreeNodeVO> productCategoryTreeNodeVOS = parentCategoryService.getParentCategoryTree();
        productCategoryTreeNodeVOS.sort(ProductCategoryTreeNodeVO.order());
        return ResponseBean.success(productCategoryTreeNodeVOS);
    }
    @ApiOperation(value = "添加分类信息")
    @PostMapping("add")
    public ResponseBean add(@RequestBody ProductCategory productCategory)
    {
        parentCategoryService.add(productCategory);
        return ResponseBean.success();
    }
    @ApiOperation(value = "删除分类信息")
    @DeleteMapping("delete/{Id}")
    public ResponseBean delete(@PathVariable Long Id)
    {
        parentCategoryService.delete(Id);
        return ResponseBean.success();
    }
    @ApiOperation(value = "更新分类信息")
    @PutMapping("update/{Id}")
    public ResponseBean update(@PathVariable Long Id,@RequestBody ProductCategory productCategory)
    {
        parentCategoryService.save(productCategory);
        return ResponseBean.success();
    }

}
