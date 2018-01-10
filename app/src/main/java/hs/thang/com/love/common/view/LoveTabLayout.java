package hs.thang.com.love.common.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import hs.thang.com.love.R;

/**
 * Created by sev_user on 3/22/2017.
 */

public class LoveTabLayout extends TabLayout {

    public LoveTabLayout(Context context) {
        super(context);
        createTabs();
    }

    public LoveTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        createTabs();
    }

    public LoveTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createTabs();
    }

    private void createTabs() {
        addTab(R.string.chat);
        addTab(R.string.love);
        addTab(R.string.setting);
    }

    private void addTab(int contentDescriptionId) {
        Tab tab = newTab();
        TextView textview = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        textview.setText(getResources().getString(contentDescriptionId));
        tab.setCustomView(textview);
        addTab(tab);
    }
}
