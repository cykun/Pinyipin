package team.wucaipintu.pinyipin.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.ui.activity.CreditActivity;
import team.wucaipintu.pinyipin.ui.activity.MyPostActivity;
import team.wucaipintu.pinyipin.ui.activity.SettingActivity;
import team.wucaipintu.pinyipin.ui.activity.UserInfoActivity;

public class UserFragment extends Fragment {
    @BindView(R.id.linearlayout_user)
    LinearLayout userLL;

    @BindView(R.id.linearlayout_credit)
    LinearLayout creditLL;

    @BindView(R.id.linearlayout_post)
    LinearLayout postLL;

    @BindView(R.id.linearlayout_setting)
    LinearLayout settingLL;

    private Unbinder unBinder;

    private String phoneNUmber;
    private String nikeName;

    public static UserFragment getInstance(String phoneNumber, String nikeName){
        UserFragment fragment=new UserFragment();
        fragment.phoneNUmber=phoneNumber;
        fragment.nikeName=nikeName;
        return new UserFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public UserFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
    }
}
