package com.patsnap.magic.store.dao;

import com.patsnap.magic.store.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryDao extends JpaRepository<Category, String> {
}
