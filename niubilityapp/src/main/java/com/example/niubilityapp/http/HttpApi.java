package com.example.niubilityapp.http;

public interface HttpApi {
    String TAG = "weige";
    /**
     * 注意链接的拼接模式,baseUrl必须以/ 结尾
     */
    String baseUrl = "https://api.apiopen.top/";
    String news = "getWangYiNews"; //新闻
    String joke = "getJoke"; //段子列表
    String zhihu = "https://news-at.zhihu.com/api/3/news/hot";
    String wangyi = "http://c.m.163.com/nc/article/list/T1467284926140/0-20.html";
    String base_url = "https://api.isoyu.com/";
    String api_news = "api/News/new_list";
}
