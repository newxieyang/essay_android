package com.tatu.essay.api;


public class Api {


   final static boolean isDebug = true;

   final static String base_url;
    static {
        if(isDebug) {
            base_url = "http://127.0.0.1:8081/api/";
        } else  {
            base_url = "https://dengtatu.cn/api/";
        }
    }

    static final String essay_url = base_url + "essay/";

//    简历
    static final String resume_url = base_url + "resume/";

    // 工作经验
    static final String experience_url  = base_url + "experiences/";

    // 技能&工具
    static final String skill_url  = base_url + "skill/";

    public static final int PAGE_SIZE = 50;

    public static String versionName;

    public static Long authorId;

    public static String phone;

    /**
     * 当前记录起始索引
     */
    static final String PAGE_NUM_STR = "pageNumber";

    /**
     * 每页显示记录数
     */
    static final String PAGE_SIZE_STR = "pageSize";

    /**
     * 排序列
     */
    static final String ORDER_BY_COLUMN = "orderByColumn";



    public static  final int  V_PAGE_SIZE =  20;


}
