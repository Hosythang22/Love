package hs.thang.com.love.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import hs.thang.com.love.view.FontCache;
import hs.thang.com.thu.R;

@SuppressLint("AppCompatCustomView")
public class TextViewC extends TextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public TextViewC(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);
    }

    public TextViewC(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.TextViewC);

        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", TypefaceCustom.ALBONDIGAS);

        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
        attributeArray.recycle();
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        switch (textStyle) {
            case TypefaceCustom.ALBONDIGAS:
                return FontCache.getTypeface(context, "fonts/BaseFiolexGirl.ttf");
            case TypefaceCustom.ALDINE:
                return FontCache.getTypeface(context, "fonts/Aldine.ttf");
            case TypefaceCustom.DROIDSANSFALLBACK:
                return FontCache.getTypeface(context, "fonts/DroidSansFallback.ttf");
            case TypefaceCustom.ECLIPSEDEMO:
                return FontCache.getTypeface(context, "fonts/Eclipse Demo.ttf");
            case TypefaceCustom.MELANCHOLIGHTITALIC:
                return FontCache.getTypeface(context, "fonts/Melancholight Italic.ttf");
            case TypefaceCustom.MELANCOLIGHT:
                return FontCache.getTypeface(context, "fonts/Melancholight.ttf");
            case TypefaceCustom.XLINES:
                return FontCache.getTypeface(context, "fonts/XLines.ttf");
            case TypefaceCustom.XOXOXA:
                return FontCache.getTypeface(context, "fonts/Xoxoxa.ttf");
            case TypefaceCustom.XXX:
                return FontCache.getTypeface(context, "fonts/XXX.ttf");
            case TypefaceCustom.YNWUAY:
                return FontCache.getTypeface(context, "fonts/y.n.w.u.a.y.ttf");
            case TypefaceCustom.YB:
                return FontCache.getTypeface(context, "fonts/YB Hybrid.ttf");
            case TypefaceCustom.YEAR2000BOOGIE:
                return FontCache.getTypeface(context, "fonts/Year2000Boogie.ttf");
            default:
                return FontCache.getTypeface(context, "fonts/Year2000Boogie.ttf");
        }
    }
}
