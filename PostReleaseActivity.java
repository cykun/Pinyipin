package team.wucaipintu.pinyipin.ui.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import team.wucaipintu.pinyipin.R;

public class PostReleaseActivity extends AppCompatActivity {
    private ImageView backIV;
    private TextView releaseTV;
    private ImageView addImageIV;
    private EditText timeET;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private int Year;
    private int Month;
    private int Day;
    private int Hour;
    private int Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        initView();
        initListener();
    }

    public void initView() {
        backIV=(ImageView)findViewById(R.id.imageView_back);
        releaseTV=(TextView) findViewById(R.id.textView_release);
        addImageIV=(ImageView) findViewById(R.id.imageView_add);
        timeET=(EditText) findViewById(R.id.editText_time);
    }

    public void initListener(){
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                }
        });

        releaseTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(PostReleaseActivity.this);
                builder.setMessage("发布成功");
                builder.show();

                //延时关闭窗口,跳转主页
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        Intent intent = new Intent(PostReleaseActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                },1000);
            }
        });

        addImageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        timeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=View.inflate(getApplicationContext(),R.layout.dialog_timepicker,null);
                datePicker=(DatePicker)view.findViewById(R.id.datePicker);
                timePicker=(TimePicker)view.findViewById(R.id.timePicker);
                final Calendar c=Calendar.getInstance();

                Year=c.get(Calendar.YEAR);
                Month=c.get(Calendar.MONTH);
                Day=c.get(Calendar.DAY_OF_MONTH);
                datePicker.init(Year, Month, Day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker dpview, int year, int monthOfYear, int dayOfMonth) {
                    }
                });

                Hour=c.get(Calendar.HOUR_OF_DAY);
                Minute=c.get(Calendar.MINUTE);
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(Hour);
                timePicker.setCurrentMinute(Minute);
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker tpview, int hourOfDay, int minute) {
                    }
                });

                AlertDialog.Builder builder=new AlertDialog.Builder(PostReleaseActivity.this);
                builder.setView(view);
                builder.setTitle("设置时间");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Year=datePicker.getYear();
                        String smonth=datePicker.getMonth()+1<10?"0"+(datePicker.getMonth()+1):""+(datePicker.getMonth()+1);
                        String sday=datePicker.getDayOfMonth()<10?"0"+datePicker.getDayOfMonth():""+datePicker.getDayOfMonth();
                        String shour=timePicker.getCurrentHour()<10 ? "0"+timePicker.getCurrentHour():""+timePicker.getCurrentHour();
                        String sminute=timePicker.getCurrentMinute()<10 ? "0"+timePicker.getCurrentMinute():""+timePicker.getCurrentMinute();

                        timeET.setText("    "+Year+"/"+smonth+"/"+sday+" "+shour+":"+sminute);
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    public class postThread extends Thread{

        public postThread(){

        }
    }
}
