package com.hiking.hkimgloader.policy;

import com.hiking.hkimgloader.request.BitmapRequest;

/**
 * Created by Administrator on 2018/3/24.
 */

public class SerialPolicy implements  LoadPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request1.getSerialNo()-request2.getSerialNo();
    }
}
