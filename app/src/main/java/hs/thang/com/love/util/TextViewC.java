package hs.thang.com.love.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import hs.thang.com.love.chatscreen.chat.utils.Constants;
import hs.thang.com.love.common.utils.SharedPrefUtil;
import hs.thang.com.love.common.view.FontCache;
import hs.thang.com.love.R;

@SuppressLint("AppCompatCustomView")
public class TextViewC extends TextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    private Context mContext;

    public TextViewC(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);
    }

    public TextViewC(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.TextViewC);
        int textStyle = SharedPrefUtil.getInstance(mContext).getInt(Constants.SETTING_FONT_STYLE, TypefaceCustom.BEAUTIFUL_EVERY_TIME);
        setFont(textStyle);

        attributeArray.recycle();
    }

    public void setFont(int textStyle) {
        Typeface customFont = selectTypeface(mContext, textStyle);
        setTypeface(customFont);
    }

    // todo: make list font style to use everywhere

    private Typeface selectTypeface(Context context, int textStyle) {
        switch (textStyle) {
            case TypefaceCustom.AMATIC_SC_BOLD:
                return FontCache.getTypeface(context, "fonts/AmaticSC-Bold.ttf"); // good
            case TypefaceCustom.ARISTONNE:
                return FontCache.getTypeface(context, "fonts/Aristonne.ttf"); // good
            case TypefaceCustom.BASE_FIOLEX_GIRL:
                return FontCache.getTypeface(context, "fonts/BaseFiolexGirl.ttf"); // good
            case TypefaceCustom.BASE_FUTARA:
                return FontCache.getTypeface(context, "fonts/BaseFutara.ttf"); // good
            case TypefaceCustom.BEAUTIFUL_EVERY_TIME:
                return FontCache.getTypeface(context, "fonts/BeautifulEveryTime.ttf"); // good
            case TypefaceCustom.COMFORTAA_REGULAR:
                return FontCache.getTypeface(context, "fonts/Comfortaa-Regular.ttf"); // good
            case TypefaceCustom.DANCING_SCRIPT_REGULAR:
                return FontCache.getTypeface(context, "fonts/DancingScript-Regular.ttf"); // good
            case TypefaceCustom.COMIC_SANS:
                return FontCache.getTypeface(context, "fonts/ComicSans.ttf"); // good
            case TypefaceCustom.HERA_BIG:
                return FontCache.getTypeface(context, "fonts/HeraBig.ttf"); // good
            case TypefaceCustom.KAILEEN_BOLD:
                return FontCache.getTypeface(context, "fonts/Kaileen_Bold.ttf"); // good
            case TypefaceCustom.KINGTHINGS_PETROCK:
                return FontCache.getTypeface(context, "fonts/Kingthings_Petrock.ttf"); // good
            case TypefaceCustom.LOBSTER_REGULAR:
                return FontCache.getTypeface(context, "fonts/Lobster-Regular.ttf"); // good
            case TypefaceCustom.PACIFICO_REGULAR:
                return FontCache.getTypeface(context, "fonts/Pacifico-Regular.ttf"); // good
            case TypefaceCustom.PATRICK_HAND_REGULAR:
                return FontCache.getTypeface(context, "fonts/PatrickHand-Regular.ttf"); // good
            case TypefaceCustom.PLAY_REGULAR:
                return FontCache.getTypeface(context, "fonts/Play-Regular.ttf"); // good
            case TypefaceCustom.RIX_LOVE_FOOL:
                return FontCache.getTypeface(context, "fonts/RixLoveFool.ttf"); // good
            case TypefaceCustom.ROBOTO:
                return FontCache.getTypeface(context, "fonts/Roboto.ttf"); // good
            case TypefaceCustom.ROBOTO_MEDIUM:
                return FontCache.getTypeface(context, "fonts/Roboto-Medium.ttf"); // good
            case TypefaceCustom.SOFIA_BOLD:
                return FontCache.getTypeface(context, "fonts/SofiaBold.ttf"); // good
            case TypefaceCustom.VALENTINE:
                return FontCache.getTypeface(context, "fonts/Valentine.ttf"); // good
            case TypefaceCustom.VNI_VARI:
                return FontCache.getTypeface(context, "fonts/VNI-Vari.TTF"); // good
            case TypefaceCustom.ZEMKE_HAND:
                return FontCache.getTypeface(context, "fonts/ZemkeHand.ttf"); // good
            default:
                return FontCache.getTypeface(context, "fonts/AmaticSC-Bold.ttf");
        }
    }
}
