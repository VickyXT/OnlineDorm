package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;


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
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        initView();
    }

    @Override
    public void onClick(View view){

//        if (view.getId() == R.id.login_btn){
//            Intent i = new Intent(this, SelectDorm.class);
//            startActivityForResult(i,1);
//        }
    }


}
