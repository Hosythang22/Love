package hs.thang.com.love.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import hs.thang.com.love.core.Event;
import hs.thang.com.love.core.ViewObservable;
import hs.thang.com.love.data.EventInfor;
import hs.thang.com.love.util.TextViewC;
import hs.thang.com.love.view.DatePickerFragment;
import hs.thang.com.thu.R;

import static java.util.prefs.Preferences.MAX_NAME_LENGTH;

/**
 * Created by DELL on 10/7/2017.
 */

public class NewEventDialog extends ViewObservable {
    private static final String TAG = "NewEventDialog";

    public static final int PICK_IMAGE = 1;
    public static final int PICK_DATE = 2;
    public static final int PICK_CINTENT = 3;

    private final InputMethodManager mInputMethodManager;
    private final MediaNewEventDialogFragment mDialogFragment;
    private final Context mCtx;
    private Handler mHandler = null;
    private static final int HIDE_SOFT_INPUT = 0;
    private AlertDialog mAlertDialog = null;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(int id);
    }

    public NewEventDialog(Context ctx) {
        mCtx = ctx;
        mDialogFragment = new MediaNewEventDialogFragment(mCtx);

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
    private class MediaNewEventDialogFragment extends DialogFragment {

        private static final String TAG = "MediaNewEventDialogFragment";

        private TextViewC mTxtOk;
        private TextViewC mTxtCancel;
        private final Context mCtx;
        private ImageView mThumbnail;
        private String mContent = "";
        private String mDate = "";
        private TextViewC mTxtPick;
        private TextViewC mTxtDate;
        private EditText mAlertEditText = null;
        private Uri mUri;

        public MediaNewEventDialogFragment(Context ctx) {
            mCtx = ctx;
            Bundle args = new Bundle();
            this.setArguments(args);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            View alertDialogueView = LayoutInflater.from(mCtx).inflate(R.layout.alert_dialog_text_entry, (ViewGroup) null);
            mAlertEditText = (EditText) alertDialogueView.findViewById(R.id.username_edit);
            mTxtOk = (TextViewC) alertDialogueView.findViewById(R.id.txtOkDialog);
            mTxtCancel = (TextViewC) alertDialogueView.findViewById(R.id.txtCancelDialog);
            mTxtPick = (TextViewC) alertDialogueView.findViewById(R.id.pickImage);
            mTxtDate = (TextViewC) alertDialogueView.findViewById(R.id.date);
            mThumbnail = (ImageView) alertDialogueView.findViewById(R.id.coverImage);

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

            if (mContent != null) {
                mAlertEditText.setText(mContent);
                mAlertEditText.setSelection(mAlertEditText.length());
                mAlertEditText.selectAll();
            }
            mAlertEditText.setSelectAllOnFocus(true);

            mAlertEditText.setOnKeyListener((v, keyCode, event) -> false);

            mTxtOk.setOnClickListener(v -> {
                startHandleCreateEvent();
                hideSoftInput(mAlertEditText.getWindowToken());
                mAlertDialog.dismiss();
            });

            mTxtPick.setOnClickListener( view -> {
                mOnItemClickListener.onItemClick(PICK_IMAGE);
            });

            mTxtDate.setOnClickListener(v -> {
                pickDateAndSetDate();
            });

            mTxtCancel.setOnClickListener(v -> {
                mAlertDialog.dismiss();
                hideSoftInput(mAlertEditText.getWindowToken());
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

        public void setThumbnail(Uri uri) {
            mUri = uri;
            RequestOptions options = new RequestOptions()
                    /*.signature(mediaItem.getSignature())*/
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .centerCrop()
                    /*.placeholder(placeholder)*/
                    //.animate(R.anim.fade_in)//TODO:DONT WORK WELL
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            Glide.with(mThumbnail.getContext())
                    .load(uri)
                    .apply(options)
                    .thumbnail(0.5f)
                    .into(mThumbnail);
        }

        private void startHandleCreateEvent() {
            mContent = mAlertEditText.getText().toString().trim();

            EventInfor eventInfor = new EventInfor(mContent, mDate, mUri, "xxx");
            notifyObservers(Event.Builder.create().setType(Event.EVENT_RENAME_MEDIA).setData(eventInfor));
        }

        public void setCurrentName(String currentName) {
            mContent = currentName;
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

        private void pickDateAndSetDate() {
            DialogFragment newFragment = new DatePickerFragment() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    super.onDateSet(view, year, month, dayOfMonth);
                    mDate = String.valueOf((new StringBuilder().append(dayOfMonth).append(".")
                            .append(month + 1).append(".")
                            .append(year).append(",")));
                    mTxtDate.setText(mDate);
                }
            };
            newFragment.show(getActivity().getFragmentManager(), "datePicker");
        }
    }

    public void setCurrentName(String currentName) {
        mDialogFragment.setCurrentName(currentName);
    }

    public void setThumbnail(Uri uri) {
        mDialogFragment.setThumbnail(uri);
    }

    public void showCreateNewEventDialog() {
        try {
            mDialogFragment.show(((Activity) mCtx).getFragmentManager(), "dialog");
        } catch (IllegalStateException e) {
            android.util.Log.e(TAG, e.toString());
        }
    }


}
