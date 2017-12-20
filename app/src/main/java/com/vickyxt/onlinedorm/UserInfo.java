package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

    void getUserDetail() {
        final Method method = new Method();
        String stuid = "1301210899";
        method.getDetail(stuid, new MyCallback() {
            @Override
            public void onSuccess(HashMap<String, String> data) {
                Log.d("data", data.toString());
                updateUserInfo(data);
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

    public void updateUserInfo(HashMap hashMap){
        studidTV.setText((String) hashMap.get("studentid"));
        nameTV.setText((String) hashMap.get("name"));
        genderTV.setText((String) hashMap.get("gender"));
        vcodeTV.setText((String) hashMap.get("vcode"));
        roomTV.setText((String) hashMap.get("room"));
        buildingTV.setText((String) hashMap.get("building"));
    }

}