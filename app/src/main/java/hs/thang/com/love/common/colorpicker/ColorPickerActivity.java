package hs.thang.com.love.common.colorpicker;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import hs.thang.com.love.AbsActivity;
import hs.thang.com.thu.R;
import thang.hs.com.colorpicker.ColorPicker;

/**
 * Created by sev_user on 1/9/2018.
 */

public class ColorPickerActivity extends AbsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_activity_color_picker);

        Button button = (Button) findViewById(R.id.pick);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(ColorPickerActivity.this);
                colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        Log.d("onChooseColor: position", "" + position + ",  color = " + color);// will be fired only when OK button was tapped
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
            }
        });
    }
}
