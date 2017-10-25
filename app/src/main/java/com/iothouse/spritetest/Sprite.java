package com.iothouse.spritetest;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by bj on 2017/10/25.
 */

public class Sprite {
    public static final int DOWN = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;

    public float mPositionX;
    public float mPositionY;
    public int mWidth;
    public int mHeight;

    //精灵行走速度
    public float speed;
    //精灵当前行走方向
    public int direction;

    //动画帧对象
    public FrameAnimation mFrameAnimation;

    public Sprite(FrameAnimation frameAnimation, float position_x, float position_y,
                  int width, int height, float speed) {
        this.mFrameAnimation = frameAnimation;
        this.mPositionX = position_x;
        this.mPositionY = position_y;
        this.mWidth = width;
        this.mHeight = height;
        this.speed = speed;

        direction = DOWN;
    }

    public void updatePosition(long deltaTime) {
        switch (direction) {
            case LEFT:
                //让物体的移动速度不受机器性能的影响,每帧精灵需要移动的距离为：移动速度*时间间隔
                this.mPositionX = this.mPositionX - (float) (this.speed * deltaTime);
                break;
            case DOWN:
                this.mPositionY = this.mPositionY + (float) (this.speed * deltaTime);
                break;
            case RIGHT:
                this.mPositionX = this.mPositionX + (float) (this.speed * deltaTime);
                break;
            case UP:
                this.mPositionY = this.mPositionY - (float) (this.speed * deltaTime);
                break;
        }
    }

    /**
     * 根据精灵的当前位置判断是否改变行走方向
     */
    public void setDirection() {
        if (this.mPositionX <= 0
                && (this.mPositionY + this.mHeight) < GameSurfaceView.SCREEN_HEIGHT) {
            if (this.mPositionX < 0)
                this.mPositionX = 0;
            this.direction = Sprite.DOWN;
        } else if ((this.mPositionY + this.mHeight) >= GameSurfaceView.SCREEN_HEIGHT
                && (this.mPositionX + this.mWidth) < GameSurfaceView.SCREEN_WIDTH) {
            if ((this.mPositionY + this.mHeight) > GameSurfaceView.SCREEN_HEIGHT)
                this.mPositionY = GameSurfaceView.SCREEN_HEIGHT - this.mHeight;
            this.direction = Sprite.RIGHT;
        } else if ((this.mPositionX + this.mWidth) >= GameSurfaceView.SCREEN_WIDTH
                && this.mPositionY > 0) {
            if ((this.mPositionX + this.mWidth) > GameSurfaceView.SCREEN_WIDTH)
                this.mPositionX = GameSurfaceView.SCREEN_WIDTH - this.mWidth;
            this.direction = Sprite.UP;
        } else {
            if (this.mPositionY < 0)
                this.mPositionY = 0;
            this.direction = Sprite.LEFT;
        }

    }

    public void draw(Canvas canvas,long deltaTime) {

        Bitmap bitmap = mFrameAnimation.nextFrame(direction, deltaTime);
        if (null != bitmap) {
            canvas.drawBitmap(bitmap, mPositionX, mPositionY, null);
        }
    }
}
