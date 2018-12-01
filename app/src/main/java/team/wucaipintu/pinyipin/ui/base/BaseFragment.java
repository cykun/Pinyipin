package team.wucaipintu.pinyipin.ui.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import team.wucaipintu.pinyipin.R;

public abstract class BaseFragment extends Fragment {
    private String TAG = BaseFragment.class.getSimpleName();

    private Boolean isViewInitiated;
    private Boolean isVisibleToUser;
    private Boolean isDataInitiated;
    private boolean isFirstLoad=true;
    private View rootView;

    public BaseFragment() {}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(rootView==null){
            return;
        }
        if(isFirstLoad && isVisibleToUser){
            isFirstLoad=false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(rootView==null){
            rootView=view;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        isViewInitiated=true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //初始化组件
    protected abstract void initView();
    //第一次加载数据
    protected abstract void initData();
}
