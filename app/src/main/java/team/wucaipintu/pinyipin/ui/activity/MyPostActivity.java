package team.wucaipintu.pinyipin.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.PostItem;
import team.wucaipintu.pinyipin.ui.Listener.RecyclerItemClickListener;
import team.wucaipintu.pinyipin.ui.adapter.PostAdapter;

public class MyPostActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private PostAdapter postAdapter;
    private List<PostItem> postItemList;

    private String phoneNumber;
    private String nikeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        postItemList=new ArrayList<>();
        phoneNumber=intent.getStringExtra("phoneNumber");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getActivity(),"hhahaha"+postItemList.get(position).getPostId(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyPostActivity.this, PostDetailActivity.class);
                intent.putExtra("postId",postItemList.get(position).getPostId());
                intent.putExtra("phoneNumber",phoneNumber);
                intent.putExtra("nikeName",nikeName);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        postAdapter = new PostAdapter(postItemList);
        recyclerView.setAdapter(postAdapter);
        getData();
    }

    public void getData(){

    }
}
