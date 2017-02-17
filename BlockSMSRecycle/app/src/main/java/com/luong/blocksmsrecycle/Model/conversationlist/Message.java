package com.luong.blocksmsrecycle.Model.conversationlist;

import java.io.Serializable;

/**
 * Created by pham on 2/12/2017.
 */

public class Message implements Serializable{
    private String content;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
