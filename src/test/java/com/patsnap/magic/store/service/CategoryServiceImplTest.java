package com.patsnap.magic.store.service;

import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.Category;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Owen on 2017/12/31.
 */
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void testAddCategory() throws Exception {
        //一级分类
        ServerResponse response = categoryService.addCategory("女装",null);
        //二级分类
        String parentId = ((Category)response.getData()).getId();
        response = categoryService.addCategory("毛衣",parentId);
        Assert.assertNotNull(response.getData());
    }

    @Test
    public void testUpdateCategoryName() throws Exception {

    }

    @Test
    public void testGetChildrenParallelCategory() throws Exception {

    }

    @Test
    public void testSelectCategoryAndChildrenById() throws Exception {

    }
}