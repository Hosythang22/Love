package hs.thang.com.love.chat.chathead;

import android.content.Context;
import android.graphics.Point;

import hs.thang.com.love.chat.chathead.lib.ChatHeadUtils;
import hs.thang.com.love.chat.chathead.lib.ui.ChatHeadDefaultConfig;

/**
 * Created by DELL on 10/14/2017.
 */

public class CustomChatHeadConfig extends ChatHeadDefaultConfig {
    public CustomChatHeadConfig(Context context, int xPosition, int yPosition) {
        super(context);
        setHeadHorizontalSpacing(ChatHeadUtils.dpToPx(context, -2));
        setHeadVerticalSpacing(ChatHeadUtils.dpToPx(context, 2));
        setHeadWidth(ChatHeadUtils.dpToPx(context, 50));
        setHeadHeight(ChatHeadUtils.dpToPx(context, 50));
        setInitialPosition(new Point(xPosition, yPosition));
        setCloseButtonHeight(ChatHeadUtils.dpToPx(context, 50));
        setCloseButtonWidth(ChatHeadUtils.dpToPx(context, 50));
        setCloseButtonBottomMargin(ChatHeadUtils.dpToPx(context, 100));
        setCircularRingWidth(ChatHeadUtils.dpToPx(context, 53));
        setCircularRingHeight(ChatHeadUtils.dpToPx(context, 53));
    }

    @Override
    public int getMaxChatHeads(int maxWidth, int maxHeight) {
        return (int) Math.floor(maxWidth / (getHeadWidth() + getHeadHorizontalSpacing(maxWidth, maxHeight))) - 1;
    }
}
