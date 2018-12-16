package team.wucaipintu.pinyipin.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import team.wucaipintu.pinyipin.R;

public class UserInfoActivity extends AppCompatActivity {
    public Toolbar toolbar;
    private ImageView avatar;
    private String nize;
    private String sex;
    private int age;
    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        avatar = (ImageView) findViewById(R.id.avatar);
        setinfo();
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void setinfo()
    {
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        age = preferences.getInt("age",1);
    }

}
