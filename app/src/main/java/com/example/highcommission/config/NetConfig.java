package com.example.highcommission.config;

/**
 * Created by 小五 on 2017/2/21.
 */

public interface NetConfig {
    /*
    * 大淘客网址
    * */
    String DA_TAO_KE = "http://www.dataoke.com/";
    /*
    * 上传数据的接口
    * */
    String UP_GENERALIZE = "http://www.dataoke.com/ucenter/save.asp?act=add_quan&id=%s";
    /*
    * 获取数据的接口
    * */
    String GET_DATA = "http://api.dataoke.com/index.php?r=Port/index&type=total&appkey=gyqy9mufpq&v=2&page=1";
}
