package com.patsnap.magic.store.dao;

import com.patsnap.magic.store.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderItemDao extends JpaRepository<OrderItem, String> {

}
