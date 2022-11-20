package com.goods.business.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.business.mapper.ProductCategoryMapper;
import com.goods.business.service.ParentCategoryService;
import com.goods.common.enums.buisiness.BizUserTypeEnum;
import com.goods.common.model.business.ProductCategory;
import com.goods.common.model.system.Department;
import com.goods.common.model.system.Role;
import com.goods.common.vo.business.ProductCategoryTreeNodeVO;
import com.goods.common.vo.system.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: ParentCategoryServiceImpl
 * @Desc:
 * @create: 2022-11-19 11:07
 **/
@Service
public class ParentCategoryServiceImpl implements ParentCategoryService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Override
    public PageVO<ProductCategoryTreeNodeVO> categoryTree(Integer pageNum, Integer pageSize) {
        if(pageNum!=null&&pageNum!=0&&pageSize!=null&&pageSize!=0)
        {
        PageHelper.startPage(pageNum, pageSize);}
        List<ProductCategory> byPidCategoryTree = getByPidCategoryTree(0L);
        List<ProductCategoryTreeNodeVO> productCategoryTreeNodeVOS = converterToMenuNodeVO(byPidCategoryTree, 1);
        if(pageNum!=null&&pageNum!=0&&pageSize!=null&&pageSize!=0) {
            PageInfo<ProductCategory> info = new PageInfo<>(byPidCategoryTree);
            return new PageVO<>(info.getTotal(), productCategoryTreeNodeVOS);
        }
        return new PageVO<>(byPidCategoryTree.size(), productCategoryTreeNodeVOS);
    }

    @Override
    public List<ProductCategory> getByPidCategoryTree(Long pid) {
        Example o = new Example(ProductCategory.class);
        o.createCriteria().andEqualTo("pid", pid);
        return  productCategoryMapper.selectByExample(o);
    }

    @Override
    public List<ProductCategoryTreeNodeVO> getParentCategoryTree() {
        List<ProductCategory> byPidCategoryTree = getByPidCategoryTree(0L);
        List<ProductCategoryTreeNodeVO> productCategoryTreeNodeVOS = converterToMenuNodeVO(byPidCategoryTree, 0);
        return productCategoryTreeNodeVOS;
    }

    @Override
    public void add(ProductCategory productCategory) {
        Date date=new Date();
        productCategory.setCreateTime(date);
        productCategory.setModifiedTime(date);
        productCategoryMapper.insert(productCategory);
    }

    @Override
    public void delete(Long id) {
        productCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ProductCategory getById(Long id) {
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(id);
        return productCategory;
    }

    @Override
    public void save(ProductCategory productCategory) {
        productCategory.setModifiedTime(new Date());
        productCategoryMapper.updateByPrimaryKey(productCategory);
    }

    /**
     * 转成menuVO(只包含菜单)List
     *
     * @param menus
     * @return
     */
    public  List<ProductCategoryTreeNodeVO> converterToMenuNodeVO(List<ProductCategory> menus,int lev) {

        //先过滤出用户的菜单
        List<ProductCategoryTreeNodeVO> productCategoryTreeNodeVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menus)) {
            for (ProductCategory menu : menus) {
                ProductCategoryTreeNodeVO productCategoryTreeNodeVO = new ProductCategoryTreeNodeVO();
                BeanUtils.copyProperties(menu,productCategoryTreeNodeVO);
                productCategoryTreeNodeVO.setLev(lev);
                List<ProductCategory> byPidCategoryTree = getByPidCategoryTree(productCategoryTreeNodeVO.getId());
                if(!CollectionUtils.isEmpty(byPidCategoryTree))
                {
                    productCategoryTreeNodeVO.setChildren(converterToMenuNodeVO(byPidCategoryTree,lev+1));
                }
                productCategoryTreeNodeVOList.add(productCategoryTreeNodeVO);
            }
        }
        productCategoryTreeNodeVOList.sort(ProductCategoryTreeNodeVO.order());
        return productCategoryTreeNodeVOList;
    }
}
