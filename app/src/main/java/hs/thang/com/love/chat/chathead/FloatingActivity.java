package hs.thang.com.love.chat.chathead;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hs.thang.com.love.AbsActivity;
import hs.thang.com.thu.R;

/**
 * Created by DELL on 10/14/2017.
 */

public class FloatingActivity extends AbsActivity implements View.OnClickListener {

    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;

    private Button addButton;
    private Button removeButton;
    private Button removeAllButtons;
    private Button toggleButton;
    private Button updateBadgeCount;

    private ChatHeadService chatHeadService;
    private boolean bound;
    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ChatHeadService.LocalBinder binder = (ChatHeadService.LocalBinder) service;
            chatHeadService = binder.getService();
            bound = true;
            chatHeadService.minimize();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Utils.canDrawOverlays(this))
            startChatHead();
        else{
            requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
        }
        setupButtons();
    }

    private void startChatHead() {
        Intent intent = new Intent(this, ChatHeadService.class);
        startService(intent);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }



    private void setupButtons() {
        setContentView(R.layout.floatting_activity);

        addButton = (Button) findViewById(R.id.add_head);
        removeButton = (Button) findViewById(R.id.remove_head);
        removeAllButtons = (Button) findViewById(R.id.remove_all_heads);
        toggleButton = (Button) findViewById(R.id.toggle_arrangement);
        updateBadgeCount = (Button) findViewById(R.id.update_badge_count);

        addButton.setOnClickListener(this);
        removeButton.setOnClickListener(this);
        removeAllButtons.setOnClickListener(this);
        toggleButton.setOnClickListener(this);
        updateBadgeCount.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (bound) {
            if (v == addButton) {
                chatHeadService.addChatHead();
            } else if (v == removeButton) {
                chatHeadService.removeChatHead();
            } else if (v == removeAllButtons) {
                chatHeadService.removeAllChatHeads();
            } else if (v == toggleButton) {
                chatHeadService.toggleArrangement();
            } else if (v == updateBadgeCount) {
                chatHeadService.updateBadgeCount();
            }
        } else {
            Toast.makeText(this, "Service not bound", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPermission(int requestCode){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(this)) {
                needPermissionDialog(requestCode);
            }else{
                startChatHead();
            }

        }
    }

    private void needPermissionDialog(final int requestCode){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need to allow permission");
        builder.setPositiveButton("OK",
                (dialog, which) -> {
                    // TODO Auto-generated method stub
                    requestPermission(requestCode);
                });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // TODO Auto-generated method stub

        });
        builder.setCancelable(false);
        builder.show();
    }
}
