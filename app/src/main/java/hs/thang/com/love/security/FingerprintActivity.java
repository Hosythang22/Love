package hs.thang.com.love.security;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import hs.thang.com.love.AbsActivity;
import hs.thang.com.love.MainActivity;
import hs.thang.com.love.view.customview.KeyboardView;
import hs.thang.com.love.view.customview.password.PasswordView;
import hs.thang.com.thu.R;

public class FingerprintActivity extends AbsActivity implements KeyboardView.OnClickListenner {

    private static final String TAG = "FingerprintActivity";

    public static final int MSG_UPDATE_PASSWORD_STATUS = 1;

    private KeyStore mKeyStore;
    // Variable used for storing the key in the Android Keystore container
    private Cipher mCipher;
    private static final String KEY_NAME = "thangThu";
    private TextView mTextView;
    private KeyboardView mKeyboardView;
    /*private PasswordView mPasswordView;
    private int mPasswordCount;
    private StringBuilder mPasswordBuilder = new StringBuilder();*/
    private Password mPassword;

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;

            switch (what) {
                case MSG_UPDATE_PASSWORD_STATUS:
                    mPassword.mPasswordView.setStatePasswordView(false, mPassword.mPasswordCount);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        initView();

        // Initializing both Android Keyguard Manager and Fingerprint Manager
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        mTextView = (TextView) findViewById(R.id.errorText);
        // Check whether the device has a Fingerprint sensor.

        if (!fingerprintManager.isHardwareDetected()) {
            /**
             * An error message will be displayed if the device does not contain the fingerprint hardware.
             * However if you plan to implement a default authentication method,
             * you can redirect the user to a default authentication activity from here.
             * Example:
             * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
             * startActivity(intent);
             */
            mTextView.setText("Your Device does not have a Fingerprint Sensor");
        } else {
            // Checks whether fingerprint permission is set on manifest
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                mTextView.setText("Fingerprint authentication permission not enabled");
            } else {
                // Check whether at least one fingerprint is registered
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    mTextView.setText("Register at least one fingerprint in Settings");
                } else {
                    generateKey();

                    if (cipherInit()) {
                        FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(mCipher);
                        FingerprintHandler helper = new FingerprintHandler(this);
                        helper.startAuth(fingerprintManager, cryptoObject);
                    }
                }
            }
        }
    }

    private void initView() {
        mKeyboardView = (KeyboardView) findViewById(R.id.keyboard_view);
        mKeyboardView.setListenner(this);
        mPassword = new Password((PasswordView) findViewById(R.id.password_view), 0, new StringBuilder());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void generateKey() {

        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator;

        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            mKeyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean cipherInit(){

        try {
            mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME,
                    null);
            mCipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mHandler!= null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public String onClickNumber(String text) {
        mPassword.mPasswordBuilder.append(text);
        mPassword.mPasswordCount ++;
        mPassword.mPasswordView.setStatePasswordView(true, mPassword.mPasswordCount);

        if (mPassword.mPasswordCount == 4) {
            if (mPassword.mPasswordBuilder.toString().equals("2410")) {
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                mPassword.mPasswordBuilder.delete(0,5);
                mPassword.mPasswordCount = 0;
                mHandler.sendEmptyMessageDelayed(MSG_UPDATE_PASSWORD_STATUS, 100);
            }
        }
        return null;
    }

    @Override
    public void onClickClear() {
        mPassword.mPasswordCount = 0;
        mPassword.mPasswordBuilder.delete(0,5);
        mPassword.mPasswordView.setStatePasswordView(false, mPassword.mPasswordCount);
    }

    @Override
    public void onClickBack() {
        if (mPassword.mPasswordCount > 0) {
            mPassword.mPasswordBuilder.deleteCharAt(mPassword.mPasswordCount - 1);
            mPassword.mPasswordView.setStatePasswordView(false, mPassword.mPasswordCount --);
        }
    }

    public static class Password {
        PasswordView mPasswordView;
        int mPasswordCount;
        StringBuilder mPasswordBuilder;

        public Password(PasswordView passwordView, int passwordCount, StringBuilder password) {
            this.mPasswordView = passwordView;
            this.mPasswordCount = passwordCount;
            this.mPasswordBuilder = password;
        }
    }
}
