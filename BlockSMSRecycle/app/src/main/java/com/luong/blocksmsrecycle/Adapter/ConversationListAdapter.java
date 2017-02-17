package com.luong.blocksmsrecycle.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        TextView txtConversationListName, txtBodyMsg;
        public ViewHolder(View itemView) {
            super(itemView);

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
        holder.txtConversationListName.setText(conversations.get(position).getAddress());
        holder.txtBodyMsg.setText(conversations.get(position).getBody());
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


}
