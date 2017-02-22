package com.luong.blocksmsrecycle.View.sms_garbage;

import com.luong.blocksmsrecycle.Model.conversationlist.Message;

import java.util.List;

/**
 * Created by pham on 2/21/2017.
 */

public interface ViewSMSGarbage {
    void DisplayMessage(List<Message> messageList);
    void SuccessDelete();
    void FailDelete();
}
