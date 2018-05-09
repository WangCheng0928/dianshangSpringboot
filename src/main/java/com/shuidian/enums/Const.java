package com.shuidian.enums;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @Author: wangcheng
 * @Date: Created in 16:37 2018/2/27
 */
public class Const {

    public static final String CURRRENT_USER = "currentUser";
    public static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PHONE = "^[1][34578]\\d{9}$";
    //用户名（字母开头 + 数字/字母/下划线）
    public static final String USERNAME = "^[A-Za-z][A-Za-z1-9_-]+$";

    //ftp
    public static final String FTP_PREFIX_KEY = "ftp.server.http.prefix";
    public static final String FTP_PREFIX_VALUE = "http://image.imooc.com/";
    public static final String FTP_USER_KEY = "ftp.user";
    public static final String FTP_PWD_KEY = "ftp.pass";
    public static final String FTP_IP_KEY = "ftp.server.ip";
    public static final String FTP_UPLOAD_REMOTEPATH = "img";

    //alipay
    public static final String alipay_callback_url = "http://image.imooc.com";

    public interface Role{
        int ROLE_CUSTOMER=0; //普通用户
        int ROLE_ADMIN=1; //管理员
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }

    public interface CartChecked{
        int CHECKED = 1; //购物车选中状态
        int UNCHECKED = 0; // 购物车未选中状态
        String LIMIT_COUNT_SUCCESS = "LIMIT_COUNT_SUCCESS";
        String LIMIT_COUNT_FAILURE = "LIMIT_COUNT_FAILURE";
    }

    public enum ProductStatusEnum{
        ON_SALE(1, "在线");
        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum OrderStatusEnum{
        CANCELED(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已付款"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭")
        ;

        private String value;
        private int code;

        OrderStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum : values()){
                if (orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    public interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    public enum PayPlatformEnum{
        ALIPAY(1, "支付宝");
        private String value;
        private int code;

        PayPlatformEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum PaymentTypeEnum{
        ONLINE_PAY(1, "在线支付");
        private String value;
        private int code;

        PaymentTypeEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static PaymentTypeEnum codeOf(int code){
            for (PaymentTypeEnum paymentTypeEnum : values()){
                if (paymentTypeEnum.getCode() == code){
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }
}
