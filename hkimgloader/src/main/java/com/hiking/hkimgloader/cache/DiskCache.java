package com.hiking.hkimgloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.hiking.hkimgloader.disk.DiskLruCache;
import com.hiking.hkimgloader.disk.IOUtil;
import com.hiking.hkimgloader.request.BitmapRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2018/3/24.
 */

public class DiskCache implements  BitmapCache {
    private static final String TAG ="DiskCache";
    private static DiskCache mDiskCache;
    //缓存路径
    private String mCacheDir = "Cache_Image";
    //MB
    private static final int MB = 1024 * 1024;
    //jackwharton的杰作
    private volatile DiskLruCache mDiskLruCache;

    private DiskCache(Context context)
    {
        iniDiskCache(context);
    }
    public static DiskCache getInstance(Context context) {
        if(mDiskCache==null)
        {
            synchronized (DiskCache.class)
            {
                if(mDiskCache==null)
                {
                    Log.e(TAG,"enter DiskCache getInstance");
                    mDiskCache=new DiskCache(context);
                }
            }
        }
        return mDiskCache;
    }
    private void iniDiskCache(Context context) {
        //得到缓存的目录  android/data/data/com.hiking.hkimgloader/cache/Image
        Log.e(TAG,"enter DiskCache iniDiskCache");
        File directory=getDiskCache(mCacheDir,context);
        Log.e(TAG,"得到缓存的目录 directory="+directory);
        if(!directory.exists())
        {
            boolean flag= directory.mkdir();
            Log.e(TAG,"DiskCache.iniDiskCache file.mkdir() = "+flag +"如果是false，请动态申请存储权限");
        }
        try {
            //最后一个参数 指定缓存容量
            mDiskLruCache=DiskLruCache.open(directory,1,1,50*MB);
            Log.e(TAG,"success to create mDiskLruCache");
        } catch (IOException e) {
            Log.e(TAG,"fail to create mDiskLruCache  "+e.toString());
            e.printStackTrace();
        }
    }

    private File getDiskCache(String mCacheDir, Context context) {
        String cachePath;
        //默认缓存路径

        return new File(context.getCacheDir(),mCacheDir);
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        DiskLruCache.Editor edtor=null;
        OutputStream os=null;
        try {
            //路径必须是合法字符
            edtor=mDiskLruCache.edit(request.getImageUriMD5());
            os=edtor.newOutputStream(0);
            if(persistBitmap2Disk(bitmap,os))
            {
                edtor.commit();
            }else {
                edtor.abort();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean persistBitmap2Disk(Bitmap bitmap, OutputStream os) {
        BufferedOutputStream bos=new BufferedOutputStream(os);

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            IOUtil.closeQuietly(bos);
        }
        return true;

    }

    @Override
    public Bitmap get(BitmapRequest request) {
        try {
            DiskLruCache.Snapshot snapshot=mDiskLruCache.get(request.getImageUriMD5());
            if(snapshot!=null)
            {
                InputStream inputStream=snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(BitmapRequest request) {
        try {
            mDiskLruCache.remove(request.getImageUriMD5());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
