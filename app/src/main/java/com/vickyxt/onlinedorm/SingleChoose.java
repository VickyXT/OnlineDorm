package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by VickyXT on 2017/12/21.
 */

public class SingleChoose extends Activity implements View.OnClickListener {

    private ImageView backBtn;

    void initView(){
        setContentView(R.layout.single_choose);

        backBtn = (ImageView)findViewById(R.id.back_btn);
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
            Intent i = new Intent(this, SelectDorm.class);
            startActivityForResult(i,1);
        }

    }
}
