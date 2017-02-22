package com.luong.blocksmsrecycle.Presenter.sms_garbage;

import android.content.Context;

import com.luong.blocksmsrecycle.Model.conversationlist.Message;
import com.luong.blocksmsrecycle.Model.sms_garbage.ModelSMSGarbage;
import com.luong.blocksmsrecycle.View.sms_garbage.ViewSMSGarbage;

import java.util.List;

/**
 * Created by pham on 2/21/2017.
 */

public class PresenterLogicSMSGarbage {
    Context context;
    ModelSMSGarbage modelSMSGarbage;
    ViewSMSGarbage viewSMSGarbage;

    public PresenterLogicSMSGarbage(Context context, ViewSMSGarbage viewSMSGarbage) {
        this.context = context;
        this.viewSMSGarbage = viewSMSGarbage;
        modelSMSGarbage = new ModelSMSGarbage(context);
    }

    public void getListSMSGarbage(){
        List<Message> messages = modelSMSGarbage.getListSMSGarbage(context);
        if (messages.size() >= 0) {
            viewSMSGarbage.DisplayMessage(messages);
        }
    }

    public void deleteSMSGar(String time) {
        boolean kt = modelSMSGarbage.deleteSMSGar(time);
        if (kt) {
            viewSMSGarbage.SuccessDelete();
        } else {
            viewSMSGarbage.FailDelete();
        }
    }
}
