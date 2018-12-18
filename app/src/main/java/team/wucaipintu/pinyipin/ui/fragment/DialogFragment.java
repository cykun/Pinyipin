package team.wucaipintu.pinyipin.ui.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.Dialog;
import team.wucaipintu.pinyipin.ui.Listener.RecyclerItemClickListener;
import team.wucaipintu.pinyipin.ui.activity.ChatRoomActivity;
import team.wucaipintu.pinyipin.ui.adapter.MessageAdapter;

public class DialogFragment extends Fragment {

    private RecyclerView messageRV;
    private List<Dialog> dialogList;
    private boolean isFirstLoad = true;
    private String phoneNumber;
    private String nikeName;

    public static DialogFragment getInstance(String phoneNumber,String nikeName){
        DialogFragment fragment=new DialogFragment();
        fragment.phoneNumber=phoneNumber;
        fragment.nikeName=nikeName;
        return fragment;
    }

//    private DialogFragment() {
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageRV = view.findViewById(R.id.rv_message);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        messageRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        messageRV.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        messageRV.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), messageRV, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),ChatRoomActivity.class);
                intent.putExtra("phoneNumber",phoneNumber);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        if (isFirstLoad) {
            dialogList = new ArrayList<>();
        }
        //initData();
        messageRV.setAdapter(new MessageAdapter(dialogList));
    }

//    public void loadData() {
//        Dialog dialog=new Dialog();
//        dialog.setName("昵称");
//        dialog.setContent("你好");
//        dialog.setHeadId(R.drawable.defaulthead);
//        dialog.setDatatime("2018/11/30 21:32");
//        for (int i = 0; i < 4; i++) {
//            dialogList.add(dialog);
//        }
//    }
}
