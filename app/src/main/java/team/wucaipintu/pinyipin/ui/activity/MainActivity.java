package team.wucaipintu.pinyipin.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.ui.adapter.ViewPaperAdapter;
import team.wucaipintu.pinyipin.ui.fragment.ContactFragment;
import team.wucaipintu.pinyipin.ui.fragment.DialogFragment;
import team.wucaipintu.pinyipin.ui.fragment.HomeFragment;
import team.wucaipintu.pinyipin.ui.fragment.UserFragment;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_buttom)
    BottomNavigationBar bottomNavigationBar;

    @BindView(R.id.view_paper)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.button)
    Button button;

    private ArrayList<Fragment> fragmentArrayList;
    private FragmentManager fragmentManager;

    private TextBadgeItem textBadgeItem_message;
    private int userId=-1;
    private String nikeName;
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }
    private Fragment mConversationList;
    private Fragment mConversationFragment=null;
    protected static  final String TAG="MainActivity";
    private  static final String token1="Goxa1taM647d0pX1ufRT/m1hqL6r7qk/o4G0nEAeWPVZRaKAmBq0S37aB2d+calUXPTte5tU2me8Oj6RC3sKrw==";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connectRongServer(token1);
        ButterKnife.bind(this);
        RongIM.init(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(MainActivity.this, "333", "菠萝三号");
            }
        });
        fragmentManager = getSupportFragmentManager();
        fragmentArrayList = new ArrayList<>();
        //bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.main_buttom);
       // viewPager = (ViewPager) findViewById(R.id.view_paper);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);

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

    private void connectRongServer(String token){
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e(TAG,"token is error , please check token and appkey");
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String s) {

                    //mUser1.setText("用户1连接服务器成功");
                    Log.e(TAG,"onSuccess :"+s);
                    Toast.makeText(MainActivity.this, "connect server success 菠萝一号", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG,"connect failure errorCode is :"+errorCode.getValue());
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
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
                Intent intent0 = new Intent(MainActivity.this, SearchContactActivity.class);
                startActivity(intent0);
                break;
            case R.id.action_add_friend:
                Intent intent1=new Intent(MainActivity.this,SearchContactActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_add_group:
                Intent intent2=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private  Fragment initConversationList(){
        if(mConversationFragment==null){
            ConversationListFragment listFragment=new ConversationListFragment();
            Uri uri=Uri.parse("rong://"+getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(),"false")//设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(),"false")
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(),"false")//设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(),"false")//设置私聊会话是否聚合显示
                    .build();
            listFragment.setUri(uri);
            return listFragment;
        }else{
            return mConversationFragment;
        }
    };

    /* 初始化viewpaper */
    public void initViewPaper() {
        mConversationList=initConversationList();//获取会话列表的对象

        viewPager.setOffscreenPageLimit(3);
        fragmentArrayList.add(HomeFragment.getInstance(userId,nikeName));
        //fragmentArrayList.add(new DialogFragment());//会话列表
        fragmentArrayList.add(mConversationList);
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
