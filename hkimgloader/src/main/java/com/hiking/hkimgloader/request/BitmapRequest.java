package com.hiking.hkimgloader.request;

import android.widget.ImageView;

import com.hiking.hkimgloader.config.DisplayConfig;
import com.hiking.hkimgloader.loader.HkImageLoader;
import com.hiking.hkimgloader.policy.LoadPolicy;
import com.hiking.hkimgloader.utils.MD5Utils;

import java.lang.ref.SoftReference;

/**
 * Created by Administrator on 2018/3/24.
 */

public class BitmapRequest  implements Comparable<BitmapRequest> {
    //持有imageview的软引用
    private SoftReference<ImageView> imageViewSoft;
    //图片路径
    private String imageUrl;
    //MD5的图片路径
    private String imageUriMD5;
     //下载完成监听
    public HkImageLoader.ImageListener imageListener;

    private DisplayConfig displayConfig;
    public BitmapRequest(ImageView imageView,String imageUrl,DisplayConfig displayConfig,
                         HkImageLoader.ImageListener imageListener) {
        this.imageViewSoft=new SoftReference<ImageView>(imageView);
        //设置可见的Image的Tag，要下载的图片路径
        imageView.setTag(imageUrl);
        this.imageUrl=imageUrl;
        this.imageUriMD5= MD5Utils.toMD5(imageUrl);
        if(displayConfig!=null)
        {
            this.displayConfig=displayConfig;
        }
        this.imageListener = imageListener;
    }

    //加载策略
    private LoadPolicy loadPolicy= HkImageLoader.getInstance().getConfig().getLoadPolicy();
    /**
     * 编号
     */
    private int serialNo;


    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitmapRequest that = (BitmapRequest) o;

        if (serialNo != that.serialNo) return false;
        return loadPolicy != null ? loadPolicy.equals(that.loadPolicy) : that.loadPolicy == null;

    }

    @Override
    public int hashCode() {
        int result = loadPolicy != null ? loadPolicy.hashCode() : 0;
        result = 31 * result + serialNo;
        return result;
    }
    public ImageView getImageView()
    {
        return  imageViewSoft.get();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageUriMD5() {
        return imageUriMD5;
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

    public LoadPolicy getLoadPolicy() {
        return loadPolicy;
    }

    @Override
    public int compareTo(BitmapRequest o) {
        return loadPolicy.compareto(o,this);
    }
}
