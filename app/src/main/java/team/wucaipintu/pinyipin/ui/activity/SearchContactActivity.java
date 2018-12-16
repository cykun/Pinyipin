package team.wucaipintu.pinyipin.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.wucaipintu.pinyipin.R;

public class SearchContactActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageButton back;

    @BindView(R.id.input)
    EditText input;

    @BindView(R.id.prompt)
    LinearLayout prompt;

    @BindView(R.id.show_user)
    TextView show_user;

    @BindView(R.id.show_group)
    TextView show_group;

    @BindView(R.id.search_user)
    LinearLayout searchUser;

    @BindView(R.id.search_group)
    LinearLayout searchGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        searchUser.setOnClickListener(this);
        searchGroup.setOnClickListener(this);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (!content.equals("")) {
                    prompt.setVisibility(View.VISIBLE);
                    show_user.setText(content);
                    show_group.setText(content);
                } else {
                    prompt.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_user: {
                Intent intent = new Intent(SearchContactActivity.this, ContactAddActivity.class);
                intent.putExtra("usertId", "");//自己的手机号
                intent.putExtra("toId", "");//对方的手机号
                startActivity(intent);
            }
            break;
            case R.id.search_group: {
                Intent intent=new Intent(SearchContactActivity.this,GroupAddActivity.class);
                intent.putExtra("groupId","");
                intent.putExtra("phoneNumber","");
                startActivity(intent);
            }
            break;
        }
    }
}
