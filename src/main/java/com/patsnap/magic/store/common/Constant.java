package com.patsnap.magic.store.common;

public class Constant {

    public static final String CURRENT_USER = "currentUser";

    public static final String TYPE_USERNAME = "userName";

    public static final String TYPE_EMAIL = "email";

    public interface Role{
        int ROLE_USER = 0; //普通用户
        int ROLE_ADMIN = 1;//管理员
    }

}
