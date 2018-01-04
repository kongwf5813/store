package com.patsnap.magic.store.service;

import com.patsnap.magic.store.BaseTest;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.Category;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Created by Owen on 2017/12/31.
 */
public class CategoryServiceImplTest extends BaseTest{

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void testCategoryAll() throws Exception {
        //测试新增分类接口
        ServerResponse response = categoryService.addCategory("男人装",null);
        Category saveCategory = (Category)response.getData();
        Assert.assertNotNull(saveCategory);

        //测试更新分类接口
        response =  categoryService.updateCategoryName(saveCategory.getId(), "男装");
        Assert.assertNotNull(response.getData());

        //测试查询子分类接口
        response = categoryService.getChildrenParallelCategory(saveCategory.getId());
        Assert.assertNotNull(response.getData());

        //测试递归查询本分类及及其子分类接口
        response = categoryService.selectCategoryAndChildrenById(saveCategory.getId());
        List<String> result = (List<String>) response.getData();
        Assert.assertTrue(result.size() == 1);
    }
}