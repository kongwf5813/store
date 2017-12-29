package com.patsnap.magic.store.service;

import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.User;
import com.patsnap.magic.store.request.UserRequestInfo;

public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(UserRequestInfo user);

    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    ServerResponse<User> updateInformation(User user);
}
