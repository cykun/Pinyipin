package team.wucaipintu.pinyipin.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.Contact;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE);
        String phoneNumber=sharedPreferences.getString("phoneNumber","");
        final int userId=sharedPreferences.getInt("userId",0);
        final String nikeName=sharedPreferences.getString("nikeName","");
        if(phoneNumber.equals("")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.exit(1);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }
                    });
                }
            }).start();
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.exit(1);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            intent.putExtra("nikeName",nikeName);
                            intent.putExtra("userId",userId);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }
                    });
                }
            }).start();
        }
    }
}
