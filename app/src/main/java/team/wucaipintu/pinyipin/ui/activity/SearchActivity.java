package team.wucaipintu.pinyipin.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import team.wucaipintu.pinyipin.R;

public class SearchActivity extends AppCompatActivity {

    private ImageView backIV;
    private EditText searchET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backIV=(ImageView)findViewById(R.id.imageview_back);
        searchET=(EditText)findViewById(R.id.edittext_search);
        initListener();
    }

    public void initListener(){
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });
    }
}
