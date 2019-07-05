# 开放APP 全部用的androidx 版本,为了后续升级
# niubilityAPP主要是采用其他开放接口练手,关键是想自己封装一个基础库,集成目前大众的三方库(只是依赖而已,用的时候方便而已,
#直接依赖该基础库就都有了)
图片(包括九宫格,圆角,圆形,以及边框)
网络用的rxeasyhttp ,推荐
textview,以及常引用父控件的背景不再写shape,还有选中,press等状态,直接xml里配置
常用的util类,包括toast,log
列表+下拉刷新(BaseRecyclerViewAdapterHelper+SmartRefreshLayout)
webview库,bugly库,eventbus库,屏幕适配库(autosize),轮播图(banner库).....
另外还有base基类库

-----------------------这是规划(后续一步步来)--------------------

后期重新整改glideimage库 ,把rwigitHelper的功能添加进去,库本身的圆角和圆形的图片通过glide加载有些问题,通过裁剪画布的方式比较靠谱
