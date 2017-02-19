package com.luong.blocksmsrecycle.View.messagelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.luong.blocksmsrecycle.Adapter.MessageListAdapter;
import com.luong.blocksmsrecycle.Model.conversationlist.Conversation;
import com.luong.blocksmsrecycle.Model.conversationlist.Message;
import com.luong.blocksmsrecycle.Presenter.messagelist.PresenterLogicMessageList;
import com.luong.blocksmsrecycle.R;

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




        long threadId = getIntent().getLongExtra("threadId",-1);
        conversation = (Conversation) getIntent().getSerializableExtra("message");
        getSupportActionBar().setTitle(conversation.getAddress());

        Log.d("TEST",threadId+" "+ conversation.getMessageList().size());
        messageListAdapter = new MessageListAdapter(this,conversation.getMessageList(), conversation.getAddress());
        rvListMessage.setLayoutManager(mLinearLayoutManager);
        rvListMessage.setAdapter(messageListAdapter);
        messageListAdapter.notifyDataSetChanged();

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

    }

    @Override
    public void DisplayMessage(List<Message> messageList) {
        messageListAdapter = new MessageListAdapter(this,messageList, conversation.getAddress());
        rvListMessage.setLayoutManager(mLinearLayoutManager);
        rvListMessage.setAdapter(messageListAdapter);
        messageListAdapter.notifyDataSetChanged();
    }
}
