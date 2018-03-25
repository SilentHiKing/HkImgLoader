package com.hiking.hkimgloader.loader;


import com.hiking.hkimgloader.request.BitmapRequest;

/**
 * Created by Administrator on 2018/3/24.
 */

public interface Loader {
    /**
     * 加载图片
     * @param request
     */
    void loadImage(BitmapRequest request);
}
