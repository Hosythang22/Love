package hs.thang.com.love.chatscreen.chat.core.users.add;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hs.thang.com.love.chatscreen.chat.models.User;
import hs.thang.com.love.chatscreen.chat.utils.Constants;
import hs.thang.com.love.common.utils.SharedPrefUtil;
import hs.thang.com.love.R;

/**
 * Author: Kartik Sharma
 * Created on: 9/2/2016 , 10:08 PM
 * Project: FirebaseChat
 */

public class AddUserInteractor implements AddUserContract.Interactor {
    private AddUserContract.OnUserDatabaseListener mOnUserDatabaseListener;

    public AddUserInteractor(AddUserContract.OnUserDatabaseListener onUserDatabaseListener) {
        this.mOnUserDatabaseListener = onUserDatabaseListener;
    }

    @Override
    public void addUserToDatabase(final Context context, FirebaseUser firebaseUser) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        User user = new User(firebaseUser.getUid(),
                firebaseUser.getEmail(),
                SharedPrefUtil.getInstance(context).getString(Constants.ARG_FIREBASE_TOKEN));
        database.child(Constants.ARG_USERS)
                .child(firebaseUser.getUid())
                .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mOnUserDatabaseListener.onSuccess(context.getString(R.string.user_successfully_added));
                        } else {
                            mOnUserDatabaseListener.onFailure(context.getString(R.string.user_unable_to_add));
                        }
                    }
                });
    }
}
