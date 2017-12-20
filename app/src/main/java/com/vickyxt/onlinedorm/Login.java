package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vickyxt.util.NetUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by VickyXT on 2017/11/27.
 */

public class Login extends Activity implements View.OnClickListener {

    private static final int LOGIN_REQUEST = 1;
    private Button LoginBtn;
    private EditText UserName = null;
    private EditText PassWord = null;

    private TextView loginTitle;
    private ImageView userImg, passwordImg;
    private String username,password;

    private Handler lHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case LOGIN_REQUEST:
                    break;
                default:
                    break;
            }
        }
    };

    void initView(){
        loginTitle = (TextView)findViewById(R.id.login_title);
        userImg = (ImageView)findViewById(R.id.user_image);
        passwordImg = (ImageView)findViewById(R.id.password_image);
    }

    void Login(String stuid) {
        final Method method = new Method();
        method.Login(username,password);
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login);
        UserName = (EditText)findViewById(R.id.username);
        username = UserName.getText().toString();
        PassWord = (EditText)findViewById(R.id.password);
        password = PassWord.getText().toString();
        LoginBtn = (Button)findViewById(R.id.login_btn);
        LoginBtn.setOnClickListener(this);

        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE){
            Log.d("OnlineDorm","网络ok");
            Toast.makeText(Login.this,"网络ok！",Toast.LENGTH_LONG).show();
        }
        else{
            Log.d("OnlineDorm","网络挂了");
            Toast.makeText(Login.this,"网络挂了",Toast.LENGTH_LONG).show();
        }

        initView();
    }

    @Override
    public void onClick(View view){

        if (view.getId() == R.id.login_btn){
            Intent i = new Intent(this, SelectDorm.class);
            startActivityForResult(i,1);
        }
    }

}
