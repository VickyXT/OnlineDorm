package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * Created by nmchgx on 2017/12/20.
 */

public class UserInfo extends Activity implements View.OnClickListener {


    void initView(){
        setContentView(R.layout.user_info);
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

    void getUserDetail() {
        final Method method = new Method();
        String stuid = "1301210899";
        method.getDetail(stuid, new MyCallback() {
            @Override
            public void onSuccess(HashMap<String, String> data) {
                Log.d("data", data.toString());
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        initView();
        getUserDetail();
    }

    @Override
    public void onClick(View view){

    }

}