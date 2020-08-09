package com.tatu.essay.api;


public class Api {


//    static String base_url = "https://dengtatu.cn/";

    static String base_url = "http://192.168.1.3:8081/";
    static String essay_url = base_url + "api/essay/";

    String apiDesc;

    public static final int PAGE_SIZE = 50;

    public static String versionName;

    public static Long authorId;

    public static String phone;

    /**
     * 当前记录起始索引
     */
    static String PAGE_NUM_STR = "pageNumber";

    /**
     * 每页显示记录数
     */
    static String PAGE_SIZE_STR = "pageSize";

    /**
     * 排序列
     */
    static String ORDER_BY_COLUMN = "orderByColumn";



    public static int  V_PAGE_SIZE =  20;


}
