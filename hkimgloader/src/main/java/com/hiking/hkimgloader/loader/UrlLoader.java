package com.hiking.hkimgloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.hiking.hkimgloader.request.BitmapRequest;
import com.hiking.hkimgloader.utils.BitmapDecoder;
import com.hiking.hkimgloader.utils.ImageViewHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/3/24.
 */

public class UrlLoader extends  AbstarctLoader {

    private final static String TAG =UrlLoader.class.getName();
    //缓存路径
    private String mCacheDir = "Cache_Image";
    @Override
    protected Bitmap onLoad(final BitmapRequest request) {
        //先下载  后读取
        downloadImgByUrl(request.getImageUrl(),getCache(request.getImageUriMD5()));
        BitmapDecoder decoder=new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(getCache(request.getImageUriMD5()).getAbsolutePath(),options);
            }
        };
        return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView())
                    ,ImageViewHelper.getImageViewHeight(request.getImageView()));
    }

    public static boolean downloadImgByUrl(String urlStr, File file)
    {
        FileOutputStream fos = null;
        InputStream is = null;
        try
        {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            is = conn.getInputStream();
            fos = new FileOutputStream(file);
            byte[] buf = new byte[512];
            int len = 0;
            while ((len = is.read(buf)) != -1)
            {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return true;

        } catch (Exception e)
        {
            Log.e(TAG,"网络下载失败 "+e.toString());
            e.printStackTrace();
        } finally
        {
            try
            {
                if (is != null)
                    is.close();
            } catch (IOException e)
            {
            }

            try
            {
                if (fos != null)
                    fos.close();
            } catch (IOException e)
            {
            }
        }

        return false;

    }
    private File getCache(String unipue)
    {
        File file=new File(Environment.getExternalStorageDirectory(),mCacheDir);
        Log.d(TAG,"URLLoader getCache ="+file);
        if(!file.exists())
        {
            boolean flag=file.mkdir();
            Log.e(TAG,"URLLoader getCache(String unipue) file.mkdir() = "+flag +"如果是false，请动态申请存储权限");
        }
        return new File(file,unipue);
    }

}
