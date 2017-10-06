package hs.thang.com.love.view.tab;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import hs.thang.com.love.TestActivity;
import hs.thang.com.love.common.CountUpTimer;
import hs.thang.com.love.common.RenameDialog;
import hs.thang.com.love.core.Event;
import hs.thang.com.love.data.StringData;
import hs.thang.com.love.util.LoveUtil;
import hs.thang.com.love.view.customview.CustomHeart;
import hs.thang.com.love.util.CustomTextView;
import hs.thang.com.love.view.DatePickerFragment;
import hs.thang.com.thu.R;

@SuppressLint("ValidFragment")
public class MainFragment extends AbsFragment {

    private ImageView mAvata1;
    private ImageView mAvata2;
    private LinearLayout mLargeImage;
    private CustomTextView mTxtNickName1;
    private CustomTextView mTxtNickName2;
    private CustomTextView mTxtStartDate;
    private CustomTextView mTxtDaysCount;
    private CustomTextView mTxtEachDay;
    private Context mContext;
    private CountUpTimer mCountUpTimer;
    private String mStartTime;
    private LinearLayout mBackgroudMain;

    //for test
    CustomHeart mTest;

    private UpdateBackgroundListener mCallback;

    private static final String TAG = "MainFragment";
    private static final int REQUEST_CODE_PHOTO_PICKED_WITH_DATA_FILE_AVATA1 = 1001;
    private static final int REQUEST_CODE_PHOTO_PICKED_WITH_DATA_FILE_AVATA2 = 1002;
    private static final int REQUEST_CODE_PHOTO_PICKED_WITH_DATA_FILE_BACKGROUND = 1003;

    @SuppressLint("ValidFragment")
    public MainFragment(Context context) {
        mContext = context;
    }

    public interface UpdateBackgroundListener {
        void updateBackground(Uri uri);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (UpdateBackgroundListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initString(StringData.KEY_START_DATE, mTxtStartDate);
        String startTime = initCountTimes(StringData.KEY_START_DATE_FULL);
        if (startTime != null) {
            setTime(startTime);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelCountUpTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView(View rootView) {
        mAvata1 = (ImageView) rootView.findViewById(R.id.avata1);
        mAvata2 = (ImageView) rootView.findViewById(R.id.avata2);
        mLargeImage = (LinearLayout) rootView.findViewById(R.id.linLargeCircle);
        mTxtNickName1 = (CustomTextView) rootView.findViewById(R.id.nickname1);
        mTxtNickName2 = (CustomTextView) rootView.findViewById(R.id.nickname2);
        mTxtStartDate = (CustomTextView) rootView.findViewById(R.id.txtTop);
        mTxtDaysCount = (CustomTextView) rootView.findViewById(R.id.txtMedium);
        mTxtEachDay = (CustomTextView) rootView.findViewById(R.id.txtBottom);
        mBackgroudMain = (LinearLayout) rootView.findViewById(R.id.backgound_frag_main);

        mTest = (CustomHeart) rootView.findViewById(R.id.forTest);

        initBothAvataAndNickName();
        setInitBackgroud(LoveUtil.CASE_BACKGROUD);

        mAvata1.setOnClickListener(onClickListener);
        mAvata2.setOnClickListener(onClickListener);
        mLargeImage.setOnClickListener(onClickListener);
        mTest.setOnClickListener(onClickListener);

        registerForContextMenu();
    }

    private void registerForContextMenu() {
        registerForContextMenu(mAvata1);
        registerForContextMenu(mAvata2);
        registerForContextMenu(mLargeImage);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        switch (v.getId()) {
            case R.id.linLargeCircle:
                menuInflater.inflate(R.menu.menu_context_for_back_ground, menu);
                break;
            case R.id.avata1:
                menuInflater.inflate(R.menu.menu_context_for_avata_1, menu);
                break;
            case R.id.avata2:
                menuInflater.inflate(R.menu.menu_context_for_avata_2, menu);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idChangeAvata1:
                Intent intent = LoveUtil.getPhotoPickIntent(mContext, LoveUtil.CASE_AVATA1);
                startActivityForResult(intent, REQUEST_CODE_PHOTO_PICKED_WITH_DATA_FILE_AVATA1);
                return true;
            case R.id.idChangeNickName1:
                renameNickName(mTxtNickName1, StringData.KEY_NICKNAME_1);
                return true;
            case R.id.idChangeAvata2:
                Intent intent2 = LoveUtil.getPhotoPickIntent(mContext, LoveUtil.CASE_AVATA2);
                startActivityForResult(intent2, REQUEST_CODE_PHOTO_PICKED_WITH_DATA_FILE_AVATA2);
                return true;
            case R.id.idChangeNickName2:
                renameNickName(mTxtNickName2, StringData.KEY_NICKNAME_2);
                return true;
            case R.id.idChangeData:
                pickDateAndSetDate();
                return true;
            case R.id.idChangeBackGroud:
                Intent intent3 = LoveUtil.getPhotoPickBackgroudIntent(mContext, LoveUtil.CASE_BACKGROUD);
                startActivityForResult(intent3, REQUEST_CODE_PHOTO_PICKED_WITH_DATA_FILE_BACKGROUND);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.avata1:
                    getActivity().openContextMenu(mAvata1);
                    break;
                case R.id.avata2:
                    getActivity().openContextMenu(mAvata2);
                    break;
                case R.id.linLargeCircle:
                    getActivity().openContextMenu(mLargeImage);
                    break;
                case R.id.forTest:
                    Intent intent = new Intent(mContext, TestActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    private void pickDateAndSetDate() {
        DialogFragment newFragment = new DatePickerFragment() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                super.onDateSet(view, year, month, dayOfMonth);
                mStartTime = String.valueOf((new StringBuilder().append(dayOfMonth).append(".")
                        .append(month + 1).append(".")
                        .append(year).append(",").append(" 00:00")));

                setStartDate(year, month, dayOfMonth);
                StringData.setPreference(mContext, StringData.KEY_START_DATE_FULL, mStartTime);

                cancelCountUpTimer();
                setTime(mStartTime);
            }
        };
        newFragment.show(getActivity().getFragmentManager(), "datePicker");
    }

    private void setStartDate(int year, int month, int dayOfMonth) {
        String date = String.valueOf(new StringBuilder().append(dayOfMonth).append("/")
                .append(month + 1).append("/").append(year));
        mTxtStartDate.setText(date);
        StringData.setPreference(getActivity(), StringData.KEY_START_DATE, date);
    }


    private void setTime(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        formatter.setLenient(false);

        long startMilliseconds = 0;
        Date startDate;
        try {
            startDate = formatter.parse(time);
            startMilliseconds = startDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mCountUpTimer = new CountUpTimer(mContext,
                (System.currentTimeMillis() - startMilliseconds) / 1000, 1000) {
            @Override
            public void onTickdays(String days) {
                super.onTickdays(days);
                mTxtDaysCount.setText(days);
            }

            @Override
            public void onTickTimes(String times) {
                super.onTickTimes(times);
                mTxtEachDay.setText(times);
            }
        };
        mCountUpTimer.start();
    }

    private void renameNickName(final TextView nickName, final String string) {
        RenameDialog renameDialog = new RenameDialog(mContext);
        renameDialog.showMediaRenameDialog();
        renameDialog.setCurrentName(nickName.getText().toString());
        renameDialog.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                Event ev = (Event) data;
                String newName = ev.getData() + "";
                StringData.setPreference(mContext, string, newName);
                nickName.setText(newName);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_PHOTO_PICKED_WITH_DATA_FILE_AVATA1:
                    setResultAvata(data, mAvata1);
                    break;
                case REQUEST_CODE_PHOTO_PICKED_WITH_DATA_FILE_AVATA2:
                    setResultAvata(data, mAvata2);
                    break;
                case REQUEST_CODE_PHOTO_PICKED_WITH_DATA_FILE_BACKGROUND:
                    //setBackGroud(data.getData(), mBackground);
                    mCallback.updateBackground(data.getData());
                    break;
                default:
                    break;
            }
        }
    }

    private void initBothAvataAndNickName() {
        initBothAvataImages();
        initBothNickName();
    }

    private void initBothAvataImages() {
        initAvataImages(LoveUtil.CASE_AVATA1, mAvata1);
        initAvataImages(LoveUtil.CASE_AVATA2, mAvata2);
    }

    private void initAvataImages(int avata, ImageView mAvata) {
        String path = getPathSaveImage(avata);
        File file = new File(path);
        if (file.exists()) {
            Uri selectedImage = Uri.fromFile(file);
            setAvata(selectedImage, mAvata);
        }
    }

    private void initBothNickName() {
        initString(StringData.KEY_NICKNAME_1, mTxtNickName1);
        initString(StringData.KEY_NICKNAME_2, mTxtNickName2);
    }

    private void initString(String key, TextView textView) {
        String getText = StringData.getPreference(mContext, key);
        if (getText != "") {
            textView.setText(getText);
        } else {
            Log.i(TAG, "initString: " + mContext.getResources().getString(R.string.nickName));
            textView.setText(mContext.getResources().getString(R.string.nickName));
        }
    }

    private String initCountTimes(String key) {
        String startTime = StringData.getPreference(mContext, StringData.KEY_START_DATE_FULL);
        if (startTime != "") {
            return startTime;
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setInitBackgroud(int avata) {
        String path = getPathSaveImage(avata);
        File file = new File(path);
        if (file.exists()) {
            Uri selectedImage = Uri.fromFile(file);
            mCallback.updateBackground(selectedImage);
        }
    }

    private void setResultAvata(Intent data, ImageView mAvata) {
        if (data == null) {
            //Display an error
            return;
        }
        Uri selectedImage = data.getData();
        setAvata(selectedImage, mAvata);
    }

    private void setAvata(Uri selectedImage, ImageView mAvata) {
        Bitmap bitmap = LoveUtil.getCircleBitmap(mContext, LoveUtil.decodeUriToBitmap(getContext(), selectedImage), true);
        mAvata.setImageBitmap(bitmap);
    }

    private String getPathSaveImage(int avata) {
        return LoveUtil.pathForCroppedPhoto(mContext, LoveUtil.generateTempPhotoFileName(avata));
    }


    private void cancelCountUpTimer() {
        if (mCountUpTimer != null) {
            mCountUpTimer.cancel();
        }
    }
}
