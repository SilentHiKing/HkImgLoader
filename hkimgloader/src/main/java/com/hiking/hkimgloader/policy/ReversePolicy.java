package com.hiking.hkimgloader.policy;


import com.hiking.hkimgloader.request.BitmapRequest;
/**
 * Created by Administrator on 2018/3/24.
 */

public class ReversePolicy implements  LoadPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request2.getSerialNo()-request1.getSerialNo();
    }
}
