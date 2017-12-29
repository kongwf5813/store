package com.patsnap.magic.store.dao;

import com.patsnap.magic.store.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductDao extends JpaRepository<Product, String> {

    @Query("select product from Product product where product.name like :keyword")
    Page<Product> findProductsByNameLike(@Param("keyword")String keyword, Pageable page);
}
