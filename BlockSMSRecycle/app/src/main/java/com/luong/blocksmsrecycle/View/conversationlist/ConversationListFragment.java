package com.luong.blocksmsrecycle.View.conversationlist;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.luong.blocksmsrecycle.Adapter.ConversationListAdapter;
import com.luong.blocksmsrecycle.Interface.IItemClick;
import com.luong.blocksmsrecycle.Model.conversationlist.Conversation;
import com.luong.blocksmsrecycle.Presenter.conversationlist.PresenterLogicGetListConversation;
import com.luong.blocksmsrecycle.R;
import com.luong.blocksmsrecycle.SmsReceiver;
import com.luong.blocksmsrecycle.View.compose.ComposeActivity;
import com.luong.blocksmsrecycle.View.messagelist.MessageListActivity;

import java.util.List;

/**
 * Created by pham on 2/12/2017.
 */
public class ConversationListFragment extends Fragment implements ViewGetListConversation, IItemClick, View.OnClickListener {
    public static final String TAG = "ConversationListFragment";
    public static final int REQUES_CODE = 1;

    PresenterLogicGetListConversation presenterLogicGetListConversation;
    RecyclerView recyclerView;
    ConversationListAdapter conversationListAdapter;
    List<Conversation> conversationList;
    FloatingActionButton fab;

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversation_list_fragment,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.conversations_list);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);



        presenterLogicGetListConversation = new PresenterLogicGetListConversation(this,getContext());
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(SmsReceiver.ACTION_UPDATE_LESSON_NAME));


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        } else {
//            refreshSmsInbox();
            presenterLogicGetListConversation.getListConversation();
        }

        return view;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_SMS)) {
                Toast.makeText(getActivity(), "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_SMS},
                    READ_SMS_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Read SMS permission granted", Toast.LENGTH_SHORT).show();
//                refreshSmsInbox();
                presenterLogicGetListConversation.getListConversation();
            } else {
                Toast.makeText(getActivity(), "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    @Override
    public void DisplayListConversation(List<Conversation> conversations) {
        conversationList = conversations;
        for (Conversation conversation : conversations) {
            Log.d("TEST", conversation.getAddress()+" "+ conversation.getBody());
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        conversationListAdapter = new ConversationListAdapter(getContext(),conversations, this);
        recyclerView.setAdapter(conversationListAdapter);
        conversationListAdapter.notifyDataSetChanged();
    }

    @Override
    public void ItemClick(int postion) {
        long threadId = conversationList.get(postion).getThreadId();
        Intent iMessage = new Intent(getActivity(), MessageListActivity.class);
        iMessage.putExtra("threadId", threadId);
        iMessage.putExtra("message",conversationList.get(postion));
        startActivity(iMessage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                startActivityForResult(new Intent(getActivity(),ComposeActivity.class), REQUES_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUES_CODE && resultCode == Activity.RESULT_OK) {
            presenterLogicGetListConversation.getListConversation();
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String kt = bundle.getString("sms");
            if (kt.equals("yes")) {
                presenterLogicGetListConversation.getListConversation();
            }

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }
}
