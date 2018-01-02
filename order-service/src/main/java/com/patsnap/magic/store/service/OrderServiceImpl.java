package com.patsnap.magic.store.service;

import com.google.common.collect.Lists;
import com.patsnap.magic.store.common.Constant;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.dao.IOrderDao;
import com.patsnap.magic.store.dao.IOrderItemDao;
import com.patsnap.magic.store.entity.Order;
import com.patsnap.magic.store.entity.OrderItem;
import com.patsnap.magic.store.vo.OrderItemVo;
import com.patsnap.magic.store.vo.OrderVo;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by Owen on 2017/12/31.
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IOrderItemDao orderItemDao;

    @Override
    public ServerResponse<OrderVo> createOrder(String userId, List<OrderItem> orderItems) {
        if (CollectionUtils.isEmpty(orderItems)) {
            return ServerResponse.createByErrorMessage("订单列表不能为空");
        }
        if (StringUtils.isBlank(userId)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderNo(generateOrderNo());
        order.setStatus(Constant.OrderStatus.NO_PAY);
        DateTime sysdate = new DateTime();
        order.setCreateTime(sysdate.toDate());
        order.setUpdateTime(sysdate.toDate());
        order.setEndTime(sysdate.plusDays(1).toDate());
        Order savedOrder = orderDao.save(order);
        if (savedOrder == null) {
            return ServerResponse.createByErrorMessage("创建订单失败");
        }

        orderItems.forEach(orderItem -> {
            orderItem.setOrderNo(savedOrder.getOrderNo());
            orderItem.setCreateTime(savedOrder.getCreateTime());
            orderItem.setUpdateTime(savedOrder.getUpdateTime());
            orderItem.setUserId(userId);
        });
        List<OrderItem> savedOderItems = orderItemDao.save(orderItems);
        if (savedOderItems == null){
            return ServerResponse.createByErrorMessage("创建订单列表失败");
        }

        OrderVo orderVo = setOrderVo(savedOrder, savedOderItems);
        return ServerResponse.createBySuccess("创建订单成功", orderVo);
    }

    @Override
    public ServerResponse<String> cancel(String userId, Long orderNo) {
        Order order = orderDao.findByUserIdAndOrderNo(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("该用户此订单不存在");
        }
        if (order.getStatus() != Constant.OrderStatus.NO_PAY) {
            return ServerResponse.createByErrorMessage("已付款,无法取消订单");
        }

        order.setStatus(Constant.OrderStatus.CANCELED);
        DateTime dateTime = new DateTime();
        order.setUpdateTime(dateTime.toDate());
        order.setCloseTime(dateTime.toDate());

        Order savedOrder = orderDao.save(order);
        if (savedOrder != null) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse<OrderVo> getOrderDetail(String userId, Long orderNo) {
        Order order = orderDao.findByUserIdAndOrderNo(userId, orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemDao.findByOrderNoAndUserId(orderNo, userId);
            OrderVo orderVo = setOrderVo(order, orderItemList);
            return ServerResponse.createBySuccess(orderVo);
        }
        return ServerResponse.createByErrorMessage("没有找到该订单");
    }

    @Override
    public ServerResponse<Page<OrderVo>> getOrderList(String userId, int pageNum, int pageSize) {
        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.ASC, "createTime");
        Page<Order> orderList = orderDao.findByUserId(userId, pageable);
        List<OrderVo> orderVoList = setOrderVoList(orderList.getContent(), userId);
        return ServerResponse.createBySuccess(new PageImpl(orderVoList));
    }

    private OrderVo setOrderVo(Order order, List<OrderItem> orderItemList) {

        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setPaymentTime(order.getPaymentTime());
        orderVo.setSendTime(order.getSendTime());
        orderVo.setEndTime(order.getEndTime());
        orderVo.setCreateTime(order.getCreateTime());
        orderVo.setCloseTime(order.getCloseTime());

        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = setOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    private OrderItemVo setOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductUrl());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemVo.setCreateTime(orderItem.getCreateTime());
        return orderItemVo;
    }

    private List<OrderVo> setOrderVoList(List<Order> orderList, String userId) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Order order : orderList) {
            List<OrderItem> orderItemList = Lists.newArrayList();
            if (userId == null) {
                orderItemList = orderDao.findByOrderNo(order.getOrderNo());
            } else {
                orderItemList = orderItemDao.findByOrderNoAndUserId(order.getOrderNo(), userId);
            }
            OrderVo orderVo = setOrderVo(order, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    private long generateOrderNo() {
        long currentTime = System.currentTimeMillis();
        return currentTime + new Random().nextInt(100);
    }

}
