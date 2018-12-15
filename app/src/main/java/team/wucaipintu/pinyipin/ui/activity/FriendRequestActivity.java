package team.wucaipintu.pinyipin.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.NewFriendRequest;
import team.wucaipintu.pinyipin.ui.adapter.FriendRequestAdapter;

public class FriendRequestActivity extends AppCompatActivity {

    @BindView(R.id.rv_newfriend)
    RecyclerView newfriendRV;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<NewFriendRequest> requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendrequest);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();
        newfriendRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        newfriendRV.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        newfriendRV.setAdapter(new FriendRequestAdapter(requests));
    }

    public void initData(){
        requests=new ArrayList<>();
        NewFriendRequest request=new NewFriendRequest(R.drawable.name,"郑西坤",0);

        for (int i=0;i<8;i++){
            requests.add(request);
        }
    }
}
