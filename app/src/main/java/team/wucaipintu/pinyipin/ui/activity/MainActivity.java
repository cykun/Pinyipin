package team.wucaipintu.pinyipin.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;

import java.util.ArrayList;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.ui.adapter.ViewPaperAdapter;
import team.wucaipintu.pinyipin.ui.fragment.ContactFragment;
import team.wucaipintu.pinyipin.ui.fragment.DialogFragment;
import team.wucaipintu.pinyipin.ui.fragment.HomeFragment;
import team.wucaipintu.pinyipin.ui.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationBar bottomNavigationBar;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentArrayList;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private TextBadgeItem textBadgeItem_message;
    private int userId=-1;
    private String nikeName;
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentArrayList = new ArrayList<>();
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.main_buttom);
        viewPager = (ViewPager) findViewById(R.id.view_paper);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        if(userId==-1){
            userId=intent.getIntExtra("userId",0);
        }
        if(nikeName==null){
            nikeName=intent.getStringExtra("nikeName");
        }
        initView();
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onResume(){
        super.onResume();
    }

    public void initView() {
        initViewPaper();
        initBottomNavigationBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            //case R.id.actio_add:break;
        }
        return super.onOptionsItemSelected(item);
    }

    /* 初始化viewpaper */
    public void initViewPaper() {
        viewPager.setOffscreenPageLimit(3);
        fragmentArrayList.add(HomeFragment.getInstance(userId,nikeName));
        fragmentArrayList.add(new DialogFragment());
        fragmentArrayList.add(ContactFragment.getInstance(userId));
        fragmentArrayList.add(new UserFragment());
        ViewPaperAdapter fragmentPagerAdapter = new ViewPaperAdapter(fragmentManager, fragmentArrayList);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /* 初始化底部导航栏 */
    public void initBottomNavigationBar() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setActiveColor("#2828FF")
                .setInActiveColor("#272727");
        textBadgeItem_message = new TextBadgeItem();
        textBadgeItem_message.setBorderWidth(2).setBackgroundColor("#FF0000")
                .setBorderColor("#FF0000")
                .setGravity(Gravity.RIGHT | Gravity.TOP)
                .setText("2").setTextColor("#F0F8FF")
                .setAnimationDuration(1000);
        BottomNavigationItem bottomNavigationItem_message = new BottomNavigationItem(R.drawable.message, "消息").setBadgeItem(textBadgeItem_message);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home, "拼贴"))
                .addItem(bottomNavigationItem_message)
                .addItem(new BottomNavigationItem(R.drawable.friend, "联系人"))
                .addItem(new BottomNavigationItem(R.drawable.i, "我"))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

}
