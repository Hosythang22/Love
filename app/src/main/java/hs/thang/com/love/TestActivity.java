package hs.thang.com.love;

import android.os.Bundle;
import android.view.View;

import hs.thang.com.love.common.view.CanvasView;
import hs.thang.com.thu.R;

public class TestActivity extends AbsActivity {

    private CanvasView mCanvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        /*mCanvasView = (CanvasView) findViewById(R.id.signature_canvas);
        setContentView(new BallBounce(this));*/
    }

    public void clearCanvas(View v) {
        mCanvasView.clearCanvas();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
