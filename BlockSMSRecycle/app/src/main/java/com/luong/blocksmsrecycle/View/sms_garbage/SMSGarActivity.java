package com.luong.blocksmsrecycle.View.sms_garbage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.luong.blocksmsrecycle.Database.DataHelper;

import java.util.List;

/**
 * Created by pham on 2/21/2017.
 */
public class SMSGarActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataHelper dataHelper = new DataHelper(this);
        List<String> strings = dataHelper.getListSMSGar();
        if (strings.size()> 0) {
            Log.d("TEST",strings.get(0));
        }
//tét
    }
}
