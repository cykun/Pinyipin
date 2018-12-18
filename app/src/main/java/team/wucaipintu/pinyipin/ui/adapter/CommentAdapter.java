package team.wucaipintu.pinyipin.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.Comment;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommnetHolder> {

    public class CommnetHolder extends RecyclerView.ViewHolder {
        private ImageView headIV;
        private TextView nikenameTV;
        private TextView contentTV;
        private TextView datatimeTV;

        public CommnetHolder(View view) {
            super(view);
            headIV = view.findViewById(R.id.iv_head);
            nikenameTV = view.findViewById(R.id.tv_nikename);
            contentTV = view.findViewById(R.id.tv_comment);
            datatimeTV = view.findViewById(R.id.tv_datatime);
        }
    }

    public List<Comment> comments;

    public CommentAdapter(List<Comment> commentList) {
        this.comments = commentList;
    }

    @Override
    public void onBindViewHolder(CommnetHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.headIV.setImageResource(R.drawable.name);
        holder.nikenameTV.setText(comment.nikeName);
        holder.datatimeTV.setText(comment.releaseTime);
        holder.contentTV.setText(comment.content);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public CommnetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommnetHolder(view);
    }
}
