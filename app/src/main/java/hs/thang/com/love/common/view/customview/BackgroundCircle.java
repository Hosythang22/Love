package hs.thang.com.love.common.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import hs.thang.com.love.R;

public class BackgroundCircle extends View {

    private float mCircleX;
    private float mCircleY;
    private float mCircleRadius;
    private Paint mPaintCircle;
    private Paint mPaintInside;
    private Path mPath;

    private float mScreenWidth;
    private float mScreenHeight;

    public BackgroundCircle(Context context) {
        super(context);
        init();
    }

    public BackgroundCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackgroundCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCircleRadius = getContext().getResources().getDimension(R.dimen.circle_size) / 2;
        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setColor(Color.RED);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setStrokeWidth(20);

        mPaintInside = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintInside.setColor(Color.BLUE);
        mPaintInside.setStyle(Paint.Style.FILL);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenWidth = w;
        mScreenHeight = h;

        mCircleX = mScreenWidth / 2;
        mCircleY = mScreenHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //mPath.moveTo();

        canvas.drawCircle(mCircleX, mCircleY, mCircleRadius, mPaintCircle);
    }
}
