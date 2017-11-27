package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.vickyxt.util.NetUtil;

/**
 * Created by VickyXT on 2017/11/27.
 */

public class Login extends Activity {

    private ImageView LoginBtn;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login);

        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE){
            Log.d("myWeather","网络ok");
            Toast.makeText(Login.this,"网络ok！",Toast.LENGTH_LONG).show();
        }
        else{
            Log.d("myWeather","网络挂了");
            Toast.makeText(Login.this,"网络挂了",Toast.LENGTH_LONG).show();
        }

    }
}
