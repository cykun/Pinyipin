package team.wucaipintu.pinyipin.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.util.JsonUtil;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.editText_phoneNumber)
    EditText phoneNumberET;

    @BindView(R.id.editText_password)
    EditText passwordET;

    @BindView(R.id.editText_name)
    EditText nameET;

    @BindView(R.id.radioButton_boy)
    RadioButton boyRB;

    @BindView(R.id.radioButton_gril)
    RadioButton girlRB;

    @BindView(R.id.editText_age)
    EditText ageET;

    @BindView(R.id.editText_nikename)
    EditText nikenameET;

    @BindView(R.id.editText_region)
    EditText regionET;

    @BindView(R.id.button_register)
    Button registerBT;

    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        client = new OkHttpClient();
        initListener();
    }

    /**
     * 设置监听器
     */
    public void initListener() {
        registerBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberET.getText().toString();
                String password = passwordET.getText().toString();
                String name = nameET.getText().toString();
                String nikeName = nikenameET.getText().toString();
                String age = ageET.getText().toString();
                String region = regionET.getText().toString();
                String sex = "";
                if (boyRB.isChecked()) {
                    sex = "男";
                } else if (girlRB.isChecked()) {
                    sex = "女";
                }


                if (phoneNumber.equals("") || password.equals("") || name.equals("") || nikeName.equals("") || age.equals("") || region.equals("") || sex.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("错误");
                    builder.setMessage("不能为空");
                    builder.setPositiveButton("好", null);
                    builder.show();
                } else {
                    PostThread postThread = new PostThread(phoneNumber, password, name, nikeName, sex, age, region);
                    postThread.start();
                }
            }
        });
    }

    public class PostThread extends Thread {

        private String phoneNumber;
        private String password;
        private String name;
        private String nikeName;
        private String sex;
        private String age;
        private String region;

        public PostThread(String phoneNumber, String password, String name, String nikeName, String sex, String age, String region) {
            this.password = password;
            this.phoneNumber = phoneNumber;
            this.age = age;
            this.sex = sex;
            this.name = name;
            this.nikeName = nikeName;
            this.region = region;
        }

        @Override
        public void run() {
            FormBody formBody = new FormBody.Builder()
                    .add("phoneNumber", phoneNumber)
                    .add("password", password)
                    .add("name", name)
                    .add("nikeName", nikeName)
                    .add("age", age)
                    .add("sex", sex)
                    .add("address", region)
                    .build();
            Request request = new Request.Builder()
                    .post(formBody)
                    .url("http://39.108.37.77:8080/pinyipin1/user/register")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("错误");
                    builder.setMessage(e.getMessage());
                    builder.setPositiveButton("好", null);
                    builder.show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String data = response.body().string();
                    int code = JsonUtil.getValue(data, "code");
                    if (code == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setTitle("错误");
                        builder.setMessage(data);
                        Looper.prepare();
                        builder.setPositiveButton("好", null);
                        builder.show();
                        Looper.loop();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setTitle("成功");
                        builder.setMessage("注册成功");
                        Looper.prepare();
                        builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.show();
                        Looper.loop();
                    }
                }
            });
        }
    }

}
