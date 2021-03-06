package com.hiking.hkimgloader.request;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/3/24.
 */

public class RequestQueue {


    /**
     *阻塞式队列
     * 多线程共享
     * 生产效率 和消费效率想查太远了。
     * disPlayImage()
     * 使用优先级队列
     * 优先级高的队列先被消费
     * 每 一个产品都有编号
     */
    private BlockingQueue<BitmapRequest> mRequestQueue=new PriorityBlockingQueue<>();
    /**
     * 转发器的数量
     */
    private int  threadCount;
    //i++  ++i  能 1   不能 2
    private AtomicInteger i=new AtomicInteger(0);
    //一组转发器
    private RequestDispatcher[] mDispachers;

    public RequestQueue(int threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * 添加请求对象
     * @param request
     */
    public void addRequest(BitmapRequest request)
    {
        //判断请求队列是否包含请求
        if(!mRequestQueue.contains(request))
        {
            //给请求进行编号
            request.setSerialNo(i.incrementAndGet());
            mRequestQueue.add(request);
        }else
        {
            Log.i(TAG,"请求已经存在 编号："+request.getSerialNo());
        }
    }

    /**
     * 开启请求
     */
    public void start()
    {
        //先停止
        stop();
        startDispatchers();

    }

    /**
     * 开启转发器
     */
    private void startDispatchers() {
        mDispachers=new RequestDispatcher[threadCount];
        for(int i=0;i<threadCount;i++)
        {
            RequestDispatcher p=new RequestDispatcher(mRequestQueue);
            mDispachers[i]=p;
            mDispachers[i].start();
        }
    }

    /**
     * 停止
     */
    public void stop()
    {
        if (this.mDispachers == null ){return;}
        for(int i = 0; i < this.mDispachers.length; ++i) {
            if(this.mDispachers[i] != null) {
                this.mDispachers[i].interrupt();
            }
        }
    }

    public boolean isAlive(){
        if(this.mDispachers[0] != null) {
            return this.mDispachers[0].isAlive();
        }
        return false;
    }
}
