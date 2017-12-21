package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by VickyXT on 2017/12/21.
 */

public class Result extends Activity implements View.OnClickListener {

    private Button backBtn;

    void initView(){
        setContentView(R.layout.result);

        backBtn = (Button)findViewById(R.id.finish_btn);
        backBtn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.back_btn){
            Intent i = new Intent(this, UserInfo.class);
            startActivityForResult(i,1);
        }

    }
}