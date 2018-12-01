package team.wucaipintu.pinyipin.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.Group;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder>{
    public class GroupViewHolder extends RecyclerView.ViewHolder{
        private ImageView headIV;
        private TextView nameTV;

        public GroupViewHolder(View view){
            super(view);
            headIV=view.findViewById(R.id.iv_head);
            nameTV=view.findViewById(R.id.tv_name);
        }
    }

    private ArrayList<Group> groups;

    public GroupAdapter (ArrayList<Group> groups){
        this.groups=groups;
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position){
        Group group=groups.get(position);
        holder.headIV.setImageResource(R.drawable.name);
        holder.nameTV.setText(group.groupName);
    }

    @Override
    public int getItemCount(){
        return groups.size();
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new GroupViewHolder(view);
    }
}
