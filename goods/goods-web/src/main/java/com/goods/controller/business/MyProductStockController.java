package com.goods.controller.business;

import com.goods.business.service.SupplierService;
import com.goods.common.model.business.Supplier;
import com.goods.common.response.ResponseBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: MyProduct_stock
 * @Desc:
 * @create: 2022-11-21 08:32
 **/
@Api(tags = "接口实例实现")
@RestController
public class MyProductStockController {
    @Autowired
    private SupplierService supplierService;

    @PostMapping("/supplier/getComboboxList")
    public List<Supplier> getComboboxList() {
        List<Supplier> supplierList = supplierService.findAll();
        return supplierList;
    }

}
