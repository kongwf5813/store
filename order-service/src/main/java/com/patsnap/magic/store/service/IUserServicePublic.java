package com.patsnap.magic.store.service;

import com.patsnap.magic.store.common.ServerResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Owen on 2018/1/4.
 */
@FeignClient("user-service")
public interface IUserServicePublic {

    @RequestMapping(value = "/user/isExist", method = RequestMethod.POST)
    @ResponseBody
    ServerResponse isExist(String userId);
}
