package team.wucaipintu.pinyipin.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.Contact;
import team.wucaipintu.pinyipin.ui.Listener.RecyclerItemClickListener;
import team.wucaipintu.pinyipin.ui.activity.ChatRoomActivity;
import team.wucaipintu.pinyipin.ui.activity.GroupActivity;
import team.wucaipintu.pinyipin.ui.activity.FriendRequestActivity;
import team.wucaipintu.pinyipin.ui.adapter.ContactAdapter;

public class ContactFragment extends Fragment {

    private RecyclerView contactRV;
    private ArrayList<Contact> contacts;
    private LinearLayout groupLL;
    private LinearLayout newfriendLL;
    private boolean isFirstLoad = true;
    private int userId;
    private ContactAdapter contactAdapter;

    public static ContactFragment getInstance(int userId) {
        ContactFragment fragment = new ContactFragment();
        fragment.userId = userId;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactRV = view.findViewById(R.id.rv_contact);
        groupLL = view.findViewById(R.id.ll_group);
        newfriendLL = view.findViewById(R.id.ll_newfriend);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contactRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        contactRV.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        contactRV.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), contactRV, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),ChatRoomActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        //initData();
        if (isFirstLoad) {
            contacts = new ArrayList<>();
            loadData();
            isFirstLoad = false;
        }

        contactAdapter = new ContactAdapter(contacts);
        contactRV.setAdapter(contactAdapter);

        groupLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GroupActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        newfriendLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendRequestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://39.108.37.77:8080/pinyipin/friend/list");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("accept", "*/*");
                    httpURLConnection.setRequestProperty("user-agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                    httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                    String param = "userId=" + userId;
                    httpURLConnection.connect();
                    PrintWriter writer = new PrintWriter(httpURLConnection.getOutputStream());
                    writer.print(param);
                    writer.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String data = "";
                    String line;
                    while ((line = reader.readLine()) != null) {
                        data += line;
                    }
                    writer.close();
                    reader.close();
                    Gson gson = new Gson();
                    Type contactListType = new TypeToken<ArrayList<Contact>>() {
                    }.getType();
                    ArrayList<Contact> contactArrayList = gson.fromJson(data, contactListType);
                    for (Contact contact : contactArrayList) {
                        contacts.add(contact);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            contactAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                }
            }
        }).start();
    }

//    public void initData(){
//        contacts=new ArrayList<>();
//        Contact contact=new Contact(R.drawable.name,"郑西坤");
//        for(int i=0;i<12;i++){
//            contacts.add(contact);
//        }
//    }
}
