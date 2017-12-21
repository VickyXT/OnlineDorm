package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;


/**
 * Created by VickyXT on 2017/11/29.
 */

public class SelectDorm extends Activity implements View.OnClickListener {


    void initView(){
        setContentView(R.layout.select_dorm);
        PageNavigationView tab = (PageNavigationView) findViewById(R.id.tab);

        NavigationController navigationController = tab.material()
                .addItem(android.R.drawable.ic_menu_edit, "主页")
                .addItem(android.R.drawable.ic_menu_info_details, "我的")
                .build();

        navigationController.setSelect(0);

        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                //选中时触发
                if (index == 1) {
                    Intent intent = new Intent(SelectDorm.this, UserInfo.class);
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
        checkLogin();
    }

    @Override
    public void onClick(View view){

    }

    private void checkLogin(){
        SharedPreferences sharedPreferences = (SharedPreferences)getSharedPreferences("user_info",MODE_PRIVATE);
        String stuid = sharedPreferences.getString("stuid", "");
        if (stuid.equals("")) {
            Intent intent = new Intent(SelectDorm.this, Login.class);
            startActivity(intent);
        }
    }
}
