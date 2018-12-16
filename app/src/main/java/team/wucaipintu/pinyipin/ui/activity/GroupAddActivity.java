package team.wucaipintu.pinyipin.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.wucaipintu.pinyipin.R;

public class GroupAddActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String phoneNumber;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add);
        Intent intent=getIntent();
        phoneNumber=intent.getStringExtra("phoneNumber");
        groupId=intent.getStringExtra("groupId");
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
    }
}
