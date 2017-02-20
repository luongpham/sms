package com.luong.blocksmsrecycle;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.luong.blocksmsrecycle.View.conversationlist.ConversationListFragment;
import com.luong.blocksmsrecycle.View.ruleblock.RuleBlockActivity;
import com.luong.blocksmsrecycle.View.sms_garbage.SMSGarActivity;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    public static long sThreadShowing;
    public static final int DELETE_CONVERSATION_TOKEN = 1801;
    public static final int HAVE_LOCKED_MESSAGES_TOKEN = 1802;
    public static final String MMS_SETUP_DONT_ASK_AGAIN = "mmsSetupDontAskAgain";

    private ConversationListFragment mConversationList;

    /**
     * True if the mms setup fragment has been dismissed and we shouldn't show it anymore.
     */
    private final String KEY_MMS_SETUP_FRAGMENT_DISMISSED = "mmsSetupFragmentShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();
                switch (id){
                    case R.id.item_message:
                        break;
                    case R.id.item_msg_recy:
                        Intent iMSGGAR = new Intent(MainActivity.this,SMSGarActivity.class);
                        startActivity(iMSGGAR);
                        break;
                    case R.id.item_rule:
                        Intent iRule = new Intent(MainActivity.this, RuleBlockActivity.class);
                        startActivity(iRule);
                        break;
                    case R.id.item_msg_like:
                        break;
                    case R.id.item_setting:
                        break;
                    case R.id.item_info:
                        break;
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


       FragmentManager fm = getSupportFragmentManager();
        mConversationList = (ConversationListFragment) fm.findFragmentByTag(ConversationListFragment.TAG);
        if (mConversationList == null) {
            mConversationList = new ConversationListFragment();
        }

        FragmentTransaction menuTransaction = fm.beginTransaction();
        menuTransaction.replace(R.id.content_frame, mConversationList, ConversationListFragment.TAG);
        menuTransaction.commit();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return true;
    }


}
