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
import com.luong.blocksmsrecycle.Interface.IItemClick;
import com.luong.blocksmsrecycle.Interface.IItemLongClick;
import com.luong.blocksmsrecycle.Model.conversationlist.Message;
import com.luong.blocksmsrecycle.R;

import java.util.List;

import static com.luong.blocksmsrecycle.Adapter.ConversationListAdapter.isPhoneNumberFormat;

/**
 * Created by pham on 2/22/2017.
 */

public class SMSGarbageAdapter extends RecyclerView.Adapter<SMSGarbageAdapter.ViewHolder> {

    Context context;
    List<Message> messageList;
    IItemClick iItemClick;
    IItemLongClick iItemLongClick;

    public SMSGarbageAdapter(Context context, List<Message> messageList, IItemClick iItemClick, IItemLongClick iItemLongClick) {
        this.context = context;
        this.messageList = messageList;
        this.iItemClick = iItemClick;
        this.iItemLongClick = iItemLongClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtConversationListName, txtBodyMsg;
        ImageView imgAvatar;
        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.conversation_list_avatar);
            txtConversationListName = (TextView) itemView.findViewById(R.id.conversation_list_name);
            txtBodyMsg = (TextView) itemView.findViewById(R.id.conversation_list_snippet);
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

        Message message = messageList.get(position);
        holder.txtConversationListName.setText(message.getAddress());
        holder.txtBodyMsg.setText(message.getContent());
        TextDrawable drawable = null;
        String nameAvatar = message.getAddress();
        if (isPhoneNumberFormat(nameAvatar)) {
            Drawable draw = ContextCompat.getDrawable(context, R.drawable.ic_person);
            holder.imgAvatar.setImageDrawable(draw);
        } else {
            drawable = TextDrawable.builder()
                    .buildRound(message.getAddress().substring(0,1), Color.BLUE);
            holder.imgAvatar.setImageDrawable(drawable);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                iItemLongClick.ItemLongClick(position);
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iItemClick.ItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


}
