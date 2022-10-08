package com.zxt.constants;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 13:46
 */
public interface UrlConstants {
    //String BASE_B_URL = "http://localhost:20002/";
    String BASE_B_URL = "http://EUREKA-CLIENT-B/";

    String RESTTEMPLATE_GET_01 = BASE_B_URL + "b/01/{id}?username=zhangsan";
    String RESTTEMPLATE_POST_02 = BASE_B_URL + "b/02/{id}?username=lisi";//请求体中传递json数据
    String RESTTEMPLATE_PUT_03 = BASE_B_URL + "b/03/{id}?username=wangwu";//请求体中传递json数据
    String RESTTEMPLATE_DELETE_04 = BASE_B_URL + "b/04/{id}?username=zhaoliu";
}
