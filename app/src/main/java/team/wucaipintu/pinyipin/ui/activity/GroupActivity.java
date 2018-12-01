package team.wucaipintu.pinyipin.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.Contact;
import team.wucaipintu.pinyipin.bean.Group;
import team.wucaipintu.pinyipin.ui.Listener.RecyclerItemClickListener;
import team.wucaipintu.pinyipin.ui.adapter.ContactAdapter;
import team.wucaipintu.pinyipin.ui.adapter.GroupAdapter;

public class GroupActivity extends AppCompatActivity {

    private RecyclerView groupRV;
    private ArrayList<Group> groups;
    private Toolbar toolbar;
    private int userId;
    private GroupAdapter adapter;
    private boolean isFirstLoad=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Intent intent=getIntent();
        userId=intent.getIntExtra("userId",0);
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

        if(isFirstLoad){
            groups=new ArrayList<>();
            loadData();
            isFirstLoad=false;
        }
        //initData();
        groupRV=(RecyclerView)findViewById(R.id.rv_group);
        groupRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        groupRV.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter=new GroupAdapter(groups);
        groupRV.setAdapter(adapter);
        groupRV.addOnItemTouchListener(new RecyclerItemClickListener(this, groupRV, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(GroupActivity.this,ChatRoomActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    public void loadData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://39.108.37.77:8080/pinyipin/group/list");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("accept", "*/*");
                    httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("user-agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                    String param = "userId="+userId;
                    httpURLConnection.connect();
                    PrintWriter writer = new PrintWriter(httpURLConnection.getOutputStream());
                    writer.print(param);
                    writer.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String line,data="";
                    while ((line=reader.readLine())!=null){
                        data+=line;
                    }
                    writer.close();
                    reader.close();
                    Gson gson=new Gson();
                    Type groupListType = new TypeToken<ArrayList<Group>>() {
                    }.getType();
                    ArrayList<Group> groupArrayList=gson.fromJson(data,groupListType);
                    for(Group group:groupArrayList){
                        groups.add(group);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }catch (IOException e){ }
            }
        }).start();
    }

//    public void initData(){
//        contacts=new ArrayList<>();
//        Contact contact=new Contact();
//        contact.nikeName="群主";
//        for(int i=0;i<12;i++){
//            contacts.add(contact);
//        }
//    }
}
