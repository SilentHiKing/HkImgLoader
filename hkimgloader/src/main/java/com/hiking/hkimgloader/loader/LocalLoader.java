package com.hiking.hkimgloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.hiking.hkimgloader.request.BitmapRequest;
import com.hiking.hkimgloader.utils.BitmapDecoder;
import com.hiking.hkimgloader.utils.ImageViewHelper;

import java.io.File;

/**
 * Created by Administrator on 2018/3/24.
 */

public class LocalLoader extends  AbstarctLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        //得到本地图片的路径
        final String path= Uri.parse(request.getImageUrl()).getPath();
        File file=new File(path);
        if(!file.exists())
        {
            return null;
        }
        BitmapDecoder decoder=new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path,options);
            }
        };

        return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView())
                ,ImageViewHelper.getImageViewHeight(request.getImageView()));
    }
}
