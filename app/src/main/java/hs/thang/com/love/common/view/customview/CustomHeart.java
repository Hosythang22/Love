package hs.thang.com.love.common.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import hs.thang.com.love.R;

public class CustomHeart extends View {

    private static final String TAG = "CustomHeart";

    private Path mPath;
    private Paint mPaint;
    private float mWidth;
    private float mHeight;

    private float mMinWidth;
    private float mMinHeight;

    private float mMaxWidth;
    private float mMaxHeight;

    private float mOffsetX, mOffsetY;

    public CustomHeart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);

        mMaxWidth = getContext().getResources().getDimension(R.dimen.heart_max_width);
        mMaxHeight = getContext().getResources().getDimension(R.dimen.heart_max_height);

        mMinWidth = getContext().getResources().getDimension(R.dimen.heart_min_width);
        mMinHeight = getContext().getResources().getDimension(R.dimen.heart_min_height);

        mWidth = mMaxWidth;
        mHeight = mMaxHeight;
    }

    public CustomHeart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Fill the canvas with background color
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setShader(null);
        mPath.reset();

        if (mWidth-- < mMinWidth) {
            mWidth = mMaxWidth;
        }

        if (mHeight-- < mMinHeight) {
            mHeight = mMaxHeight;
        }

        mOffsetX = (getWidth() - mWidth) / 2.0f;
        mOffsetY = (getHeight() - mHeight) / 2.0f;

        // Starting point
        mPath.moveTo(centerCoor(mWidth / 2, mHeight / 5).x, centerCoor(mWidth / 2, mHeight / 5).y);

        // Upper left mPath
        mPath.cubicTo(centerCoor(5 * mWidth / 14, 0).x, centerCoor(5 * mWidth / 14, 0).y,
                centerCoor(0, mHeight / 15).x, centerCoor(0, mHeight / 15).y,
                centerCoor(mWidth / 28, 2 * mHeight / 5).x, centerCoor(mWidth / 28, 2 * mHeight / 5).y);

        // Lower left mPath
        mPath.cubicTo(centerCoor(mWidth / 14, 2 * mHeight / 3).x ,centerCoor(mWidth / 14, 2 * mHeight / 3).y,
                centerCoor(3 * mWidth / 7, 5 * mHeight / 6).x, centerCoor(3 * mWidth / 7, 5 * mHeight / 6).y,
                centerCoor(mWidth / 2, mHeight).x, centerCoor(mWidth / 2, mHeight).y);

        // Lower right mPath
        mPath.cubicTo(centerCoor(4 * mWidth / 7, 5 * mHeight / 6).x, centerCoor(4 * mWidth / 7, 5 * mHeight / 6).y,
                centerCoor(13 * mWidth / 14, 2 * mHeight / 3).x, centerCoor(13 * mWidth / 14, 2 * mHeight / 3).y,
                centerCoor(27 * mWidth / 28, 2 * mHeight / 5).x, centerCoor(27 * mWidth / 28, 2 * mHeight / 5).y);

        // Upper right mPath
        mPath.cubicTo(centerCoor(mWidth, mHeight / 15).x, centerCoor(mWidth, mHeight / 15).y,
                centerCoor(9 * mWidth / 14, 0).x, centerCoor(9 * mWidth / 14, 0).y,
                centerCoor(mWidth / 2, mHeight / 5).x, centerCoor(mWidth / 2, mHeight / 5).y);

        canvas.drawPath(mPath, mPaint);

        invalidate();
    }

    private PointF centerCoor(float x, float y) {
        return new PointF(x + mOffsetX, y + mOffsetY);
    }
}
