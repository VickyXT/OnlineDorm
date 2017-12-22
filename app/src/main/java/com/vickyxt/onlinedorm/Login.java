package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private static final int SHOW_ERROR = 0;
    private static final int LOGIN_REQUEST = 1;
    private static final int UPDATE_USER_INFO = 2;

    private Button LoginBtn;
    private EditText UserName = null;
    private EditText PassWord = null;

    private TextView loginTitle;
    private ImageView userImg, passwordImg;
    private String username, password;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case LOGIN_REQUEST:
                    getUserDetail((String) msg.obj);
                    break;
                case UPDATE_USER_INFO:
                    Toast.makeText(Login.this, "登录成功", Toast.LENGTH_LONG).show();
                    if (saveUserInfo((HashMap<String, String>) msg.obj)) {
                        goToNextActivity();
                    }
                    break;
                case SHOW_ERROR:
                    Toast.makeText(Login.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    void initView(){
        setContentView(R.layout.login);
        loginTitle = (TextView)findViewById(R.id.login_title);
        userImg = (ImageView)findViewById(R.id.user_image);
        passwordImg = (ImageView)findViewById(R.id.password_image);
        UserName = (EditText)findViewById(R.id.username);
        PassWord = (EditText)findViewById(R.id.password);
        LoginBtn = (Button)findViewById(R.id.login_btn);
        LoginBtn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        initView();

        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE){
            Log.d("OnlineDorm","网络ok");
            Toast.makeText(Login.this,"网络ok！",Toast.LENGTH_LONG).show();
        }
        else{
            Log.d("OnlineDorm","网络挂了");
            Toast.makeText(Login.this,"网络挂了",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view){
        if (view.getId() == R.id.login_btn){
            username = UserName.getText().toString();
            password = PassWord.getText().toString();
            if (username.isEmpty() || username.equals("")) {
                Toast.makeText(Login.this, "请填写用户名", Toast.LENGTH_LONG).show();
            } else if (password.isEmpty() || password.equals("")) {
                Toast.makeText(Login.this, "请填写密码", Toast.LENGTH_LONG).show();
            } else {
                Login(username, password);
            }
        }
    }

    private void Login(final String username, String password) {
        final Method method = new Method();
        method.Login(username, password, new MyCallback() {
            @Override
            public void onSuccess(HashMap<String, String> data) {
                Message msg = new Message();
                msg.what = LOGIN_REQUEST;
                msg.obj = username;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(String error) {
                Message msg = new Message();
                msg.what = SHOW_ERROR;
                msg.obj = error;
                mHandler.sendMessage(msg);
            }
        });
    }

    private void getUserDetail(String stuid) {
        final Method method = new Method();
        method.getDetail(stuid, new MyCallback() {
            @Override
            public void onSuccess(HashMap<String, String> data) {
                if (data != null){
                    Log.d("data",data.toString());

                    Message msg = new Message();
                    msg.what = UPDATE_USER_INFO;
                    msg.obj = data;
                    mHandler.sendMessage(msg);
                }
            }

            @Override
            public void onError(String error) {
                Message msg = new Message();
                msg.what = SHOW_ERROR;
                msg.obj = error;
                mHandler.sendMessage(msg);
            }
        });
    }

    private boolean saveUserInfo(HashMap<String, String> hashMap) {
        String stuid = (String) hashMap.get("studentid");
        String name = (String) hashMap.get("name");
        String gender = (String) hashMap.get("gender");
        String vcode = (String) hashMap.get("vcode");
        String room = (String) hashMap.get("room");
        String building = (String) hashMap.get("building");
        String location = (String) hashMap.get("location");
        String grade = (String) hashMap.get("grade");

        SharedPreferences sharedPreferences = (SharedPreferences)getSharedPreferences("user_info",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("stuid", stuid);
        editor.putString("name", name);
        editor.putString("gender", gender);
        editor.putString("vcode", vcode);
        editor.putString("room", room);
        editor.putString("building", building);
        editor.putString("location", location);
        editor.putString("grade", grade);
        editor.commit();
        return true;
    }

    private void goToNextActivity() {
        Intent i = new Intent(this, SelectDorm.class);
        startActivityForResult(i, 1);
    }
}
