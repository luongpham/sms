package com.luong.blocksmsrecycle.Presenter.conversationlist;

import android.content.Context;

import com.luong.blocksmsrecycle.Model.conversationlist.Conversation;
import com.luong.blocksmsrecycle.Model.conversationlist.ModelConversation;
import com.luong.blocksmsrecycle.View.conversationlist.ViewGetListConversation;

import java.util.List;

/**
 * Created by pham on 2/12/2017.
 */

public class PresenterLogicGetListConversation implements IPresenterGetListConversation{
    ViewGetListConversation viewGetListConversation;
    ModelConversation modelConversation;
    Context context;

    public PresenterLogicGetListConversation(ViewGetListConversation viewGetListConversation, Context context) {
        this.viewGetListConversation = viewGetListConversation;
        this.modelConversation = new ModelConversation(context);
        this.context = context;
    }

    @Override
    public void getListConversation() {
        List<Conversation> conversations = modelConversation.getListConversation();
        if (conversations.size() > 0) {
            viewGetListConversation.DisplayListConversation(conversations);
        }
    }
}
