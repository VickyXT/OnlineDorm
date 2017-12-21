package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by VickyXT on 2017/12/21.
 */

public class SingleChoose extends Activity implements View.OnClickListener {

    private static final int SHOW_ERROR = 0;
    private static final int GET_ROOM_INFO = 1;

    private ImageView backBtn, dot5,dot8,dot9, dot13, dot14;
    private Button submitBtn;
    private RelativeLayout b5, b8, b9, b13, b14;
    private String[] Room;





    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GET_ROOM_INFO:
                    UpdateRoom((HashMap<String, String>) msg.obj);
                    break;
                case SHOW_ERROR:
                    Toast.makeText(SingleChoose.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    void initView(){
        setContentView(R.layout.single_choose);

        backBtn = (ImageView)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

        submitBtn = (Button)findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(this);

        dot5 = (ImageView)findViewById(R.id.dot5);
        dot8 = (ImageView)findViewById(R.id.dot8);
        dot9 = (ImageView)findViewById(R.id.dot9);
        dot13 = (ImageView)findViewById(R.id.dot13);
        dot14 = (ImageView)findViewById(R.id.dot14);

        b5 = (RelativeLayout)findViewById(R.id.b5);
        b5.setOnClickListener(this);

        b8 = (RelativeLayout)findViewById(R.id.b8);
        b8.setOnClickListener(this);

        b9 = (RelativeLayout)findViewById(R.id.b9);
        b9.setOnClickListener(this);

        b13 = (RelativeLayout)findViewById(R.id.b13);
        b13.setOnClickListener(this);

        b14 = (RelativeLayout)findViewById(R.id.b14);
        b14.setOnClickListener(this);

        SharedPreferences sharedPreferences = (SharedPreferences)getSharedPreferences("user_info",MODE_PRIVATE);
        String gender = sharedPreferences.getString("gender", "");
        getRoom(gender);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.back_btn){
            Intent i = new Intent(this, SelectDorm.class);
            startActivityForResult(i,1);
        }

        if(v.getId() == R.id.submit_btn){
            Intent i = new Intent(this, Result.class);
            startActivityForResult(i,1);
        }

        if(v.getId() == R.id.b5){
            ;
        }

    }

    private void getRoom(String gender) {
        final Method method = new Method();
        method.getRoom(gender, new MyCallback() {
            @Override
            public void onSuccess(HashMap<String, String> data) {
                if (data != null){
                    Log.d("data",data.toString());

                    Message msg = new Message();
                    msg.what = GET_ROOM_INFO;
                    msg.obj = data;
                    mHandler.sendMessage(msg);
                }
            }

            @Override
            public void onError(String error) {
                Message msg = new Message();
                msg.what = SHOW_ERROR;
                msg.obj = error;
                mHandler.sendMessage(msg);
            }
        });
    }

    private void UpdateRoom(HashMap<String,String> hashMap){


        String room5 = (String) hashMap.get("5");
        String room8 = (String) hashMap.get("8");
        String room9 = (String) hashMap.get("9");
        String room13 = (String) hashMap.get("13");
        String room14 = (String) hashMap.get("14");


    }

}
