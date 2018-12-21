package team.wucaipintu.pinyipin.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.Comment;
import team.wucaipintu.pinyipin.bean.PostDetail;
import team.wucaipintu.pinyipin.ui.adapter.CommentAdapter;

public class PostDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_title)
    TextView titleTV;

    @BindView(R.id.content)
    TextView contentTV;

    @BindView(R.id.deadline)
    TextView deallineTV;

    @BindView(R.id.nikename)
    TextView nikeNameTV;

    @BindView(R.id.releasetime)
    TextView releaseTimeTV;

    @BindView(R.id.population)
    TextView populationTV;

    @BindView(R.id.neednum)
    TextView needTV;

    @BindView(R.id.nownum)
    TextView nowTV;

    @BindView(R.id.bt_add)
    Button addBT;

    @BindView(R.id.ib_like)
    ImageButton likeIB;

    @BindView(R.id.tv_like_num)
    TextView likeNumTV;

    @BindView(R.id.bt_send)
    Button sendBT;

    @BindView(R.id.tv_comment_num)
    TextView commentNumTV;

    @BindView(R.id.et_comment)
    EditText msgET;

    @BindView(R.id.rv_comment)
    RecyclerView recyclerView;

    private int postId;
    private String phoneNumber;
    private ArrayList<Comment> comments;
    private PostDetail postDetail;
    private CommentAdapter adapter;
    private boolean isLikeed=false;

    private String nikeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Window window = getWindow();
            View decorView = window.getDecorView();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); //可有可无
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        phoneNumber=preferences.getString("phoneNumber","");
        nikeName=preferences.getString("nikeName","123");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        likeIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLikeed){
                    likeIB.setImageResource(R.drawable.liked);
                    isLikeed=true;
                } else{
                    likeIB.setImageResource(R.drawable.like);
                    isLikeed=false;
                }
            }
        });
        msgET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content=s.toString();
                if(content.equals("")){
                    sendBT.setClickable(false);
                }else {
                    sendBT.setClickable(true);
                }
            }
        });
        postDetail = new PostDetail();
        comments=new ArrayList<>();
        addBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroupRequest();
            }
        });

        sendBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=msgET.getText().toString();
                sendComment(data);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                // 直接禁止垂直滑动
                return false;
            }
        });
        adapter=new CommentAdapter(comments);
        recyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", 1);
//        phoneNumber=intent.getStringExtra("phoneNumber");
//        nikeName=intent.getStringExtra("nikeName");
        getPostData();
    }

    public void addGroupRequest(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://39.108.37.77:8080/pinyipin1/group/add");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("accept", "*/*");
                    httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("user-agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                    String param = "phoneNumber="+phoneNumber+"&groupId="+postDetail.groupId;
                    httpURLConnection.connect();
                    PrintWriter writer = new PrintWriter(httpURLConnection.getOutputStream());
                    writer.print(param);
                    writer.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String data = reader.readLine();
                    writer.close();
                    reader.close();
                    int code=new JsonParser().parse(data).getAsJsonObject().get("code").getAsInt();
                    if(code==0){
                        AlertDialog.Builder builder=new AlertDialog.Builder(PostDetailActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("你已经加入了");
                        builder.setPositiveButton("好", null);
                        Looper.prepare();
                        builder.show();
                        Looper.loop();
                    }else {
                        AlertDialog.Builder builder=new AlertDialog.Builder(PostDetailActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("加入成功");
                        builder.setPositiveButton("好", null);
                        Looper.prepare();
                        builder.show();
                        Looper.loop();
                    }
                } catch (IOException e) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(PostDetailActivity.this);
                    builder.setTitle("错误");
                    builder.setMessage("未知错误");
                    builder.setPositiveButton("好", null);
                    Looper.prepare();
                    builder.show();
                    Looper.loop();
                }
            }
        }).start();
    }

    public void getPostData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://39.108.37.77:8080/pinyipin1/post/detail");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("accept", "*/*");
                    httpURLConnection.setRequestProperty("user-agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                    httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                    String param = "postId="+postId;
                    httpURLConnection.connect();
                    PrintWriter writer = new PrintWriter(httpURLConnection.getOutputStream());
                    writer.print(param);
                    writer.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String line, data = "";
                    while ((line = reader.readLine()) != null) {
                        data += line;
                    }
                    Gson gson = new Gson();
                    postDetail = gson.fromJson(data, PostDetail.class);
                    writer.close();
                    reader.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            titleTV.setText(postDetail.title);
                            contentTV.setText(postDetail.content);
                            deallineTV.setText(postDetail.deadline);
                            releaseTimeTV.setText(postDetail.releaseTime);
                            nikeNameTV.setText(postDetail.nikeName);
                            needTV.setText(String.valueOf(postDetail.needNum));
                            nowTV.setText("0");
                            populationTV.setText(postDetail.population);
                            for(Comment comment:postDetail.comments){
                                comments.add(comment);
                            }
                            commentNumTV.setText(String.valueOf(comments.size()));
                            adapter.notifyDataSetChanged();
                            //Toast.makeText(PostDetailActivity.this,""+userId+":"+postDetail.groupId,Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (IOException e) {
                }
            }
        }).start();
    }

    //发送评论
    public void sendComment(final String content){
        msgET.setText("");
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        Comment comment=new Comment();
        comment.content=content;
        comment.nikeName=nikeName;
        comment.releaseTime=format.format(date);
        comments.add(comment);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://39.108.37.77:8080/pinyipin1/comment/add");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("accept", "*/*");
                    httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("user-agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                    String param = "phoneNumber="+phoneNumber+"&postId="+postId+"&content="+content;
                    httpURLConnection.connect();
                    PrintWriter writer = new PrintWriter(httpURLConnection.getOutputStream());
                    writer.print(param);
                    writer.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String data = reader.readLine();

                    writer.close();
                    reader.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            commentNumTV.setText(String.valueOf(comments.size()));
                        }
                    });
                } catch (IOException e) {}
            }
        }).start();
    }
}
