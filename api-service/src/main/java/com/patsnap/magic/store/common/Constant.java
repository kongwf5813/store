package com.patsnap.magic.store.common;

public class Constant {

    public static final String CURRENT_USER = "currentUser";

    public static final String TYPE_USERNAME = "userName";

    public static final String TYPE_EMAIL = "email";

    public interface Role {
        String ROLE_USER = "USER"; //普通用户
        String ROLE_ADMIN = "ADMIN";//管理员
    }

    public interface ProductStatus {
        int ON_SALE = 1;
        int OFF_SALE = 0;
    }

    public interface OrderStatus {
        int CANCELED = 0;
        int NO_PAY = 10;
        int PAID = 20;
        int SHIPPED = 40;
        int ORDER_SUCCESS = 50;
        int ORDER_CLOSE = 60;
    }

    public static final String PERMISSION_DENIED = "user.permission.denied";
}
