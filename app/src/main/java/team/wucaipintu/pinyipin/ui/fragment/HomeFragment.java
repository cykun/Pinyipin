package team.wucaipintu.pinyipin.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kekstudio.dachshundtablayout.DachshundTabLayout;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.ui.activity.PostReleaseActivity;

public class HomeFragment extends Fragment {
    private static String[] POSTS={"推荐","学习","旅游","拼车","其它"};
    private ViewPager viewPager;
    private DachshundTabLayout dachshundTabLayout;
    private FloatingActionButton button;
    private int userId;
    private String nikeName;

    public static HomeFragment getInstance(int userId,String nikeName){
        HomeFragment fragment=new HomeFragment();
        fragment.userId=userId;
        fragment.nikeName=nikeName;
        return fragment;
    }

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        viewPager=view.findViewById(R.id.post_viewpager);
        dachshundTabLayout=view.findViewById(R.id.tab_layout);
        button=view.findViewById(R.id.fab_add);
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PageFragment.getInstance(POSTS[position],userId,nikeName);
            }

            @Override
            public int getCount() {
                return POSTS.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return POSTS[position];
            }
        });

        dachshundTabLayout.setupWithViewPager(viewPager);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PostReleaseActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
    }
}
