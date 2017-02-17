package com.luong.blocksmsrecycle.View.ruleblock;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.luong.blocksmsrecycle.R;

/**
 * Created by pham on 2/17/2017.
 */
public class RuleBlockActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editPrefixPhoneNum;
    Button btnSave;
    ListView lvPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ruleblock);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editPrefixPhoneNum = (EditText) findViewById(R.id.editPrefixPhoneNum);
        btnSave = (Button) findViewById(R.id.btnSave);
        lvPhone = (ListView) findViewById(R.id.lvPhone);

        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                break;
        }
    }
}
