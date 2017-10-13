package hs.thang.com.love.chat.ui.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import hs.thang.com.love.chat.core.chat.ChatContract;
import hs.thang.com.love.chat.core.chat.ChatPresenter;
import hs.thang.com.love.chat.events.PushNotificationEvent;
import hs.thang.com.love.chat.models.Chat;
import hs.thang.com.love.chat.ui.adapters.ChatRecyclerAdapter;
import hs.thang.com.love.chat.utils.Constants;
import hs.thang.com.thu.R;

/**
 * Created by sev_user on 10/13/2017.
 */

public class ChatBottomSheetView extends FrameLayout implements ChatContract.View, TextView.OnEditorActionListener {

    private RecyclerView mRecyclerViewChat;
    private EditText mETxtMessage;
    private ProgressDialog mProgressDialog;
    private ChatRecyclerAdapter mChatRecyclerAdapter;
    private ChatPresenter mChatPresenter;

    private Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChatBottomSheetView(Context context) {
        super(context);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChatBottomSheetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChatBottomSheetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(Context context) {
        mContext = context;
        setBackgroundColor(R.color.sinch_purple);
        setNestedScrollingEnabled(false);
        inflate(getContext(), R.layout.chat_bottom_sheet_view, this);
        initViews();
    }

    private void initViews() {
        mRecyclerViewChat = (RecyclerView) findViewById(R.id.recycler_view_chat);

        mRecyclerViewChat.setNestedScrollingEnabled(false);
        mRecyclerViewChat.setHasFixedSize(false);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle(mContext.getString(R.string.loading));
        mProgressDialog.setMessage(mContext.getString(R.string.please_wait));
        mProgressDialog.setIndeterminate(true);

        mETxtMessage = (EditText) findViewById(R.id.edit_text_message);
        mETxtMessage.setOnEditorActionListener(this);



        mChatPresenter = new ChatPresenter(this);

        mChatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                ((AppCompatActivity) mContext).getIntent().getStringExtra(Constants.ARG_RECEIVER_UID));
    }

    private  <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    @Override
    public void onSendMessageSuccess() {
        mETxtMessage.setText("");
        Toast.makeText(mContext, "Message sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendMessageFailure(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetMessagesSuccess(Chat chat) {
        if (mChatRecyclerAdapter == null) {
            mChatRecyclerAdapter = new ChatRecyclerAdapter(new ArrayList<Chat>());
            mRecyclerViewChat.setAdapter(mChatRecyclerAdapter);
        }
        mChatRecyclerAdapter.add(chat);
        Log.i("fuck1310", "onGetMessagesSuccess: chat = " + chat.toString());
        mRecyclerViewChat.smoothScrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);
    }

    @Override
    public void onGetMessagesFailure(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onPushNotificationEvent(PushNotificationEvent pushNotificationEvent) {
        if (mChatRecyclerAdapter == null || mChatRecyclerAdapter.getItemCount() == 0) {
            mChatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    pushNotificationEvent.getUid());
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendMessage();
            return true;
        }
        return false;
    }

    private void sendMessage() {
        String message = mETxtMessage.getText().toString();
        String receiver = ((AppCompatActivity) mContext).getIntent().getStringExtra(Constants.ARG_RECEIVER);
        String receiverUid = ((AppCompatActivity) mContext).getIntent().getStringExtra(Constants.ARG_RECEIVER_UID);
        String sender = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String receiverFirebaseToken = ((AppCompatActivity) mContext).getIntent().getStringExtra(Constants.ARG_FIREBASE_TOKEN);
        Chat chat = new Chat(sender,
                receiver,
                senderUid,
                receiverUid,
                message,
                System.currentTimeMillis());
        mChatPresenter.sendMessage(mContext.getApplicationContext(),
                chat,
                receiverFirebaseToken);
    }
}
