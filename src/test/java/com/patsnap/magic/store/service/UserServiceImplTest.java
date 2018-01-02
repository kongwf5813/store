package com.patsnap.magic.store.service;

import com.patsnap.magic.store.BaseTest;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.User;
import com.patsnap.magic.store.request.UserRequestInfo;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by Owen on 2017/12/31.
 */
public class UserServiceImplTest extends BaseTest {

    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;

    @Test
    public void testRegister() throws Exception {
        UserRequestInfo userRequestInfo = new UserRequestInfo();
        userRequestInfo.setUserName("test5813");
        userRequestInfo.setPassword("abcdEF123*");
        userRequestInfo.setEmail("test123@qq.com");
        userRequestInfo.setQuestion("请问我的家乡是哪里?");
        userRequestInfo.setAnswer("大蓝鲸");
        userRequestInfo.setPhone("18911111111");
        ServerResponse<User> result = userService.register(userRequestInfo);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testLoginAndUpdateInformation() throws Exception {
        ServerResponse<User> result = userService.login("test5813", "abcdEF123*");
        Assert.assertTrue(result.isSuccess());

        User loginUser = result.getData();
        loginUser.setPhone("13912345678");
        result = userService.updateInformation(loginUser);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testResetPassword() throws Exception {
        ServerResponse<User> result = userService.login("test5813", "abcdEF123*");
        User loginUser = result.getData();
        ServerResponse<String> result1  = userService.resetPassword("abcdEF123*","ABCDef123*", loginUser);
        Assert.assertTrue(result1.isSuccess());
    }
}