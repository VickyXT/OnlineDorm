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

    private TextView studidTV,nameTV,genderTV,vcodeTV,roomTV,buildingTV;

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

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        initView();
        updateUserInfo();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFirst", false);
    }

    @Override
    public void onClick(View view){

    }

    public void updateUserInfo(){
        SharedPreferences sharedPreferences = (SharedPreferences)getSharedPreferences("user_info",MODE_PRIVATE);
        String stuid = sharedPreferences.getString("stuid", "");
        String name = sharedPreferences.getString("name", "");
        String gender = sharedPreferences.getString("gender", "");
        String vcode = sharedPreferences.getString("vcode", "");
        String room = sharedPreferences.getString("room", "");
        String building = sharedPreferences.getString("building", "");
        String location = sharedPreferences.getString("location", "");
        String grade = sharedPreferences.getString("grade", "");

        studidTV.setText(stuid);
        nameTV.setText(name);
        genderTV.setText(gender);
        vcodeTV.setText(vcode);

        if (!room.equals("")){
            roomTV.setText(room);
        }else {
            roomTV.setText("未选择");
        }

        if (!building.equals("")){
            buildingTV.setText(building);
        }else {
            buildingTV.setText("未选择");
        }
    }

}