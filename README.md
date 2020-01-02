# 开放APP 全部用的androidx 版本,为了后续升级

[电视直播 apk 下载](https://www.pgyer.com/Pso3)
# niubilityAPP主要是采用其他开放接口练手,关键是想自己封装一个基础库,集成目前大众的三方库(只是依赖而已,用的时候方便而已,直接依赖该基础库就都有了)
## 目前的进度:网络库可以直接用rxeasyhttp ,另外baseframework是需要再别的基础上继续扩充,这两个库github上 可以直接搜到
---
- 图片(包括九宫格,圆角,圆形,以及边框)
- 网络用的rxeasyhttp ,推荐
- textview,以及常引用父控件的背景不再写shape,还有选中,press等状态,直接xml里配置
- 常用的util类,包括toast,log
- 列表+下拉刷新(BaseRecyclerViewAdapterHelper+SmartRefreshLayout)
- webview库,bugly库,eventbus库,屏幕适配库(autosize),轮播图(banner库).....
- 另外还有base基类库


```
    AsyncChainLink.getInstance()   //异步并发任务操作
                .withWork(new AsyncChainRunnable() {
                    @Override
                    public void run(AsyncChainTask task) throws Exception {
                        Thread.sleep(1000);
                        Log.i("AsyncChain", "run 1: " + Thread.currentThread().getName());
                        task.allComplete();
                    }
                })
                .withWork(new AsyncChainRunnable() {
                    @Override
                    public void run(AsyncChainTask task) throws Exception {
                        Thread.sleep(2000);
                        Log.i("AsyncChain", "run 2: " + Thread.currentThread().getName());
                        task.allComplete();
                    }
                })
                .withWork(new AsyncChainRunnable() {
                    @Override
                    public void run(AsyncChainTask task) throws Exception {
                        Thread.sleep(3000);
                        Log.i("AsyncChain", "run 3: " + Thread.currentThread().getName());
                        task.allComplete();
                    }
                })
                .goAll(new AsyncChainResultCallBack() {
                    @Override
                    public void allComplete() {
                        Log.i("AsyncChain", "allComplete: 全部完成");
                    }
                });   
                
         AsyncChainLink.getInstance()   //同步串行任务
                                .with(new AsyncChainRunnable() {
                                    @Override
                                    public void run(AsyncChainTask task) throws Exception {
                                        Log.i("AsyncChain", "run: 1");
                                        task.onNext("结果一");
                                    }
                                })
                                .with(new AsyncChainRunnable() {
                                    @Override
                                    public void run(AsyncChainTask task) throws Exception {
                                        int i = 10 / 0;
                                        Log.i("AsyncChain", "run: 2" + i);
                
                                    }
                                })
                                .with(new AsyncChainRunnable() {
                                    @Override
                                    public void run(AsyncChainTask task) throws Exception {
                                        Log.i("AsyncChain", "run: 3" + task.getLastResult());
                                        task.onComplete();
                                    }
                                })
                                .error(new AsyncChainErrorCallback() {
                                    @Override
                                    public void error(AsyncChainError error) throws Exception {
                                        Log.i("AsyncChain", "error: " + error.toString());
                                    }
                                })
                                .go();      
```
