package com.luong.blocksmsrecycle.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.luong.blocksmsrecycle.Model.conversationlist.Message;
import com.luong.blocksmsrecycle.R;

import java.util.List;

import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

import static com.luong.blocksmsrecycle.Adapter.ConversationListAdapter.isPhoneNumberFormat;

/**
 * Created by pham on 2/12/2017.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    Context context;
    List<Message> messageList;
    String address;

    private static final int RIGHT_MSG = 0;
    private static final int LEFT_MSG = 1;

    public MessageListAdapter(Context context, List<Message> conversations, String address) {
        this.context = context;
        this.messageList = conversations;
        this.address = address;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTimestamp,tvLocation,txtName;
        EmojiconTextView txtMessage;
        ImageView ivUser,ivChatPhoto;
        public ViewHolder(View itemView) {
            super(itemView);

            tvTimestamp = (TextView)itemView.findViewById(R.id.timestamp);
            txtMessage = (EmojiconTextView)itemView.findViewById(R.id.txtMessage);
//            tvLocation = (TextView)itemView.findViewById(R.id.tvLocation);
//            ivChatPhoto = (ImageView)itemView.findViewById(R.id.img_chat);
            ivUser = (ImageView)itemView.findViewById(R.id.ivUserChat);
//            txtName = (TextView) itemView.findViewById(R.id.txtName);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == RIGHT_MSG){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_right,parent,false);
//            view.findViewById(R.id.ivUserChat).setVisibility(View.GONE);
            return new ViewHolder(view);
        }else if (viewType == LEFT_MSG){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_left,parent,false);
            return new ViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.txtMessage.setText(message.getContent());
        if (getItemViewType(position) == LEFT_MSG) {
            TextDrawable drawable = null;
            String nameAvatar = address;
            if (isPhoneNumberFormat(nameAvatar)) {
                Drawable draw = ContextCompat.getDrawable(context, R.drawable.ic_person);
                holder.ivUser.setImageDrawable(draw);
            } else {
                drawable = TextDrawable.builder()
                        .buildRound(nameAvatar.substring(0,1), Color.BLUE);
                holder.ivUser.setImageDrawable(drawable);
            }
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (message != null){
            if (message.getType() == 1){
                return LEFT_MSG;
            }else{
                return RIGHT_MSG;
            }
        }
        return -1;
    }

}
