package hs.thang.com.love.settingscreen.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import hs.thang.com.love.R;

/**
 * Created by sev_user on 1/10/2018.
 */

@SuppressLint("ValidFragment")
public class FontPickerDialog extends DialogFragment {
    private AlertDialog mAlertDialog = null;
    private final Context mCtx;
    View mAlertDialogueView;

    public FontPickerDialog(Context ctx,View alertDialogueView) {
        mCtx = ctx;
        mAlertDialogueView = alertDialogueView;
        Bundle args = new Bundle();
        this.setArguments(args);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mAlertDialog = new AlertDialog.Builder(getActivity(), R.style.FontPickerDialog)
                .setView(mAlertDialogueView).create();
        mAlertDialog.show();

        return mAlertDialog;
    }
}
