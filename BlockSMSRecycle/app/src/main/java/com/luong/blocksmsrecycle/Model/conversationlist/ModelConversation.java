package com.luong.blocksmsrecycle.Model.conversationlist;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pham on 2/12/2017.
 */

public class ModelConversation {
    Context mContext;

    public ModelConversation(Context context) {
        this.mContext = context;
    }

    public List<Conversation> getListConversation() {
        List<Conversation> conversations = new ArrayList<>();


        Uri SMS_INBOX = Uri.parse("content://sms/conversations/");
        Cursor c = mContext.getContentResolver().query(SMS_INBOX, null, null, null, "date desc");

        String[] count = new String[c.getCount()];
//        String[] snippet = new String[c.getCount()];
        String[] thread_id = new String[c.getCount()];

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            count[i] = c.getString(c.getColumnIndexOrThrow("msg_count"))
                    .toString();
            thread_id[i] = c.getString(c.getColumnIndexOrThrow("thread_id"))
                    .toString();
//            snippet[i] = c.getString(c.getColumnIndexOrThrow("snippet"))
//                    .toString();
            //Toast.makeText(getApplicationContext(), count[i] + " - " + thread_id[i]+" - "+snippet[i] , Toast.LENGTH_LONG).show();
            c.moveToNext();

//            Log.d("TEST", thread_id[i]+" "+count[i]);
        }
        c.close();

        for(int ad = 0; ad < thread_id.length ; ad++) {
            List<Message> messages = new ArrayList<>();
            Uri uri = Uri.parse("content://sms/");
            String where = "thread_id=" + thread_id[ad];
            Cursor mycursor = mContext.getContentResolver().query(uri, null, where, null, "date asc");
//            mContext.startManagingCursor(mycursor);

//            String[] number = new String[mycursor.getCount()];
            String[] body = new String[mycursor.getCount()];

            Conversation conversation = new Conversation();
            conversation.setThreadId(Long.parseLong(thread_id[ad]));


//            Log.d("TEST", thread_id[ad] );
            String number = null;
            if (mycursor.moveToFirst()) {
                number = mycursor.getString(mycursor.getColumnIndexOrThrow("address")).toString();
                for (int i = 0; i < mycursor.getCount(); i++) {
//                    number[i] = mycursor.getString(mycursor.getColumnIndexOrThrow("address")).toString();
                    body[i] = mycursor.getString(mycursor.getColumnIndexOrThrow("body")).toString();
                    Message message = new Message();
                    message.setContent(body[i]);
                    message.setType(Integer.parseInt(mycursor.getString(mycursor.getColumnIndexOrThrow("type"))));

                    messages.add(message);

                    mycursor.moveToNext();
//                    Log.d("TEST",  "number " + number[i]);
                }
                conversation.setMessageList(messages);
            }
            conversation.setAddress(number);
            conversation.setBody(body[mycursor.getCount()-1]);
            conversations.add(conversation);

            mycursor.close();
        }
        return conversations;
    }


    public List<Message> getListMessage(long threadId) {

        List<Message> messages = new ArrayList<>();
        Uri uri = Uri.parse("content://sms/");
        String where = "thread_id=" + threadId;
        Cursor mycursor = mContext.getContentResolver().query(uri, null, where, null, "date asc");
//            mContext.startManagingCursor(mycursor);


        String[] body = new String[mycursor.getCount()];
//        String number[0] = mycursor.getString(mycursor.getColumnIndexOrThrow("address")).toString();


        if (mycursor.moveToFirst()) {
            for (int i = 0; i < mycursor.getCount(); i++) {

                body[i] = mycursor.getString(mycursor.getColumnIndexOrThrow("body")).toString();
                Message message = new Message();
                message.setContent(body[i]);
                message.setType(Integer.parseInt(mycursor.getString(mycursor.getColumnIndexOrThrow("type"))));

                messages.add(message);

                mycursor.moveToNext();
            }
        }


        mycursor.close();

        return messages;
    }
}
