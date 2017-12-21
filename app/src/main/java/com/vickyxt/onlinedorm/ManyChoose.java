package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by VickyXT on 2017/12/21.
 */

public class ManyChoose extends Activity implements View.OnClickListener {

    private ImageView backBtn;
    private Button submitBtn;

    void initView(){
        setContentView(R.layout.many_choose);

        backBtn = (ImageView)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

        submitBtn = (Button)findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(this);
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

    }
}
