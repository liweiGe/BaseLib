package com.example.commom_view.ninelayout;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 图片信息的bean对象为了统一
 */
public class ImageData implements Serializable, Parcelable {
    public String url;
    public String text;
    /**
     * 自己添加的字段,可以加快评论列表里视频的显示,虽然glide可以扔一个视频链接,但是展示速度上没有纯图片快
     */
    public String videoUrl;

    public int realWidth;
    public int realHeight;


    public int width;
    public int height;

    public ImageData(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.text);
        dest.writeString(this.videoUrl);
        dest.writeInt(this.realWidth);
        dest.writeInt(this.realHeight);

        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    protected ImageData(Parcel in) {
        this.url = in.readString();
        this.text = in.readString();
        this.videoUrl = in.readString();
        this.realWidth = in.readInt();
        this.realHeight = in.readInt();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Creator<ImageData> CREATOR = new Creator<ImageData>() {
        @Override
        public ImageData createFromParcel(Parcel source) {
            return new ImageData(source);
        }

        @Override
        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };
}
