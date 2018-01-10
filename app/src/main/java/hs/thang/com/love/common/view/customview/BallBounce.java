package hs.thang.com.love.view.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import hs.thang.com.love.R;

public class BallBounce extends View {

    private static final String TAG = "BallBounce";

    int mScreenW;
    int mScreenH;
    int mBallX;
    int mBallY;
    int initialY;
    int mBallW;
    int mBallH;
    int mAngle;
    float mVerticalSpeed;
    float mAcceleration; // gia toc
    Bitmap mBitmapBall, mBackGround;

    public BallBounce(Context context) {
        super(context);
        Log.i(TAG, "BallBounce: ");
        mBitmapBall = BitmapFactory.decodeResource(getResources(), R.drawable.ball); //load a mBitmapBall image
        //mBackGround = BitmapFactory.decodeResource(getResources(), R.drawable.ball_back); //load a background
        mBallW = mBitmapBall.getWidth();
        mBallH = mBitmapBall.getHeight();
        mAcceleration = 0.2f; //acceleration
        mVerticalSpeed = 0; //vertical speed
        initialY = 100; //Initial vertical position.
        mAngle = 0; //Start value for rotation mAngle.
    }

    public BallBounce(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "BallBounce: 2");
        mBitmapBall = BitmapFactory.decodeResource(getResources(), R.drawable.ball); //load a mBitmapBall image
        //mBackGround = BitmapFactory.decodeResource(getResources(), R.drawable.ball_back); //load a background
        mBallW = mBitmapBall.getWidth();
        mBallH = mBitmapBall.getHeight();
        mAcceleration = 0.2f; //acceleration
        mVerticalSpeed = 0; //vertical speed
        initialY = 100; //Initial vertical position.
        mAngle = 0; //Start value for rotation mAngle.
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged");
        mScreenW = w;
        mScreenH = h;
        //mBackGround = Bitmap.createScaledBitmap(mBackGround, w, h, true); //Resize background to fit the screen.
        mBallX = (int) (mScreenW / 2) - (mBallW / 2); //Centre mBitmapBall into the centre of the screen.
        mBallY = (int) (mScreenH / 2) - (mBallH / 2) + 100;
       // mBallY = mInitialY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: mBallY =" + mBallY);
        Log.i(TAG, "onDraw: mBackGround =" + mBackGround);
        Log.i(TAG, "onDraw: mBitmapBall =" + mBitmapBall);
        Log.i(TAG, "onDraw: mBallY =" + mBallY +", mScreenH =" + mScreenH +", mBallH =" + mBallH +", xxx=" + (mBallY > (mScreenH - mBallH)));

        //Draw background.
        //canvas.drawBitmap(mBackGround, 0, 0, null);

        //Compute roughly mBitmapBall speed and location.
        /*mBallY += (int) mVerticalSpeed; //Increase or decrease vertical position.
        if (mBallY > (mScreenH - mBallH)) {
            mVerticalSpeed = (-1) * mVerticalSpeed; //Reverse speed when bottom hit.
        }
        mVerticalSpeed += mAcceleration; //Increase or decrease speed.*/

        //Increase rotating mAngle.
        mAngle += 5;
        if (mAngle > 360) {
            mAngle = 0;
        }


        //Draw mBitmapBall
        //canvas.save(); //Save the position of the canvas.
        canvas.rotate(mAngle, mBallX + (mBallW / 2), mBallY + (mBallH / 2)); //Rotate the canvas.
        canvas.drawBitmap(mBitmapBall, mBallX, mBallY, null); //Draw the mBitmapBall on the rotated canvas.
       // canvas.restore(); //Rotate the canvas back so that it looks like mBitmapBall has rotated.

        //Call the next frame.
        invalidate();
    }
}
