package team.wucaipintu.pinyipin.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.NewFriendRequest;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.VH> {

    public class VH extends RecyclerView.ViewHolder{
        private ImageView headIV;
        private TextView nameTV;
        private TextView requestTV;
        public VH(View view){
            super(view);
            headIV=view.findViewById(R.id.iv_head);
            nameTV=view.findViewById(R.id.tv_name);
            requestTV=view.findViewById(R.id.tv_request);
        }
    }

    public FriendRequestAdapter(List<NewFriendRequest> requestList){
        requests=requestList;
    }

    private List<NewFriendRequest> requests;

    @Override
    public int getItemCount() {
        return requests.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        NewFriendRequest request=requests.get(position);
        holder.headIV.setImageResource(request.getHeadId());
        holder.nameTV.setText(request.getName());
        if(request.getRequest()==0){
            holder.requestTV.setText("同意");
        }else{
            holder.requestTV.setText("已同意");
        }
        holder.requestTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friendrequst,parent,false);
        return new VH(view);
    }
}
