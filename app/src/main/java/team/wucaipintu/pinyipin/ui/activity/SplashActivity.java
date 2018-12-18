package team.wucaipintu.pinyipin.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.Contact;

public class SplashActivity extends AppCompatActivity {

    protected static  final String TAG="SplashActivity";
    private  static final String token1="vahxbUCQ41oszRTPUPtNR+ts5uTqc8KG1qDwl25eOByXB3xTOCUD1qX3AzA8d1YUeLUmfrtOVaHM+CPtv1YZ0g==";


    private void connectRongServer(String token){
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            @Override
            public void onTokenIncorrect() {
                Log.e(TAG,"token is error , please check token and appkey");
                Toast.makeText(SplashActivity.this, "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String s) {

                    //mUser1.setText("用户1连接服务器成功");
                    Log.e(TAG,"onSuccess :"+s);
                    Toast.makeText(SplashActivity.this, "connect server success 菠萝一号", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG,"connect failure errorCode is :"+errorCode.getValue());
                Toast.makeText(SplashActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        connectRongServer(token1);
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
