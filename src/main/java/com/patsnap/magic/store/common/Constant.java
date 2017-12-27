package com.patsnap.magic.store.common;

public class Constant {

    public static final String CURRENT_USER = "currentUser";

    public static final String TYPE_USERNAME = "userName";

    public static final String TYPE_EMAIL = "email";

    public interface Role{
        String ROLE_USER = "USER"; //普通用户
        String ROLE_ADMIN = "ADMIN";//管理员
    }


    public static final String PERMISSION_DENIED= "user.permission.denied";
}
