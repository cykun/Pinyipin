package team.wucaipintu.pinyipin.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.Dialog;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    public class  MessageViewHolder extends RecyclerView.ViewHolder {
        private ImageView headIV;
        private TextView nameTV;
        private TextView msgTV;
        private TextView datatimeTV;
        public MessageViewHolder(View view){
            super(view);
            headIV=view.findViewById(R.id.iv_head);
            nameTV=view.findViewById(R.id.tv_name);
            msgTV=view.findViewById(R.id.tv_msg);
            datatimeTV=view.findViewById(R.id.tv_datatime);
        }
    }

    private List<Dialog> dialogs;

    public MessageAdapter(List<Dialog> dialogList){
        this.dialogs = dialogList;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Dialog msg= dialogs.get(position);
        holder.headIV.setImageResource(msg.getHeadId());
        holder.nameTV.setText(msg.getName());
        holder.datatimeTV.setText(msg.getDatatime());
        holder.msgTV.setText(msg.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dialogs.size();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }
}
