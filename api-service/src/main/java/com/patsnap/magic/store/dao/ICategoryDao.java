package com.patsnap.magic.store.dao;

import com.patsnap.magic.store.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoryDao extends JpaRepository<Category, String> {

    List<Category> findByParentId(String parentId);
}
