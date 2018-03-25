package com.hiking.hkimgloader.policy;

import com.hiking.hkimgloader.request.BitmapRequest;

/**
 * Created by Administrator on 2018/3/24.
 */
public interface LoadPolicy {
    /**
     * 两个BItmapRequest进行优先级比较
     * @param request1
     * @param request2
     * @return
     */
    int compareto(BitmapRequest request1, BitmapRequest request2);
}