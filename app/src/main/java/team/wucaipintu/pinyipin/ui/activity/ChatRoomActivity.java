package team.wucaipintu.pinyipin.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.User;
import team.wucaipintu.pinyipin.bean.Message;

public class ChatRoomActivity extends AppCompatActivity implements
        MessageInput.InputListener, MessagesListAdapter.OnLoadMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.input)
    MessageInput messageInput;

    @BindView(R.id.messagesList)
    MessagesList messagesList;

    private MessagesListAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageInput.onFocusChange(toolbar,true);
                finish();
            }
        });

        adapter=new MessagesListAdapter<>("1",null);
        adapter.setLoadMoreListener(this);
        messagesList.setAdapter(adapter);
        messageInput.setInputListener(this);
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onSubmit(CharSequence input) {
        adapter.addToStart(new Message("1",input.toString(),new User("1","123","123"),new Date()), true);
        return true;
    }

}
