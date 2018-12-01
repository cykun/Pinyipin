package team.wucaipintu.pinyipin.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.PostItem;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PageViewHoder> {

    protected class PageViewHoder extends RecyclerView.ViewHolder {
        private ImageView headIV;
        private TextView nikenameTV;
        private TextView datatimeTV;
        private TextView titleTV;
        private TextView contentTV;

        public PageViewHoder(View view) {
            super(view);
            headIV = view.findViewById(R.id.head);
            nikenameTV = view.findViewById(R.id.nikename);
            datatimeTV = view.findViewById(R.id.datatime);
            titleTV = view.findViewById(R.id.title);
            contentTV = view.findViewById(R.id.content);
        }
    }

    private List<PostItem> postItems;

    public PostAdapter(List<PostItem> postItemList) {
        this.postItems = postItemList;
    }

    public void onBindViewHolder(PageViewHoder hoder, final int position) {
        PostItem p = postItems.get(position);
        hoder.headIV.setImageResource(R.drawable.defaulthead);
        hoder.titleTV.setText(p.getTitle());
        hoder.contentTV.setText(p.getContent());
        hoder.datatimeTV.setText(p.getDatatime());
        hoder.nikenameTV.setText(p.getNikeName());
        //hoder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return postItems.size();
    }

    @Override
    public PageViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PageViewHoder(view);
    }
}
