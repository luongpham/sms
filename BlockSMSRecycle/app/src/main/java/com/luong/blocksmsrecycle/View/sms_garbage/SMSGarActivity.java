package com.luong.blocksmsrecycle.View.sms_garbage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.luong.blocksmsrecycle.Adapter.SMSGarbageAdapter;
import com.luong.blocksmsrecycle.Interface.IItemClick;
import com.luong.blocksmsrecycle.Interface.IItemLongClick;
import com.luong.blocksmsrecycle.Model.conversationlist.Message;
import com.luong.blocksmsrecycle.Presenter.sms_garbage.PresenterLogicSMSGarbage;
import com.luong.blocksmsrecycle.R;

import java.util.List;

/**
 * Created by pham on 2/21/2017.
 */
public class SMSGarActivity extends AppCompatActivity implements ViewSMSGarbage, IItemClick, IItemLongClick {
    RecyclerView recyclerView;
    PresenterLogicSMSGarbage presenterLogicSMSGarbage;
    SMSGarbageAdapter messageListAdapter;
    List<Message> messageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sms_garbage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("SMS Garbage");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.messageRecyclerView);
        presenterLogicSMSGarbage = new PresenterLogicSMSGarbage(this,this);
        presenterLogicSMSGarbage.getListSMSGarbage();
    }

    @Override
    public void DisplayMessage(List<Message> messageList) {
        this.messageList = messageList;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        messageListAdapter = new SMSGarbageAdapter(this,messageList, this, this);
        recyclerView.setAdapter(messageListAdapter);

    }

    @Override
    public void SuccessDelete() {
        Toast.makeText(this, "Delete success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void FailDelete() {
        Toast.makeText(this, "Delete fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ItemClick(int position) {
        displaySMSGar(position);
    }

    @Override
    public void ItemLongClick(int postion) {
        displayDialog(postion);
    }

    void displayDialog(final int position) {
        AlertDialog.Builder bui = new AlertDialog.Builder(SMSGarActivity.this);
        bui.setTitle("Xóa tin nhắn rác !");
        //final int index = arg2;

        bui.setPositiveButton("OK", new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                presenterLogicSMSGarbage.deleteSMSGar(String.valueOf(messageList.get(position).getTime()));
                presenterLogicSMSGarbage.getListSMSGarbage();
            }
        });
        bui.setNegativeButton("Cancel", new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });

        bui.create().show();
    }

    private void displaySMSGar(int pos) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(SMSGarActivity.this);
        builder1.setTitle(messageList.get(pos).getAddress());
        builder1.setMessage(messageList.get(pos).getContent());
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
