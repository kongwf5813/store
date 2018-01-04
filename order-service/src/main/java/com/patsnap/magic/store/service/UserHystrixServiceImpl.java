package com.patsnap.magic.store.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Owen on 2018/1/3.
 */
@Service
public class UserHystrixServiceImpl {

    @Autowired
    private IUserServicePublic userService;

    @HystrixCommand(fallbackMethod = "fallback")
    public ServerResponse isExist(String userId) {
        return userService.isExist(userId);
    }

    public ServerResponse<User> fallback() {
        return ServerResponse.createBySuccessMessage("服务暂不可用，请稍后重试");
    }
}
