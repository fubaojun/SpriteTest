package com.iothouse.spritetest;

import android.graphics.Bitmap;

/**
 * Created by bj on 2017/10/25.
 */

public class FrameAnimation {

    private static final long FRAMEDURATION = 200;
    public static final int TOTAL_STEP = 4;

    /**动画显示的需要的资源 */
    private Bitmap[][] mBitmaps;
    private long mTickTime = 0;

    //精灵当前行走步数
    public int step;

    public FrameAnimation(Bitmap[][] bitmaps) {
        this.mBitmaps = bitmaps;
    }

    public Bitmap nextFrame(int direction, long deltaTime) {
        mTickTime += deltaTime;
        if (mTickTime >= FRAMEDURATION){
            mTickTime = 0;
            if (++step == TOTAL_STEP) {
                step = 0;
            }
        }
        return mBitmaps[direction][step];

//        if (null == lastBitmapTime) {
//            // 第一次执行
//            lastBitmapTime = System.currentTimeMillis();
//            return bitmaps[step = 0];
//        }
//
//        // 第X次执行
//        long nowTime = System.currentTimeMillis();
//        if (nowTime - lastBitmapTime <= duration[step]) {
//            // 如果还在duration的时间段内,则继续返回当前Bitmap
//            // 如果duration的值小于0,则表明永远不失效,一般用于背景
//            return bitmaps[step];
//        }
//        lastBitmapTime = nowTime;
//        return bitmaps[step++];// 返回下一Bitmap
    }
}
