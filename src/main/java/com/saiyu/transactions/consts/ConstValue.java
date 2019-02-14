package com.saiyu.transactions.consts;

public class ConstValue {

    public static boolean flag = true;//是否打开log打印

    public static final String SERVR_URL = "http://192.168.1.46:9031/manageApi/";//测试地址
//    public static final String SERVR_URL = "http://work.saiyu.com/manageApi/";//线上地址
    public static String MetaTypeJson = "application/json; charset=utf-8";
    public static final String APPID = "ManageAndroidApp";
    public static final String APPSecret = "XdEUpQjvih5KC4ZiMkXwFvNfRvvMED38";
    public static String UserSecret;

    //自动登录的标记
    public static final String AUTO_LOGIN_FLAG = "autologinflag";
    public static final String PWD_LOGIN = "pwdlogin";


    public static final String ACCESS_TOKEN = "accessToken";
    public static final String USER_KEY = "userKey";

    //    手机正则
    public static final String REGEX_PHONE = "^((1[0-9]{1})+\\d{9})$";
    //    邮箱正则
    public static final String REGEX_EMAIL = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

}
