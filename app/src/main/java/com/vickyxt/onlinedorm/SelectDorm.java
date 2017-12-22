package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;


/**
 * Created by VickyXT on 2017/11/29.
 */

public class SelectDorm extends Activity implements View.OnClickListener {

    private ImageView singleImg, manyImg;
    private RelativeLayout singleBtn, manyBtn;


    void initView(){
        setContentView(R.layout.select_dorm);

        singleImg = (ImageView) findViewById(R.id.single_img);
        manyImg = (ImageView) findViewById(R.id.many_img);

        singleBtn = (RelativeLayout) findViewById(R.id.single_choose);
        singleBtn.setOnClickListener(this);

        manyBtn = (RelativeLayout) findViewById(R.id.many_choose);
        manyBtn.setOnClickListener(this);

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
                    intent.putExtra("update", false);
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
        updateInfo();
    }

    @Override
    public void onClick(View view){

        if(view.getId() == R.id.single_choose){
            goToChooseActivity(SingleChoose.class);
        }

        if(view.getId() == R.id.many_choose){
            goToChooseActivity(ManyChoose.class);
        }

    }

    private void goToChooseActivity(Class nextActivity) {
        SharedPreferences sharedPreferences = (SharedPreferences)getSharedPreferences("user_info",MODE_PRIVATE);
        String room = sharedPreferences.getString("room", "");
        if (room.equals("")) {
            Intent intent = new Intent(this, nextActivity);
            startActivity(intent);
        } else {
            Toast.makeText(SelectDorm.this, "您已完成宿舍选择", Toast.LENGTH_LONG).show();
        }
    }

    private void checkLogin(){
        SharedPreferences sharedPreferences = (SharedPreferences)getSharedPreferences("user_info",MODE_PRIVATE);
        String stuid = sharedPreferences.getString("stuid", "");
        if (stuid.equals("")) {
            Intent intent = new Intent(SelectDorm.this, Login.class);
            startActivity(intent);
        }
    }

    public void updateInfo(){
        SharedPreferences sharedPreferences = (SharedPreferences)getSharedPreferences("user_info",MODE_PRIVATE);

        String gender = sharedPreferences.getString("gender", "");

        if (!gender.equals("女")){
            singleImg.setImageResource(R.drawable.boy);
            manyImg.setImageResource(R.drawable.boys);
        }else {
            singleImg.setImageResource(R.drawable.girl);
            manyImg.setImageResource(R.drawable.girls);
        }
    }
}
