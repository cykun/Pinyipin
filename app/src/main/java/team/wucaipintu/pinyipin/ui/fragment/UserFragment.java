package team.wucaipintu.pinyipin.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.ui.activity.CreditActivity;
import team.wucaipintu.pinyipin.ui.activity.MyPostActivity;
import team.wucaipintu.pinyipin.ui.activity.SettingActivity;
import team.wucaipintu.pinyipin.ui.activity.UserInfoActivity;

public class UserFragment extends Fragment {
    private LinearLayout userLL;
    private LinearLayout creditLL;
    private LinearLayout postLL;
    private LinearLayout settingLL;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public UserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userLL = view.findViewById(R.id.linearlayout_user);
        creditLL = view.findViewById(R.id.linearlayout_credit);
        postLL = view.findViewById(R.id.linearlayout_post);
        settingLL = view.findViewById(R.id.linearlayout_setting);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
            }
        });

        settingLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        creditLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),CreditActivity.class);
                startActivity(intent);
            }
        });

        postLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MyPostActivity.class);
                startActivity(intent);
            }
        });
    }
}
