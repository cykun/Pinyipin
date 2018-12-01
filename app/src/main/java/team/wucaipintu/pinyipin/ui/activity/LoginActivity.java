package team.wucaipintu.pinyipin.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.wucaipintu.pinyipin.R;

public class LoginActivity extends AppCompatActivity {
    private EditText phoneNumberET;
    private EditText passwordET;
    private TextView forgetPasswordTV;
    private TextView registerTV;
    private Button loginB;

    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    public void initView() {
        phoneNumberET = (EditText) findViewById(R.id.editText_phoneNumber);
        passwordET = (EditText) findViewById(R.id.editText_password);
        forgetPasswordTV = (TextView) findViewById(R.id.textView_forgetPassword);
        registerTV = (TextView) findViewById(R.id.textView_register);
        loginB = (Button) findViewById(R.id.button_login);
        okHttpClient = new OkHttpClient();
    }

    public void initListener() {
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenumber = phoneNumberET.getText().toString();
                String password = passwordET.getText().toString();
                if (phonenumber.equals("") || password.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("错误");
                    builder.setMessage("手机号/账号不能为空");
                    builder.setPositiveButton("好", null);
                    builder.show();
                } else {
                    PostThread postThread = new PostThread(phonenumber, password);
                    postThread.start();
                }
            }
        });

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private class PostThread extends Thread {

        private String phoneNumber;
        private String password;

        private PostThread(String phoneNumber, String password) {
            this.phoneNumber = phoneNumber;
            this.password = password;
        }

        @Override
        public void run() {
            FormBody formBody = new FormBody.Builder()
                    .add("phoneNumber", phoneNumber)
                    .add("password", password)
                    .build();
            Request request = new Request.Builder()
                    .post(formBody)
                    .url("http://39.108.37.77:8080/pinyipin/user/login")
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("错误");
                    builder.setMessage(e.getMessage());
                    builder.setPositiveButton("好", null);
                    builder.show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String data=response.body().string();
                    JsonObject object=new JsonParser().parse(data).getAsJsonObject();
                    int code=object.get("code").getAsInt();
                    if (code==0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("错误");
                        builder.setMessage("账号/密码错误");
                        builder.setPositiveButton("好", null);
                        Looper.prepare();
                        builder.show();
                        Looper.loop();
                    } else {
                        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("phoneNumber", phoneNumber);
                        editor.putInt("userId",object.get("userId").getAsInt());
                        editor.putString("name",object.get("name").getAsString());
                        editor.putString("nikeName",object.get("nikeName").getAsString());
                        editor.putString("region",object.get("region").getAsString());
                        editor.putString("sex",object.get("sex").getAsString());
                        editor.putInt("age",object.get("age").getAsInt());
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //intent.putExtra("phoneNumber",phoneNumber );
                        intent.putExtra("userId",object.get("userId").getAsInt());
                        intent.putExtra("nikeName",object.get("nikeName").getAsString());
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }
}

