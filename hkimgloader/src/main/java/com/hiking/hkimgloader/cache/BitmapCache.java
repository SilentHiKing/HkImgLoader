package com.hiking.hkimgloader.cache;

import android.graphics.Bitmap;
import com.hiking.hkimgloader.request.BitmapRequest;

/**
 * Created by Administrator on 2018/3/24.
 */

public interface BitmapCache {
    /**
     * 缓存bitmap
     * @param bitmap
     */
    void put(BitmapRequest request, Bitmap bitmap);

    /**
     * 通过请求取Bitmap
     * @param request
     * @return
     */
    Bitmap get(BitmapRequest request);

    /**
     * 移除缓存
     * @param request
     */
    void remove(BitmapRequest request);
}