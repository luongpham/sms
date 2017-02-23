package com.luong.blocksmsrecycle.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.luong.blocksmsrecycle.Interface.IItemClick;
import com.luong.blocksmsrecycle.Model.conversationlist.Conversation;
import com.luong.blocksmsrecycle.R;

import java.util.List;

/**
 * Created by pham on 2/12/2017.
 */

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ViewHolder> {

    Context context;
    List<Conversation> conversations;
    IItemClick iItemClick;

    public ConversationListAdapter(Context context, List<Conversation> conversations, IItemClick iItemClick) {
        this.context = context;
        this.conversations = conversations;
        this.iItemClick = iItemClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtConversationListName, txtBodyMsg, txtTime;
        ImageView imgAvatar;
        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.conversation_list_avatar);
            txtConversationListName = (TextView) itemView.findViewById(R.id.conversation_list_name);
            txtBodyMsg = (TextView) itemView.findViewById(R.id.conversation_list_snippet);
            txtTime = (TextView) itemView.findViewById(R.id.conversation_list_date);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.list_item_conversation,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Conversation conversation = conversations.get(position);
        int count = conversation.getMessageList().size();
        holder.txtConversationListName.setText(conversation.getAddress());
        holder.txtBodyMsg.setText(conversation.getBody());
        holder.txtTime.setText(MessageListAdapter.converteTimestamp(String.valueOf(conversation.getMessageList().get(count -1).getTime())));
        TextDrawable drawable = null;
        String nameAvatar = conversation.getAddress();
        if (isPhoneNumberFormat(nameAvatar)) {
            Drawable draw = ContextCompat.getDrawable(context, R.drawable.ic_person);
            holder.imgAvatar.setImageDrawable(draw);
        } else {
            drawable = TextDrawable.builder()
                    .buildRound(conversation.getAddress().substring(0,1), Color.BLUE);
            holder.imgAvatar.setImageDrawable(drawable);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iItemClick.ItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public static boolean isPhoneNumberFormat(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }

        char c = name.charAt(0);
        return !name.contains("@") && (c == '+' || c == '(' || Character.isDigit(c));
    }

}
