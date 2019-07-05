package com.example.niubilityapp.http;

public interface HttpApi {
    String TAG = "weige";
    /**
     * 注意链接的拼接模式,baseUrl必须以/ 结尾
     */
    String baseUrl = "https://api.apiopen.top/";
    String news = "getWangYiNews"; //新闻
    String joke = "getJoke"; //段子列表

}
