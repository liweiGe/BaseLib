package com.example.niubilityapp.live_TV;

import java.util.ArrayList;
import java.util.List;

public final class LiveUrl {
    public static List<VideoBean> getCityVideoList() {
        List<VideoBean> videoList = new ArrayList<>(64);
        //北京卫视
        videoList.add(new VideoBean("北京卫视", "http://ivi.bupt.edu.cn/hls/btv1.m3u8"));
        videoList.add(new VideoBean("北京文艺", "http://ivi.bupt.edu.cn/hls/btv2.m3u8"));
        videoList.add(new VideoBean("北京科教", "http://ivi.bupt.edu.cn/hls/btv3.m3u8"));
        videoList.add(new VideoBean("北京影视", "http://ivi.bupt.edu.cn/hls/btv4.m3u8"));
        videoList.add(new VideoBean("北京财经", "http://ivi.bupt.edu.cn/hls/btv5.m3u8"));
        videoList.add(new VideoBean("北京体育", "http://ivi.bupt.edu.cn/hls/btv6.m3u8"));
        videoList.add(new VideoBean("北京生活", "http://ivi.bupt.edu.cn/hls/btv7.m3u8"));
        videoList.add(new VideoBean("北京青年", "http://ivi.bupt.edu.cn/hls/btv8.m3u8"));
        videoList.add(new VideoBean("北京新闻", "http://ivi.bupt.edu.cn/hls/btv9.m3u8"));

        //地方卫视
        videoList.add(new VideoBean("深圳卫视", "http://ivi.bupt.edu.cn/hls/sztv.m3u8"));
        videoList.add(new VideoBean("安徽卫视", "http://ivi.bupt.edu.cn/hls/ahtv.m3u8"));
        videoList.add(new VideoBean("河南卫视", "http://ivi.bupt.edu.cn/hls/hntv.m3u8"));
        videoList.add(new VideoBean("陕西卫视", "http://ivi.bupt.edu.cn/hls/sxtv.m3u8"));
        videoList.add(new VideoBean("吉林卫视", "http://ivi.bupt.edu.cn/hls/jltv.m3u8"));
        videoList.add(new VideoBean("广东卫视", "http://ivi.bupt.edu.cn/hls/gdtv.m3u8"));
        videoList.add(new VideoBean("湖北卫视", "http://ivi.bupt.edu.cn/hls/hbtv.m3u8"));
        videoList.add(new VideoBean("广西卫视", "http://ivi.bupt.edu.cn/hls/gxtv.m3u8"));
        videoList.add(new VideoBean("河北卫视", "http://ivi.bupt.edu.cn/hls/hebtv.m3u8"));
        videoList.add(new VideoBean("西藏卫视", "http://ivi.bupt.edu.cn/hls/xztv.m3u8"));
        videoList.add(new VideoBean("内蒙古卫视", "http://ivi.bupt.edu.cn/hls/nmtv.m3u8"));
        videoList.add(new VideoBean("青海卫视", "http://ivi.bupt.edu.cn/hls/qhtv.m3u8"));
        videoList.add(new VideoBean("四川卫视", "http://ivi.bupt.edu.cn/hls/sctv.m3u8"));
        videoList.add(new VideoBean("江苏卫视", "http://ivi.bupt.edu.cn/hls/jshd.m3u8"));
        videoList.add(new VideoBean("天津卫视", "http://ivi.bupt.edu.cn/hls/tjtv.m3u8"));
        videoList.add(new VideoBean("山西卫视", "http://ivi.bupt.edu.cn/hls/sxrtv.m3u8"));
        videoList.add(new VideoBean("辽宁卫视", "http://ivi.bupt.edu.cn/hls/lntv.m3u8"));
        videoList.add(new VideoBean("厦门卫视", "http://ivi.bupt.edu.cn/hls/xmtv.m3u8"));
        videoList.add(new VideoBean("新疆卫视", "http://ivi.bupt.edu.cn/hls/xjtv.m3u8"));
        videoList.add(new VideoBean("东方卫视", "http://ivi.bupt.edu.cn/hls/dftv.m3u8"));
        videoList.add(new VideoBean("黑龙江卫视", "http://ivi.bupt.edu.cn/hls/hljtv.m3u8"));
        videoList.add(new VideoBean("湖南卫视", "http://ivi.bupt.edu.cn/hls/hunanhd.m3u8"));
        videoList.add(new VideoBean("云南卫视", "http://ivi.bupt.edu.cn/hls/yntv.m3u8"));
        videoList.add(new VideoBean("江西卫视", "http://ivi.bupt.edu.cn/hls/jxtv.m3u8"));
        videoList.add(new VideoBean("福建东南卫视", "http://ivi.bupt.edu.cn/hls/dntv.m3u8"));
        videoList.add(new VideoBean("浙江卫视", "http://ivi.bupt.edu.cn/hls/zjhd.m3u8"));
        videoList.add(new VideoBean("贵州卫视", "http://ivi.bupt.edu.cn/hls/gztv.m3u8"));
        videoList.add(new VideoBean("宁夏卫视", "http://ivi.bupt.edu.cn/hls/nxtv.m3u8"));
        videoList.add(new VideoBean("甘肃卫视", "http://ivi.bupt.edu.cn/hls/gstv.m3u8"));
        videoList.add(new VideoBean("重庆卫视", "http://ivi.bupt.edu.cn/hls/cqtv.m3u8"));
        return videoList;
    }

    public static List<VideoBean> getOtherVideoList() {
        List<VideoBean> videoList = new ArrayList<>(16);

        //外台
        videoList.add(new VideoBean("兵团卫视", "http://ivi.bupt.edu.cn/hls/bttv.m3u8"));
        videoList.add(new VideoBean("旅游卫视", "http://ivi.bupt.edu.cn/hls/lytv.m3u8"));
        videoList.add(new VideoBean("清华大学电视台", "http://v.cic.tsinghua.edu.cn:8080/live/tsinghuatv.flv"));
        videoList.add(new VideoBean("美国中文台", "http://media3.sinovision.net:1935/live/livestream/playlist.m3u8"));
        videoList.add(new VideoBean("HD德国", "http://de1se01.v2beat.live/playlist.m3u8"));
        videoList.add(new VideoBean("新加坡亚洲电视新闻", "http://drsh196ivjwe8.cloudfront.net/hls/cnai/03.m3u8"));
        videoList.add(new VideoBean("环球电视", "http://live-cdn.xzxwhcb.com/hls/r86am856.m3u8"));

        //其他有趣频道
        videoList.add(new VideoBean("港片轮播", "http://dlhls.cdn.zhanqi.tv/zqlive/35349_iXsXw.m3u8"));
        videoList.add(new VideoBean("赵本山小品", "http://aldirect.hls.huya.com/huyalive/29106097-2689443426-11551071559112196096-2789253866-10057-A-0-1.m3u8"));
        videoList.add(new VideoBean("易中天品三国", "http://aldirect.hls.huya.com/huyalive/28466698-2689661800-11552009468300492800-3048991590-10057-A-1525512749-1.m3u8"));
        videoList.add(new VideoBean("少年包青天", "http://tx.hls.huya.com/huyalive/30765679-2534694464-10886429828232249344-2789253856-10057-A-0-1.m3u8"));
        videoList.add(new VideoBean("天龙八部", "http://tx.hls.huya.com/huyalive/30765679-2475713428-10633108207528050688-3048991608-10057-A-0-1.m3u8"));
        videoList.add(new VideoBean("倚天屠龙记", "http://tx.hls.huya.com/huyalive/30765679-2523417175-10837994240789708800-2789253884-10057-A-0-1.m3u8"));
        videoList.add(new VideoBean("神雕侠侣", "http://tx.hls.huya.com/huyalive/29359996-2689607114-11551774593718943744-2847687530-10057-A-1525492553-1.m3u8"));
        videoList.add(new VideoBean("虎牙直播", "https://al.hls.huya.com/huyalive/78941969-2559461593-10992803837303062528-2693342886-10057-A-0-1_1200.m3u8"));
        videoList.add(new VideoBean("漫威电影", "http://tx.hls.huya.com/huyalive/30765679-2504742278-10757786168918540288-3049003128-10057-A-0-1.m3u8"));
        videoList.add(new VideoBean("电影-超级英雄", "https://aldirect.hls.huya.com/huyalive/30765679-2504742278-10757786168918540288-3049003128-10057-A-0-1_1200.m3u8"));
        videoList.add(new VideoBean("韩国-热舞", "http://aldirect.hls.huya.com/huyalive/30765679-2533159102-10879835498654728192-3049003136-10057-A-0-1_1200.m3u8"));
        videoList.add(new VideoBean("高清影院1", "http://dlhls.cdn.zhanqi.tv/zqlive/7032_0s2qn.m3u8"));
        videoList.add(new VideoBean("高清影院2", "http://dlhls.cdn.zhanqi.tv/zqlive/35349_iXsXw.m3u8"));
        videoList.add(new VideoBean("高清影院3", "http://dlhls.cdn.zhanqi.tv/zqlive/53346_ESoth.m3u8"));
        videoList.add(new VideoBean("高清影院4", "http://dlhls.cdn.zhanqi.tv/zqlive/182325_z3jRr.m3u8"));
        return videoList;
    }

    public static List<VideoBean> getCCTVVideoList() {
        List<VideoBean> videoList = new ArrayList<>(16);
        //央视
        videoList.add(new VideoBean("CCTV-1综合", "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8"));
        videoList.add(new VideoBean("CCTV-2财经", "http://ivi.bupt.edu.cn/hls/cctv2.m3u8"));
        videoList.add(new VideoBean("CCTV-3综艺", "http://ivi.bupt.edu.cn/hls/cctv3.m3u8"));
        videoList.add(new VideoBean("CCTV-4中文国际", "http://ivi.bupt.edu.cn/hls/cctv4.m3u8"));
        videoList.add(new VideoBean("CCTV-5体育", "http://ivi.bupt.edu.cn/hls/cctv5.m3u8"));
        videoList.add(new VideoBean("CCTV-6电影", "http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8"));
        videoList.add(new VideoBean("CCTV-7军事农业", "http://ivi.bupt.edu.cn/hls/cctv7.m3u8"));
        videoList.add(new VideoBean("CCTV-8电视剧", "http://ivi.bupt.edu.cn/hls/cctv8.m3u8"));
        videoList.add(new VideoBean("CCTV-9纪录", "http://ivi.bupt.edu.cn/hls/cctv9.m3u8"));
        videoList.add(new VideoBean("CCTV-10科教", "http://ivi.bupt.edu.cn/hls/cctv10.m3u8"));
        videoList.add(new VideoBean("CCTV-11戏曲", "http://ivi.bupt.edu.cn/hls/cctv11.m3u8"));
        videoList.add(new VideoBean("CCTV-12社会与法", "http://ivi.bupt.edu.cn/hls/cctv12.m3u8"));
        videoList.add(new VideoBean("CCTV-13新闻", "http://ivi.bupt.edu.cn/hls/cctv13.m3u8"));
        videoList.add(new VideoBean("CCTV-14少儿", "http://ivi.bupt.edu.cn/hls/cctv14.m3u8"));
        videoList.add(new VideoBean("CCTV-15音乐", "http://ivi.bupt.edu.cn/hls/cctv15.m3u8"));
        videoList.add(new VideoBean("CCTV-NEWS", "http://ivi.bupt.edu.cn/hls/cctv16.m3u8"));

        return videoList;
    }

//    CCTV-1综合,http://ivi.bupt.edu.cn/hls/cctv1.m3u8
//    CCTV-2财经,http://ivi.bupt.edu.cn/hls/cctv2.m3u8
//    CCTV-3综艺,http://ivi.bupt.edu.cn/hls/cctv3.m3u8
//    CCTV-4中文国际,http://ivi.bupt.edu.cn/hls/cctv4.m3u8
//    CCTV-5体育,http://ivi.bupt.edu.cn/hls/cctv5.m3u8
//    CCTV-6电影,http://ivi.bupt.edu.cn/hls/cctv6.m3u8
//    CCTV-7军事农业,http://ivi.bupt.edu.cn/hls/cctv7.m3u8
//    CCTV-8电视剧,http://ivi.bupt.edu.cn/hls/cctv8.m3u8
//    CCTV-9纪录,http://ivi.bupt.edu.cn/hls/cctv9.m3u8
//    CCTV-10科教,http://ivi.bupt.edu.cn/hls/cctv10.m3u8
//    CCTV-11戏曲,http://ivi.bupt.edu.cn/hls/cctv11.m3u8
//    CCTV-12社会与法,http://ivi.bupt.edu.cn/hls/cctv12.m3u8
//    CCTV-13新闻,http://ivi.bupt.edu.cn/hls/cctv13.m3u8
//    CCTV-14少儿,http://ivi.bupt.edu.cn/hls/cctv14.m3u8
//    CCTV-15音乐,http://ivi.bupt.edu.cn/hls/cctv15.m3u8
//    CCTV-NEWS,http://ivi.bupt.edu.cn/hls/cctv16.m3u8
//    北京卫视,http://ivi.bupt.edu.cn/hls/btv1.m3u8
//    北京文艺,http://ivi.bupt.edu.cn/hls/btv2.m3u8
//    北京科教,http://ivi.bupt.edu.cn/hls/btv3.m3u8
//    北京影视,http://ivi.bupt.edu.cn/hls/btv4.m3u8
//    北京财经,http://ivi.bupt.edu.cn/hls/btv5.m3u8
//    北京体育,http://ivi.bupt.edu.cn/hls/btv6.m3u8
//    北京生活,http://ivi.bupt.edu.cn/hls/btv7.m3u8
//    北京青年,http://ivi.bupt.edu.cn/hls/btv8.m3u8
//    北京新闻,http://ivi.bupt.edu.cn/hls/btv9.m3u8
//    北京卡酷少儿,http://ivi.bupt.edu.cn/hls/btv10.m3u8

//    深圳卫视,http://ivi.bupt.edu.cn/hls/sztv.m3u8
//    安徽卫视,http://ivi.bupt.edu.cn/hls/ahtv.m3u8
//    河南卫视,http://ivi.bupt.edu.cn/hls/hntv.m3u8
//    陕西卫视,http://ivi.bupt.edu.cn/hls/sxtv.m3u8
//    吉林卫视,http://ivi.bupt.edu.cn/hls/jltv.m3u8
//    广东卫视,http://ivi.bupt.edu.cn/hls/gdtv.m3u8
//    山东卫视,http://ivi.bupt.edu.cn/hls/sdtv.m3u8
//    湖北卫视,http://ivi.bupt.edu.cn/hls/hbtv.m3u8
//    广西卫视,http://ivi.bupt.edu.cn/hls/gxtv.m3u8
//    河北卫视,http://ivi.bupt.edu.cn/hls/hebtv.m3u8
//    西藏卫视,http://ivi.bupt.edu.cn/hls/xztv.m3u8
//    内蒙古卫视,http://ivi.bupt.edu.cn/hls/nmtv.m3u8
//    青海卫视,http://ivi.bupt.edu.cn/hls/qhtv.m3u8
//    四川卫视,http://ivi.bupt.edu.cn/hls/sctv.m3u8
//    江苏卫视,http://ivi.bupt.edu.cn/hls/jstv.m3u8
//    天津卫视,http://ivi.bupt.edu.cn/hls/tjtv.m3u8
//    山西卫视,http://ivi.bupt.edu.cn/hls/sxrtv.m3u8
//    辽宁卫视,http://ivi.bupt.edu.cn/hls/lntv.m3u8
//    厦门卫视,http://ivi.bupt.edu.cn/hls/xmtv.m3u8
//    新疆卫视,http://ivi.bupt.edu.cn/hls/xjtv.m3u8
//    东方卫视,http://ivi.bupt.edu.cn/hls/dftv.m3u8
//    黑龙江卫视,http://ivi.bupt.edu.cn/hls/hljtv.m3u8
//    湖南卫视,http://ivi.bupt.edu.cn/hls/hunantv.m3u8
//    云南卫视,http://ivi.bupt.edu.cn/hls/yntv.m3u8
//    江西卫视,http://ivi.bupt.edu.cn/hls/jxtv.m3u8
//    福建东南卫视,http://ivi.bupt.edu.cn/hls/dntv.m3u8
//    浙江卫视,http://ivi.bupt.edu.cn/hls/zjtv.m3u8
//    贵州卫视,http://ivi.bupt.edu.cn/hls/gztv.m3u8
//    宁夏卫视,http://ivi.bupt.edu.cn/hls/nxtv.m3u8
//    甘肃卫视,http://ivi.bupt.edu.cn/hls/gstv.m3u8
//    重庆卫视,http://ivi.bupt.edu.cn/hls/cqtv.m3u8
//    兵团卫视,http://ivi.bupt.edu.cn/hls/bttv.m3u8
//    旅游卫视,http://ivi.bupt.edu.cn/hls/lytv.m3u8
}
