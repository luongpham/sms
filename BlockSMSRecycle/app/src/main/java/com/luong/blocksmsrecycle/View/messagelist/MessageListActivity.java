package com.luong.blocksmsrecycle.View.messagelist;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.luong.blocksmsrecycle.Adapter.MessageListAdapter;
import com.luong.blocksmsrecycle.Model.conversationlist.Conversation;
import com.luong.blocksmsrecycle.Model.conversationlist.Message;
import com.luong.blocksmsrecycle.Presenter.messagelist.PresenterLogicMessageList;
import com.luong.blocksmsrecycle.R;
import com.luong.blocksmsrecycle.SmsReceiver;

import java.util.List;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * Created by pham on 2/12/2017.
 */

public class MessageListActivity extends AppCompatActivity implements View.OnClickListener, ViewMessageList {

    //Views UI
    private RecyclerView rvListMessage;
    private LinearLayoutManager mLinearLayoutManager;
    private ImageView btSendMessage,btEmoji;
    private EmojiconEditText edMessage;
    private View contentRoot;
    private EmojIconActions emojIcon;

    MessageListAdapter messageListAdapter;
    PresenterLogicMessageList presenterLogicMessageList;
    Conversation conversation;
    BroadcastReceiver broadcastReceiverSENT;
    BroadcastReceiver broadcastReceiverDELIVERED;
    List<Message> messageList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_message_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); //or whatever you used to do on your onOptionItemSelected's android.R.id.home callback
            }
        });

        bindViews();
        registerReceiver(broadcastReceiver, new IntentFilter(SmsReceiver.ACTION_UPDATE_LESSON_NAME));

        long threadId = getIntent().getLongExtra("threadId",-1);
        conversation = (Conversation) getIntent().getSerializableExtra("message");
        getSupportActionBar().setTitle(conversation.getAddress());

        messageList = conversation.getMessageList();

        Log.d("TEST",threadId+" "+ conversation.getMessageList().size());
        messageListAdapter = new MessageListAdapter(this, messageList, conversation.getAddress());

        messageListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                int friendlyMessageCount = messageListAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    rvListMessage.scrollToPosition(positionStart);
                }
            }
        });
        rvListMessage.setLayoutManager(mLinearLayoutManager);
        rvListMessage.setAdapter(messageListAdapter);

        presenterLogicMessageList = new PresenterLogicMessageList(this,this);
//        presenterLogicMessageList.getListMessage(threadId);

    }




    private void bindViews(){
        contentRoot = findViewById(R.id.contentRoot);
        edMessage = (EmojiconEditText)findViewById(R.id.editTextMessage);
        btSendMessage = (ImageView)findViewById(R.id.buttonMessage);
        btSendMessage.setOnClickListener(this);
        btEmoji = (ImageView)findViewById(R.id.buttonEmoji);
        emojIcon = new EmojIconActions(this,contentRoot,edMessage,btEmoji);
        emojIcon.ShowEmojIcon();
        rvListMessage = (RecyclerView)findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);


    }

    @Override
    public void onClick(View view) {
        if (edMessage.getText().toString().trim().length()>0) {
            sendSMS(conversation.getNumberPhone(), edMessage.getText().toString().trim());
            Message message = new Message();
            message.setContent(edMessage.getText().toString().trim());
            message.setType(2);
            messageList.add(message);
            messageListAdapter.refreshData(messageList);
            edMessage.setText("");
        }
        else
            Toast.makeText(getBaseContext(),
                    "Please enter both phone number and message.",
                    Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DisplayMessage(List<Message> messageList) {
        messageListAdapter = new MessageListAdapter(this,messageList, conversation.getAddress());
        rvListMessage.setLayoutManager(mLinearLayoutManager);
        rvListMessage.setAdapter(messageListAdapter);
        messageListAdapter.notifyDataSetChanged();
    }

    private void sendSMS(final String phoneNumber, final String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        ContentValues values = new ContentValues();
        values.put("address", phoneNumber);
        values.put("body", message);
        getContentResolver().insert(Uri.parse("content://sms/sent"), values);

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        // ---when the SMS has been sent---
        broadcastReceiverSENT = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {

                switch (getResultCode()) {

                    case Activity.RESULT_OK:

                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:

                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:

                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:

                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_RADIO_OFF:

                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        registerReceiver(broadcastReceiverSENT, new IntentFilter(SENT));

        // ---when the SMS has been delivered---
        broadcastReceiverDELIVERED = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {

                switch (getResultCode()) {

                    case Activity.RESULT_OK:

                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();

                        break;

                    case Activity.RESULT_CANCELED:

                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        registerReceiver(broadcastReceiverDELIVERED, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String kt = bundle.getString("sms");
            String body = bundle.getString("body");
            if (kt.equals("yes")) {
                Message message = new Message();
                message.setContent(body);
                message.setType(1);
                messageList.add(message);
                messageListAdapter.refreshData(messageList);
            }

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiverSENT != null && broadcastReceiverDELIVERED != null){
            unregisterReceiver(broadcastReceiverSENT);
            unregisterReceiver(broadcastReceiverDELIVERED);
        }
        if (broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }


    }
}
