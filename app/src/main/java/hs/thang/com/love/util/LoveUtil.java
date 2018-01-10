package hs.thang.com.love.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LoveUtil {

    public static final String KEY_SET_AS_CONTACT_PHOTO = "set-as-contactphoto";
    private static final String AVATA1_FILE = "avata1";
    private static final String AVATA2_FILE = "avata2";
    private static final String BACKGROUND_FILE = "background";
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final String JPG = ".jpg";


    public static final int CASE_AVATA_1 = 1;
    public static final int CASE_AVATA_2 = 2;
    public static final int CASE_BACKGROUD = 3;

    private static int DEVICE_SCREEN_HEIGHT_SIZE = 800; // default 800
    private static int DEVICE_SCREEN_WIDTH_SIZE = 480; // default 480

    public static String generateTempPhotoFileName(int avata) {
        switch (avata) {
            case CASE_AVATA_1:
                return AVATA1_FILE + JPG;
            case CASE_AVATA_2:
                return AVATA2_FILE + JPG;
            case CASE_BACKGROUD:
                return BACKGROUND_FILE + JPG;
            default:
                return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    public static Intent getPhotoPickIntent(Context context, int avata) {
        final String croppedPhotoPath = pathForCroppedPhoto(context, generateTempPhotoFileName(avata));
        final Uri croppedPhotoUri = Uri.fromFile(new File(croppedPhotoPath));
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setPackage("com.sec.android.gallery3d");
        intent.setType("image/*");
        intent.putExtra(KEY_SET_AS_CONTACT_PHOTO, true);
        intent.putExtra("unable-pick-private-file", true);
        addGalleryIntentExtras(intent, croppedPhotoUri, 240);
        return intent;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static String pathForCroppedPhoto(Context context, String fileName) {
        final File dir = new File(context.getExternalCacheDir() + "/tmp");
        dir.mkdirs();
        final File f = new File(dir, fileName);
        return f.getAbsolutePath();
    }

    private static void addGalleryIntentExtras(Intent intent, Uri croppedPhotoUri, int photoSize) {
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", photoSize);
        intent.putExtra("outputY", photoSize);
        addGalleryIntent(intent, croppedPhotoUri);
    }
    //--------------------- Pick Image from Gallery for AVATA END---------------------
    private static void addGalleryIntent( Intent intent, Uri croppedPhotoUri) {
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, croppedPhotoUri);
    }

    //--------------------- Pick Image from Gallery for BACKGROUND STRAT---------------------

    public static Intent getPhotoPickBackgroudIntent(Context context, int avata) {
        final String croppedPhotoPath = pathForCroppedPhoto(context, generateTempPhotoFileName(avata));
        final Uri croppedPhotoUri = Uri.fromFile(new File(croppedPhotoPath));
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(IMAGE_UNSPECIFIED);
        intent.setPackage("com.sec.android.gallery3d");
        startSelectBackgroundActivity(croppedPhotoUri, intent);
        return intent;
    }

    private static void startSelectBackgroundActivity(Uri croppedPhotoUri, Intent intent) {
        intent.putExtra("aspectX", DEVICE_SCREEN_WIDTH_SIZE);
        intent.putExtra("aspectY", DEVICE_SCREEN_HEIGHT_SIZE);
        intent.putExtra("outputX", DEVICE_SCREEN_WIDTH_SIZE);
        intent.putExtra("outputY", DEVICE_SCREEN_HEIGHT_SIZE);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("scaleUpIfNeeded", true);
        addGalleryIntent(intent, croppedPhotoUri);
    }
    //--------------------- Pick Image from Gallery for BACKGROUND END---------------------

    public static Bitmap getCircleBitmap(Context context, Bitmap bitmap, boolean recycle) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap circle = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circle);
        final Paint paint = new Paint();
        BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        final Rect rect = new Rect(0, 0, w, h);
        drawable.setBounds(0, 0, w, h);
        canvas.translate(0, 0);
        paint.reset();
        paint.setAntiAlias(true);
        canvas.drawCircle(w / 2f, h / 2f, w / 2f, paint);
        Paint paint2 = new Paint();
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null, rect, paint2);

        if (recycle) {
            bitmap.recycle();
        }
        return circle;
    }

    public static Bitmap decodeUriToBitmap(Context mContext, Uri sendUri) {
        Bitmap getBitmap = null;
        try {
            InputStream image_stream;
            try {
                image_stream = mContext.getContentResolver().openInputStream(sendUri);
                getBitmap = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBitmap;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getDimensionDp(int resID, Context context) {
        return (int) (context.getResources().getDimension(resID) / context.getResources().getDisplayMetrics().density);
    }

    public static int dip2px(float dpValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
