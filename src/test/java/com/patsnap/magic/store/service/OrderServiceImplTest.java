package com.patsnap.magic.store.service;

import com.patsnap.magic.store.BaseTest;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.OrderItem;
import com.patsnap.magic.store.vo.OrderVo;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owen on 2017/12/31.
 */
public class OrderServiceImplTest extends BaseTest {

    @Autowired
    private IOrderService orderService;

    @Test
    public void testAll() throws Exception {

        String userId = "8a80cb8160b576800160b576970c0000";

        //测试创建订单接口
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId("8a80cb8160b5ad8e0160b5ada4d60000");
        orderItem.setProductName("火影忍者鸣人");
        orderItem.setCurrentPrice(new BigDecimal(16.80));
        orderItem.setProductUrl("item1234567");
        orderItem.setQuantity(2);
        orderItem.setTotalPrice(new BigDecimal(16.80 * 2));
        orderItems.add(orderItem);
        ServerResponse result = orderService.createOrder(userId, orderItems);
        Assert.assertTrue(result.isSuccess());

        //测试取消订单接口
        OrderVo orderVo = (OrderVo) result.getData();
        result = orderService.cancel(userId, orderVo.getOrderNo());
        Assert.assertTrue(result.isSuccess());

        //测试查询订单详情接口
        result = orderService.getOrderDetail(userId, orderVo.getOrderNo());
        Assert.assertTrue(result.isSuccess());

        //测试查询订单分页接口
        result = orderService.getOrderList(userId, 1, 20);
        Assert.assertTrue(result.isSuccess());
    }
}