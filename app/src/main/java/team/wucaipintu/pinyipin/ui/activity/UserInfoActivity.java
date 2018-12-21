package team.wucaipintu.pinyipin.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.wucaipintu.pinyipin.R;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.head)
    ImageView headIV;

    @BindView(R.id.sex)
    TextView sexTV;

    @BindView(R.id.age)
    TextView ageTV;

    @BindView(R.id.address)
    TextView addressTV;

    @BindView(R.id.nikename)
    TextView nikeNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Window window = getWindow();
            View decorView = window.getDecorView();//
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); //可有可无
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setinfo();
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
        sexTV.setText(preferences.getString("sex",""));
        ageTV.setText(String.valueOf(preferences.getInt("age",0)));
        addressTV.setText(preferences.getString("address",""));
        nikeNameTV.setText(preferences.getString("nikeName",""));
    }

}
