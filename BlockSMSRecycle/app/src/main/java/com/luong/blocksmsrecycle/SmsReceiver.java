package com.luong.blocksmsrecycle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        processReceive(context, intent);
    }

    void processReceive(Context context, Intent intent)
    {
        Bundle extras = intent.getExtras();
        String message ="";
        if(extras != null)
        {
            Object []smsExtra = (Object[])extras.get("pdus");
            for (int i = 0; i < smsExtra.length; i++)
            {
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);
                String body = sms.getMessageBody();
                String address = sms.getOriginatingAddress();
                message +="SMS From: "+ address +" :\n"+body+"\n";
//                XoaToanBoSms(context, address);
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            Log.d("TEST", message+"test");
        }
    }

}
