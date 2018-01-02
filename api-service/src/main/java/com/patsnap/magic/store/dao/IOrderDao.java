package com.patsnap.magic.store.dao;

import com.patsnap.magic.store.entity.Order;
import com.patsnap.magic.store.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Owen on 2017/12/31.
 */
public interface IOrderDao extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

    Order findByUserIdAndOrderNo(String userId, Long orderNo);

    Page<Order> findByUserId(String userId, Pageable pageable);

    List<OrderItem> findByOrderNo(Long orderNo);
}
