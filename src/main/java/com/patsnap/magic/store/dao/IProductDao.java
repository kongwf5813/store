package com.patsnap.magic.store.dao;

import com.patsnap.magic.store.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductDao extends JpaRepository<Product, String> {
}
