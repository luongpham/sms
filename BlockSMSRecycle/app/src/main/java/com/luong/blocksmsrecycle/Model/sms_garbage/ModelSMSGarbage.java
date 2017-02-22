package com.luong.blocksmsrecycle.Model.sms_garbage;

import android.content.Context;

import com.luong.blocksmsrecycle.Database.DataHelper;
import com.luong.blocksmsrecycle.Model.conversationlist.Message;

import java.util.List;

/**
 * Created by pham on 2/21/2017.
 */

public class ModelSMSGarbage {
    Context context;
    DataHelper dataHelper;

    public ModelSMSGarbage(Context context) {
        this.context = context;
        dataHelper = new DataHelper(context);
    }

    public List<Message> getListSMSGarbage(Context context) {
        List<Message> messages = dataHelper.getListSMSGar();
        return messages;
    }

    public boolean deleteSMSGar(String time) {
        boolean kt = dataHelper.deleteSMSGar(time);
        return kt;
    }
}
