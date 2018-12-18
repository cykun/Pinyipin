package team.wucaipintu.pinyipin.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

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

public class PostReleaseActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.s_type)
    public Spinner typeS;

    private OkHttpClient okHttpClient;

    @BindView(R.id.et_title)
    public EditText titleET;

    @BindView(R.id.et_content)
    public EditText contentET;

    @BindView(R.id.et_deadline)
    public EditText deadlineET;

    @BindView(R.id.et_population)
    public EditText populationET;

    @BindView(R.id.et_location)
    public EditText locationET;

    @BindView(R.id.et_neednum)
    public EditText neednumEt;

    @BindView(R.id.et_group)
    public EditText groupET;

    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        //Intent intent=getIntent();
        //userId=String.valueOf(intent.getIntExtra("userId",0));
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        phoneNumber = sharedPreferences.getString("phoneNumber", "");
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        okHttpClient = new OkHttpClient();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.releasepost_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                if (titleET.getText().toString().equals("") || contentET.getText().toString().equals("")) {

                } else {
                    post();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //发起post请求
    private void post() {
        FormBody formBody = new FormBody.Builder()
                .add("title", titleET.getText().toString())
                .add("content", contentET.getText().toString())
                .add("dealline", deadlineET.getText().toString())
                .add("groupName", groupET.getText().toString())
                .add("population", populationET.getText().toString())
                .add("location", locationET.getText().toString())
                .add("needNum", neednumEt.getText().toString())
                .add("phoneNumber", phoneNumber)
                .add("type", (String) typeS.getSelectedItem())
                .build();
        Request request = new Request.Builder()
                .url("http://39.108.37.77:8080/pinyipin1/post/add")
                .post(formBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bodyData = response.body().string();
                int code = JsonUtil.getValue(bodyData, "code");
                if (code == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDialog("提示", "发布成功");
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDialog("提示", "发布失败");
                        }
                    });
                }
            }
        });
    }

    public void showDialog(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PostReleaseActivity.this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("好", null);
        builder.show();
    }

}
