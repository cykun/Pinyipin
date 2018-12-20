package com.example.imageselector;

import android.app.Activity;
import android.content.Context;

import com.donkingliang.imageselector.utils.ImageSelector;

public class ImageSelect {
    private Context mContent;
    private static final int REQUEST_CODE1=1;  //发送码
    private static final int REQUEST_CODE2=2;  //发送码
    private static final int REQUEST_CODE3=3;  //发送码
    public ImageSelect(Context context)
    {
        this.mContent=context;
    }
    public void Imageselect1(Activity activity)   //仅限选择一张图片
    {
        ImageSelector.builder()
                .useCamera(false)
                .setSingle(true)
                .setViewImage(true)
                .start(activity,REQUEST_CODE1);
    }
    public void Imageselect2(Activity activity) //可实现选择9张图片
    {
        ImageSelector.builder()
                .useCamera(false)
                .setSingle(false)  //设置是否单选
                .setViewImage(true) //是否点击放大图片查看,，默认为true
                .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                .start(activity, REQUEST_CODE2);
    }
    public void Imageselect3(Activity activity) //选择一张图片且可以剪切
    {
        ImageSelector.builder()
                .useCamera(false) //设置是否使用相机
                .setCrop(true)  // 设置是否使用图片剪切功能。
                .setSingle(true)  //设置是否单选
                .setViewImage(true) //是否点击放大图片查看,，默认为true
                .start(activity, REQUEST_CODE3); // 打开相册
    }
}
