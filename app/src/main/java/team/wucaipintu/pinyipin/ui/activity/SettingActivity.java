
package team.wucaipintu.pinyipin.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.wucaipintu.pinyipin.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.linearlayout_about)
    public LinearLayout aboutLL;

    @BindView(R.id.linearlayout_exit)
    public LinearLayout exitLL;

    @BindView(R.id.switch_button)
    public com.suke.widget.SwitchButton switchButton;

    @BindView(R.id.linearlayout_logout)
    public LinearLayout logoutLL;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private boolean isChecked=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initSwitchButton();
        aboutLL.setOnClickListener(this);
        exitLL.setOnClickListener(this);
        logoutLL.setOnClickListener(this);
       // initListener();
    }

    public void initSwitchButton(){
        switchButton.setChecked(isChecked);

        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.linearlayout_about:{
                Intent intent=new Intent(SettingActivity.this,AboutActivity.class);
                startActivity(intent);
            }break;
            case R.id.linearlayout_logout:{
                SharedPreferences sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("phoneNumber","");
                editor.commit();
            }break;
            case R.id.linearlayout_exit:{
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }break;
        }
    }

}
