package com.goods.controller.business;


import com.goods.business.service.InStockService;
import com.goods.business.service.ProductService;
import com.goods.common.model.business.Product;
import com.goods.common.response.ResponseBean;
import com.goods.common.vo.business.InStockVO;
import com.goods.common.vo.business.ProductStockVO;
import com.goods.common.vo.business.ProductVO;
import com.goods.common.vo.system.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: ProductController
 * @Desc:
 * @create: 2022-11-19 15:49
 **/
@Api(tags = "物资资料")
@RestController
@RequestMapping("business/product/")
public class ProductController {
    @Autowired
    private InStockService inStockService;
    @Autowired
    private ProductService productService;

    @GetMapping("/findProductList")
    public ResponseBean findProductList(Integer pageNum, Integer pageSize, ProductVO productVO, HttpServletRequest request) {
        pakgecategorys(productVO, request);
        PageVO<Product> productPageVO= productService.findProductList(pageNum,pageSize,productVO);
        return ResponseBean.success(productPageVO);
    }

    private void pakgecategorys(ProductVO productVO, HttpServletRequest request) {
        String categorysStringValues = request.getParameter("categorys");
        if(!StringUtils.isEmpty(categorysStringValues))
        {
            String[] categorysString = categorysStringValues.split(",");
            Long[] categorys=new Long[categorysString.length];
            for (int i = 0; i < categorysString.length; i++) {
                if(!StringUtils.isEmpty(categorysString[i]))
                {
                    String s = categorysString[i];
                    categorys[i]=Long.valueOf(s);
                }
            }
            productVO.setCategoryKeys(categorys);
        }
    }

    @GetMapping("/findProducts")
    public ResponseBean findProducts(Integer pageNum, Integer pageSize, ProductVO productVO,HttpServletRequest request) {
        pakgecategorys(productVO, request);
        PageVO<Product> productPageVO= productService.findProductList(pageNum,pageSize,productVO);
        return ResponseBean.success(productPageVO);
    }
    @ApiOperation(value = "添加物资来源")
    @PostMapping("add")
    public ResponseBean add(@RequestBody ProductVO productVO) {
        productService.add(productVO);
        return ResponseBean.success();
    }

    @ApiOperation(value = "删除物资来源")
    @DeleteMapping("delete/{Id}")
    public ResponseBean delete(@PathVariable Long Id) {
        productService.delete(Id);
        return ResponseBean.success();
    }

    @ApiOperation(value = "更新物资来源")
    @PutMapping("update/{Id}")
    public ResponseBean update(@PathVariable Long Id, @RequestBody Product product) {
        productService.save(product);
        return ResponseBean.success();
    }
    @ApiOperation(value = "更新物资 通过资料状态")
    @PutMapping("publish/{Id}")
    public ResponseBean publish(@PathVariable Long Id) {
        productService.updateStutas(Id,0);
        return ResponseBean.success();
    }
    @ApiOperation(value = "移到回收站 物资资料")
    @PutMapping("remove/{Id}")
    public ResponseBean remove(@PathVariable Long Id) {
        productService.updateStutas(Id,1);
        return ResponseBean.success();
    }
    @ApiOperation(value = "回复回收站 物资资料")
    @PutMapping("back/{Id}")
    public ResponseBean back(@PathVariable Long Id) {
        productService.updateStutas(Id,0);
        return ResponseBean.success();
    }

    @ApiOperation(value = "获取指定Id的物资来源")
    @GetMapping("/edit/{Id}")
    public ResponseBean edit(@PathVariable Long Id) {
        Product supplier = productService.getById(Id);
        return ResponseBean.success(supplier);
    }

    @ApiOperation(value = "加载库存信息")
    @GetMapping("/findProductStocks")
    public ResponseBean findProductStocks(Integer pageNum, Integer pageSize,ProductVO productVO,HttpServletRequest request) {
        pakgecategorys(productVO, request);
       PageVO<ProductStockVO> allStocks = productService.findAllStocks(pageNum, pageSize, productVO);
        return ResponseBean.success(allStocks);
    }

    @ApiOperation(value = "物资所有的库存信息")
    @GetMapping("/findAllStocks")
    public ResponseBean findAllStocks(Integer pageNum, Integer pageSize,ProductVO productVO,HttpServletRequest request) {
        pakgecategorys(productVO, request);
        PageVO<ProductStockVO> allStocks = productService.findAllStocks(pageNum, pageSize, productVO);
        List<ProductStockVO> rows = allStocks.getRows();
        return ResponseBean.success(rows);
    }

}
