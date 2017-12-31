package com.patsnap.magic.store.dao;

import com.patsnap.magic.store.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderItemDao extends JpaRepository<OrderItem, String> {
    List<OrderItem> findByOrderNoAndUserId(long orderNo, String userId);
}
