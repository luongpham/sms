package com.luong.blocksmsrecycle.Presenter.messagelist;

import android.content.Context;

import com.luong.blocksmsrecycle.Model.conversationlist.Conversation;
import com.luong.blocksmsrecycle.Model.conversationlist.Message;
import com.luong.blocksmsrecycle.Model.conversationlist.ModelConversation;
import com.luong.blocksmsrecycle.View.messagelist.ViewMessageList;

import java.util.List;

/**
 * Created by pham on 2/12/2017.
 */

public class PresenterLogicMessageList implements IPresenterMessageList {
    ViewMessageList viewMessageList;
    ModelConversation modelConversation;
    Context context;

    public PresenterLogicMessageList(ViewMessageList viewMessageList, Context context) {
        this.viewMessageList = viewMessageList;
        this.modelConversation = new ModelConversation(context);
        this.context = context;
    }

    @Override
    public void getListMessage(long threadId) {
        List<Message> listMessage = modelConversation.getListMessage(threadId);

        if (listMessage.size() > 0) {
            viewMessageList.DisplayMessage(listMessage);
        }
    }
}
