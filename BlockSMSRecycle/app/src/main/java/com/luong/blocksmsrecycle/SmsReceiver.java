package com.luong.blocksmsrecycle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.luong.blocksmsrecycle.Database.DataHelper;

public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    public static final String SMS_URI= "content://sms/inbox";
    public static final String BODY= "body";
    public static final String ADDRESS= "address";
    DataHelper dataHelper;

    public void onReceive(Context context, Intent intent) {
        dataHelper = new DataHelper(context);
        processReceive(context, intent);
    }

    void processReceive(Context context, Intent intent)
    {
        Bundle extras = intent.getExtras();
        String message ="";
        if(extras != null)
        {
            Object []smsExtra = (Object[])extras.get(SMS_BUNDLE);
            for (int i = 0; i < smsExtra.length; i++)
            {
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);
                String body = sms.getMessageBody();
                String address = sms.getOriginatingAddress();
                long time = sms.getTimestampMillis();
                message +="SMS From: "+ address +" :\n"+body+"\n";
                XoaToanBoSmsRac(context, address);
                insertSMSGarbage(address,body, time);
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            Log.d("TEST", message+"test");
        }
    }

    private void insertSMSGarbage(String address, String body, long time) {
        dataHelper.insertSMSGar(address, body, String.valueOf(time));
    }

    void XoaToanBoSmsRac(Context con, String phoneNumber)
    {
        try {
            if(dataHelper.checkDumpNumber(phoneNumber))
            {
                con.getContentResolver().delete(Uri.parse("content://sms"), "address=?", new String[]{phoneNumber});
                Toast.makeText(con, "Xóa sms rác " + phoneNumber, Toast.LENGTH_SHORT).show();
            }
            else
            {
//                Toast.makeText(con, "Khong tìm thấy !" + phoneNumber, Toast.LENGTH_SHORT).show();
            }
            //}
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(con, "Xóa rồi đó " + e, Toast.LENGTH_SHORT).show();
        }
    }

}
