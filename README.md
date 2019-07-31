# 开放APP 全部用的androidx 版本,为了后续升级

[电视直播 apk 下载](https://github.com/liweiGe/BaseLib/blob/master/apk/niubilityapp-debug.apk)
# niubilityAPP主要是采用其他开放接口练手,关键是想自己封装一个基础库,集成目前大众的三方库(只是依赖而已,用的时候方便而已,直接依赖该基础库就都有了)
## 目前的进度:网络库可以直接用rxeasyhttp ,另外baseframework是需要再别的基础上继续扩充,这两个库github上 可以直接搜到
图片(包括九宫格,圆角,圆形,以及边框)
网络用的rxeasyhttp ,推荐
textview,以及常引用父控件的背景不再写shape,还有选中,press等状态,直接xml里配置
常用的util类,包括toast,log
列表+下拉刷新(BaseRecyclerViewAdapterHelper+SmartRefreshLayout)
webview库,bugly库,eventbus库,屏幕适配库(autosize),轮播图(banner库).....
另外还有base基类库

-----------------------这是规划(后续一步步来)--------------------
