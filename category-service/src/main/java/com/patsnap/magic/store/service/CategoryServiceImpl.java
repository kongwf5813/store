package com.patsnap.magic.store.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.dao.ICategoryDao;
import com.patsnap.magic.store.entity.Category;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by Owen on 2017/12/31.
 */

@Service
public class CategoryServiceImpl implements ICategoryService{

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public ServerResponse addCategory(String categoryName, String parentId) {
        if(StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);//这个分类是可用的

        Category savedCategory = categoryDao.save(category);
        if(savedCategory != null){
            return ServerResponse.createBySuccess("添加品类成功",savedCategory);
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    @Override
    public ServerResponse updateCategoryName(String categoryId, String categoryName) {
        if(StringUtils.isBlank(categoryId) || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }

        Category existOne = categoryDao.findOne(categoryId);
        if (existOne != null){
            existOne.setName(categoryName);
            Category savedCategory = categoryDao.save(existOne);
            if(savedCategory != null){
                return ServerResponse.createBySuccess("更新品类名字成功", savedCategory);
            }
        }
        return ServerResponse.createByErrorMessage("更新品类名字失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(String categoryId) {
        List<Category> categoryList = categoryDao.findByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 递归查询本节点的id及孩子节点的id
     * @param categoryId
     * @return ServerResponse<List<String>>
     */
    public ServerResponse<List<String>> selectCategoryAndChildrenById(String categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);

        List<String> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            for(Category categoryItem : categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    //递归算法,算出子节点
    private Set<Category> findChildCategory(Set<Category> categorySet ,String categoryId){
        Category category = categoryDao.findOne(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        //查找子节点,递归算法一定要有一个退出的条件
        List<Category> categoryList = categoryDao.findByParentId(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }
}
