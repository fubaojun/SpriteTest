package com.iothouse.spritetest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.io.InputStream;

/**
 * Created by bjfu on 2017/10/24.
 */

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private String TAG = "GameSurfaceView";
    public static final int SCREEN_WIDTH = 500;
    public static final  int SCREEN_HEIGHT = 660;;

    public Bitmap mBitmapResource = null;
    public Bitmap[][] mBitmaps = null;
    public Paint mPaint = null;
    public SurfaceHolder mSurfaceHolder = null;

    private DrawThread mDrawThread = null;

    public int spriteWidth = 0;
    public int spriteHeight = 0;

    //动画帧对象
    public FrameAnimation mFrameAnimation;
    //精灵
    public Sprite mSprite;

    public GameSurfaceView(Context context) {
        super(context);
        Log.e(TAG,"-- GameSurfaceView --");
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);//必须做这一步，不然 那个回调函数不会调用也不出图

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;

        @SuppressLint("ResourceType") InputStream is = context.getResources().openRawResource(R.drawable.sprite);
        mBitmapResource = BitmapFactory.decodeStream(is, null, opt);
        mBitmaps = generateBitmapArray(mBitmapResource, 4, 4);

        mFrameAnimation = new FrameAnimation(mBitmaps);
        mSprite = new Sprite(mFrameAnimation,0,0,spriteWidth,spriteHeight,0.1f);

        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);// 画笔为绿色
        mPaint.setTextSize(40);
        mPaint.setStrokeWidth(2);// 设置画笔粗细
    }

    public Bitmap[][] generateBitmapArray(Bitmap bitmapSRC, int row, int col){
        Bitmap[][] bitmaps = new Bitmap[row][col];
        this.spriteWidth = bitmapSRC.getWidth()/row;
        this.spriteHeight = bitmapSRC.getHeight()/row;

        for (int i=0; i<row; i++){
            for (int j=0; j<col; j++){
                bitmaps[i][j] = Bitmap.createBitmap(bitmapSRC,
                        this.spriteWidth*j, this.spriteHeight*i,
                        this.spriteWidth, this.spriteHeight);

            }
        }

        return bitmaps;
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG,"-- surfaceCreated --");
        Canvas canvas = holder.lockCanvas();
        canvas.drawBitmap(mBitmapResource,0,0,null);
        Log.e(TAG,"-- surfaceCreated -drawBitmap-");
        holder.setFixedSize(500,660);//这里会把Surface 缩放到 SurfaceView 上显示
        Log.e(TAG,"-- surfaceCreated -setFixedSize-");
        float x = 500;
        float y = 660;

        canvas.drawLine(0,0,x,y,mPaint);
        canvas.drawLine(0,y,x,y,mPaint);
        canvas.drawLine(x,0,x,y,mPaint);
        canvas.drawText(""+x+","+y,120,120,mPaint);
        holder.unlockCanvasAndPost(canvas);

        if(null == mDrawThread){
            mDrawThread = new DrawThread();
            mDrawThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG,"-- surfaceChanged --");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG,"-- surfaceDestroyed --");

    }

    private class DrawThread extends Thread {
        public boolean isRunning = false;
        public float location_x = 0;
        public float location_y = 0;
        public int location_step = 0;
        public static final int total_step = 4;

        public DrawThread() {
            this.isRunning = true;
        }

        @Override
        public void run() {
            Canvas canvas = null;
            while (isRunning){
                if (++location_step == total_step) {
                    location_step = 0;
                }
                location_x = -(330*location_step/total_step);
                mSprite.updatePosition(30);
                mSprite.setDirection();
//                location_y = -(250*location_step/total_step);

                canvas = mSurfaceHolder.lockCanvas();
                canvas.drawColor(Color.BLACK);
//                canvas.drawBitmap(mBitmapResource,location_x,location_y,null);
//                canvas.drawBitmap(mBitmaps[0][location_step], 0,0,null);
//                canvas.drawBitmap(mBitmaps[1][location_step], 0,300,null);
//                canvas.drawBitmap(mBitmaps[2][location_step], 300,0,null);
//                canvas.drawBitmap(mBitmaps[3][location_step], 300,300,null);
                mSprite.draw(canvas);
                mSurfaceHolder.unlockCanvasAndPost(canvas);

                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
