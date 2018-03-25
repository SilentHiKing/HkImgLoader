package com.hiking.hkimgloader.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.hiking.hkimgloader.R;
import com.hiking.hkimgloader.cache.DoubleCache;
import com.hiking.hkimgloader.config.DisplayConfig;
import com.hiking.hkimgloader.config.ImageLoaderConfig;
import com.hiking.hkimgloader.policy.ReversePolicy;
import com.hiking.hkimgloader.request.BitmapRequest;
import com.hiking.hkimgloader.request.RequestQueue;

/**
 * Created by Administrator on 2018/3/24.
 */

public class HkImageLoader {


    //配置
    private ImageLoaderConfig config;
    //请求队列
    private RequestQueue mRequestQueue;
    //单例对象
    private static volatile  HkImageLoader mInstance;
    private HkImageLoader()
    {

    }
    private HkImageLoader(ImageLoaderConfig imageLoaderConfig)
    {
        this.config=imageLoaderConfig;
        mRequestQueue=new RequestQueue(config.getThreadCount());
        //开启请求队列
        mRequestQueue.start();
    }

    /**
     * 获取单例方法
     * 第一次调用
     * @param config
     * @return
     */
    public  static HkImageLoader getInstance(ImageLoaderConfig config)
    {
        if(mInstance==null)
        {
            synchronized (HkImageLoader.class)
            {
                if(mInstance==null)
                {
                    mInstance=new HkImageLoader(config);
                }
            }

        }
        return  mInstance;
    }

    /**
     *
     * @param context
     * @return
     */
    public static HkImageLoader getInstance(Context context)
    {
        if(mInstance==null)
        {
            synchronized (HkImageLoader.class)
            {
                if(mInstance==null)
                {
                    //配置
                    ImageLoaderConfig.Builder build = new ImageLoaderConfig.Builder();
                    build.setThreadCount(3) //线程数量
                            .setLoadPolicy(new ReversePolicy()) //加载策略
                            .setCachePolicy(new DoubleCache(context)) //缓存策略
                            .setLoadingImage(R.drawable.loading)
                            .setFaildImage(R.drawable.no_found);
                    ImageLoaderConfig config = build.build();

                    mInstance=new HkImageLoader(config);
                }
            }

        }
        return  mInstance;
    }

    public static HkImageLoader getInstance()
    {
        if(mInstance==null)
        {
            throw  new UnsupportedOperationException("没有初始化");
        }
        return mInstance;
    }

    /**
     * 把传入的数据封装成BitmapRequest，并把它加入到RequestQueue
     * @param imageView
     * @param uri  http:   file 开头
     */
    public  void displayImage(ImageView imageView,String uri)
    {
        displayImage(imageView,uri,null,null);
    }

    /**
     * 重载
     * @param imageView
     * @param uri
     * @param displayConfig
     * @param imageListener
     */
    public  void displayImage(ImageView imageView, String uri
            , DisplayConfig displayConfig, ImageListener imageListener)
    {
        //实例化一个请求
        BitmapRequest bitmapRequest=new BitmapRequest(imageView,uri,displayConfig,imageListener);
        //添加到队列里面
        mRequestQueue.addRequest(bitmapRequest);
    }
    public static  interface ImageListener{
        /**
         *
         * @param imageView
         * @param bitmap
         * @param uri
         */
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }

    /**
     * 拿到全局配置，Loader获取缓存方式、显式方式和Request得到加载策略（PriorityBlockingQueue）
     * @return
     */
    public ImageLoaderConfig getConfig() {
        return config;
    }


}
