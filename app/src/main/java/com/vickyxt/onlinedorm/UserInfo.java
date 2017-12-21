package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * Created by nmchgx on 2017/12/20.
 */

public class UserInfo extends Activity implements View.OnClickListener {


    private static final int UPDATE_USER_INFO = 1;
    private static final int SHOW_ERROR = 0;
    private TextView studidTV,nameTV,genderTV,vcodeTV,roomTV,buildingTV;


    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case UPDATE_USER_INFO:
                    updateUserInfo((HashMap) msg.obj);
                    break;
                case SHOW_ERROR:
                    Toast.makeText(UserInfo.this, (String) msg.obj,Toast.LENGTH_LONG).show();
                default:
                    break;
            }
        }
    };
    void initView(){
        setContentView(R.layout.user_info);

        studidTV = (TextView) findViewById(R.id.student_code);
        nameTV = (TextView) findViewById(R.id.name);
        genderTV = (TextView) findViewById(R.id.user_sex);
        vcodeTV = (TextView) findViewById(R.id.student_code);
        roomTV = (TextView) findViewById(R.id.student_room);
        buildingTV = (TextView) findViewById(R.id.student_building);

        PageNavigationView tab = (PageNavigationView) findViewById(R.id.tab);

        NavigationController navigationController = tab.material()
                .addItem(android.R.drawable.ic_menu_edit, "主页")
                .addItem(android.R.drawable.ic_menu_info_details, "我的")
                .build();

        navigationController.setSelect(1);

        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                //选中时触发
                if (index == 0) {
                    Intent intent = new Intent(UserInfo.this, SelectDorm.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }

            @Override
            public void onRepeat(int index) {
                //重复选中时触发
            }
        });

    }

    void getUserDetail(String stuid) {
        final Method method = new Method();
        method.getDetail(stuid, new MyCallback() {
            @Override
            public void onSuccess(HashMap<String, String> data) {

                if (data != null){
                    Log.d("data",data.toString());

                    Message msg = new Message();
                    msg.what = UPDATE_USER_INFO;
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

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        initView();
        getUserDetail("1301210899");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFirst", false);
    }

    @Override
    public void onClick(View view){

    }

    public void updateUserInfo(HashMap hashMap){
        String stuid = (String) hashMap.get("studentid");
        String name = (String) hashMap.get("name");
        String gender = (String) hashMap.get("gender");
        String vcode = (String) hashMap.get("vcode");
        String room = (String) hashMap.get("room");
        String building = (String) hashMap.get("building");
        String location = (String) hashMap.get("location");
        String grade = (String) hashMap.get("grade");

        SharedPreferences sharedPreferences = (SharedPreferences)getSharedPreferences("user_info",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("gender", gender);
        editor.putString("vcode", vcode);
        editor.putString("room", room);
        editor.putString("building", building);
        editor.putString("location", location);
        editor.putString("grade", grade);
        editor.commit();

        studidTV.setText(stuid);
        nameTV.setText(name);
        genderTV.setText(gender);
        vcodeTV.setText(vcode);

        if ((String)hashMap.get("room") != null){
            roomTV.setText(room);
        }else {
            roomTV.setText("未选择");
        }

        if ((String)hashMap.get("building") != null){
            buildingTV.setText(building);
        }else {
            buildingTV.setText("未选择");
        }
    }

}