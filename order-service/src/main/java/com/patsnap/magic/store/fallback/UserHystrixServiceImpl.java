package com.patsnap.magic.store.fallback;

import com.patsnap.magic.store.common.ServerResponse;

import org.springframework.stereotype.Service;

/**
 * Created by Owen on 2018/1/3.
 */
@Service
public class UserHystrixServiceImpl implements IUserServicePublic{

    @Override
    public ServerResponse isExist(String userId) {
        return ServerResponse.createBySuccess();
    }
}
