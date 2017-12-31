package com.patsnap.magic.store.service;

import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.Category;

import java.util.List;

/**
 * Created by Owen on 2017/12/31.
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, String parentId);

    ServerResponse updateCategoryName(String categoryId, String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(String categoryId);

    ServerResponse<List<String>> selectCategoryAndChildrenById(String categoryId);
}
