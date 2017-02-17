package com.luong.blocksmsrecycle.View.ruleblock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.luong.blocksmsrecycle.Database.DataHelper;
import com.luong.blocksmsrecycle.R;

import java.util.List;

/**
 * Created by pham on 2/17/2017.
 */
public class RuleBlockActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    EditText editPrefixPhoneNum;
    Button btnSave;
    ListView lvPhone;
    DataHelper dataHelper;
    ArrayAdapter<String> arrayAdapter;
    List<String> listPhoneGar;

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

        dataHelper = new DataHelper(this);
        loadData();

        btnSave.setOnClickListener(this);
        lvPhone.setOnItemLongClickListener(this);

    }

    void loadData() {
        listPhoneGar = dataHelper.getListPrePhone();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, listPhoneGar);
        lvPhone.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                boolean kt = dataHelper.insertData(editPrefixPhoneNum.getText().toString().trim());
                if (kt) {
                    Toast.makeText(this, "Add number success", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(this, "Add number fail", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        displayDialog(i);
        return false;
    }

    void displayDialog(int position) {
        AlertDialog.Builder bui = new AlertDialog.Builder(RuleBlockActivity.this);
        bui.setTitle("Cảnh báo !");
        //final int index = arg2;
        final String id= (String)listPhoneGar.get(position).toString().trim();
        bui.setPositiveButton("OK", new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //arrPreNum.remove(index);
//                sqlite = openOrCreateDatabase("smsgarbage.db", MODE_PRIVATE, null);
                if(dataHelper.deletePreNum(id))
                {
                    loadData();
//                    cleartext();
                    Toast.makeText(RuleBlockActivity.this, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(RuleBlockActivity.this, "Xóa thất bại !", Toast.LENGTH_SHORT).show();
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

}
