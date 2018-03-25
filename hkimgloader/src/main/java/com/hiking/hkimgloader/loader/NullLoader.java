package com.hiking.hkimgloader.loader;

import android.graphics.Bitmap;

import com.hiking.hkimgloader.request.BitmapRequest;


/**
 * Created by Administrator on 2018/3/24.
 */

public class NullLoader extends  AbstarctLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        return null;
    }
}
