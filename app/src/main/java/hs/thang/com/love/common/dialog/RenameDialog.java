package hs.thang.com.love.common.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import hs.thang.com.love.common.comunication.Event;
import hs.thang.com.love.common.comunication.ViewObservable;
import hs.thang.com.love.util.TextViewC;
import hs.thang.com.thu.R;

import static java.util.prefs.Preferences.MAX_NAME_LENGTH;

public class RenameDialog extends ViewObservable {

    private static final String TAG = "RenameDialog";

    private final InputMethodManager mInputMethodManager;
    private final MediaRenameDialogFragment mDialogFragment;
    private final Context mCtx;
    private String mString = "";
    private Handler mHandler = null;
    private TextViewC mTxtOk;
    private TextViewC mTxtCancel;
    private static final int HIDE_SOFT_INPUT = 0;
    private TextInputLayout mTextInputLayout = null;

    private EditText mAlertEditText = null;

    public RenameDialog(Context ctx) {
        mCtx = ctx;
        mDialogFragment = new MediaRenameDialogFragment(mCtx);

        mHandler = new Handler(mCtx.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case HIDE_SOFT_INPUT:
                        IBinder windowToken = (IBinder) msg.obj;
                        hideSoftInput(windowToken);
                        break;
                    default:
                        break;
                }
            }
        };
        mInputMethodManager = (InputMethodManager) mCtx.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private void hideSoftInput(IBinder windowToken) {
        try {
            ((Activity) mCtx).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            InputMethodManager imm = (InputMethodManager) mCtx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(windowToken, 0);
        } catch (IllegalArgumentException e) {
            android.util.Log.e(TAG, e.toString());
        }
    }

    @SuppressLint("ValidFragment")
    private class MediaRenameDialogFragment extends DialogFragment {

        private static final String TAG = "RenameDialogFragment";

        private AlertDialog mAlertDialog = null;
        private final Context mCtx;

        public MediaRenameDialogFragment(Context ctx) {
            mCtx = ctx;
            Bundle args = new Bundle();
            this.setArguments(args);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            View alertDialogueView = LayoutInflater.from(mCtx).inflate(R.layout.alert_dialog_text_entry, (ViewGroup) null);
            mTextInputLayout = (TextInputLayout) alertDialogueView.findViewById(R.id.text_input_layout);
            mAlertEditText = (EditText) alertDialogueView.findViewById(R.id.username_edit);
            mTxtOk = (TextViewC) alertDialogueView.findViewById(R.id.txtOkDialog);
            mTxtCancel = (TextViewC) alertDialogueView.findViewById(R.id.txtCancelDialog);

            mAlertEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                    | InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            mAlertEditText.requestFocus();

            mAlertEditText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mInputMethodManager.showSoftInput(mAlertEditText, 0);
                }
            }, 300);

            Bundle bundle = mAlertEditText.getInputExtras(true);
            bundle.putInt("maxLength", MAX_NAME_LENGTH);

            mAlertDialog = new AlertDialog.Builder(getActivity(), R.style.NewDialog)
                    .setView(alertDialogueView).create();
            mAlertDialog.show();

            if (mString != null) {
                mAlertEditText.setText(mString);
                mAlertEditText.setSelection(mAlertEditText.length());
                mAlertEditText.selectAll();
            }
            mAlertEditText.setSelectAllOnFocus(true);

                mAlertEditText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    return false;
                }
            });

            mTxtOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startHandleRename();
                    hideSoftInput(mAlertEditText.getWindowToken());
                    mAlertDialog.dismiss();
                }
            });

            mTxtCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                    hideSoftInput(mAlertEditText.getWindowToken());
                }
            });

            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            mAlertEditText.addTextChangedListener(textWatcher);
            mAlertEditText.setFocusable(true);

            return mAlertDialog;
        }

        private void startHandleRename() {
            mString = mAlertEditText.getText().toString().trim();
            notifyObservers(Event.Builder.create().setType(Event.EVENT_RENAME_MEDIA).setData(mString));
        }

        public void setCurrentName(String currentName) {
            mString = currentName;
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
        }
    }

    public void setCurrentName(String currentName) {
        mDialogFragment.setCurrentName(currentName);
    }

    public void showMediaRenameDialog() {
        try {
            mDialogFragment.show(((Activity) mCtx).getFragmentManager(), "dialog");
        } catch (IllegalStateException e) {
            android.util.Log.e(TAG, e.toString());
        }
    }
}
