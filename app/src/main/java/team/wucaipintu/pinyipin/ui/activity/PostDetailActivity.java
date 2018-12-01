package team.wucaipintu.pinyipin.ui.activity;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.Comment;
import team.wucaipintu.pinyipin.bean.PostDetail;
import team.wucaipintu.pinyipin.ui.adapter.CommentAdapter;

public class PostDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private int postId,userId;
    private TextView titleTV;
    private TextView contentTV;
    private TextView deallineTV;
    private TextView nikeNameTV;
    private TextView releaseTimeTV;
    private TextView populationTV;
    private TextView needTV;
    private TextView nowTV;
    private Button addBT,sendBT;
    private TextView commentNumTV;
    private ArrayList<Comment> comments;
    private PostDetail postDetail;
    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private EditText msgET;
    private String nikeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        postDetail = new PostDetail();
        comments=new ArrayList<>();

        titleTV = (TextView) findViewById(R.id.tv_title);
        deallineTV = (TextView) findViewById(R.id.deadline);
        nikeNameTV = (TextView) findViewById(R.id.nikename);
        releaseTimeTV = (TextView) findViewById(R.id.releasetime);
        contentTV = (TextView) findViewById(R.id.content);
        populationTV = (TextView) findViewById(R.id.population);
        needTV = (TextView) findViewById(R.id.neednum);
        nowTV = (TextView) findViewById(R.id.nownum);
        addBT = (Button) findViewById(R.id.bt_add);
        addBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroupRequest();
            }
        });
        msgET=(EditText)findViewById(R.id.et_comment);
        sendBT=(Button)findViewById(R.id.bt_send);
        sendBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=msgET.getText().toString();
                sendComment(data);
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.rv_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter=new CommentAdapter(comments);
        recyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", 2);
        userId=intent.getIntExtra("userId",0);
        nikeName=intent.getStringExtra("nikeName");
        getPostData();
    }

//    public boolean requestAdd(){
//        //FormBody formBody=new FormBody.Builder().add()
//
//        return true;
//    }

    public void addGroupRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://39.108.37.77:8080/pinyipin/group/add");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("accept", "*/*");
                    httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("user-agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                    String param = "userId="+userId+"&groupId="+postDetail.groupId;
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
                    URL url = new URL("http://39.108.37.77:8080/pinyipin/post/detail");
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
                            releaseTimeTV.setText(postDetail.datatime);
                            nikeNameTV.setText(postDetail.nikeName);
                            needTV.setText(String.valueOf(postDetail.needNum));
                            nowTV.setText("0");
                            populationTV.setText(postDetail.population);
                            for(Comment comment:postDetail.comments){
                                comments.add(comment);
                            }
                            adapter.notifyDataSetChanged();
                            Toast.makeText(PostDetailActivity.this,""+userId+":"+postDetail.groupId,Toast.LENGTH_LONG).show();
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
        comment.datatime=format.format(date);
        comments.add(comment);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://39.108.37.77:8080/pinyipin/comment/add");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("accept", "*/*");
                    httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("user-agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                    String param = "userId="+userId+"&postId="+postId+"&content="+content;
                    httpURLConnection.connect();
                    PrintWriter writer = new PrintWriter(httpURLConnection.getOutputStream());
                    writer.print(param);
                    writer.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String data = reader.readLine();

                    //System.out.println(data);
                    writer.close();
                    reader.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {}
            }
        }).start();
    }
}
