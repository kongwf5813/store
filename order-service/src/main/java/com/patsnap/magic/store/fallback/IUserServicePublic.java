package com.patsnap.magic.store.fallback;

import com.patsnap.magic.store.common.ServerResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Owen on 2018/1/4.
 */
@FeignClient(name = "user-service", fallback = UserHystrixServiceImpl.class)
public interface IUserServicePublic {

    @RequestMapping(value = "/user/isExist/{userId}", method = RequestMethod.GET)
    @ResponseBody
    ServerResponse isExist(@PathVariable(value = "userId") String userId);
}
