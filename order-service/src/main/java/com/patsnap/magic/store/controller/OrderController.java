package com.patsnap.magic.store.controller;


import com.netflix.discovery.converters.Auto;
import com.patsnap.magic.store.common.Constant;
import com.patsnap.magic.store.common.ResponseCode;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.OrderItem;
import com.patsnap.magic.store.entity.User;
import com.patsnap.magic.store.service.IOrderService;
import com.patsnap.magic.store.service.IUserServicePublic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/order")
public class OrderController {

    private static  final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserServicePublic userServicePublic;

    @RequestMapping("create")
    @ResponseBody
    public ServerResponse create(HttpSession session, List<OrderItem> orderItems){
        User user = (User)session.getAttribute(Constant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.createOrder(user.getId(), orderItems);
    }


    @RequestMapping("cancel")
    @ResponseBody
    public ServerResponse cancel(HttpSession session, Long orderNo){
        User user = (User)session.getAttribute(Constant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.cancel(user.getId(),orderNo);
    }

    @RequestMapping("detail")
    @ResponseBody
    public ServerResponse detail(String userId, Long orderNo){
        if(userId ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        //具有服务断路处理
        ServerResponse response = userServicePublic.isExist(userId);
        if (response.isSuccess()) {
            return orderService.getOrderDetail(userId,orderNo);
        }
        return ServerResponse.createByErrorMessage("用户不存在");
    }

    @RequestMapping("list")
    @ResponseBody
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.getOrderList(user.getId(), pageNum, pageSize);
    }
}
