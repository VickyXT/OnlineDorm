package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by VickyXT on 2017/12/21.
 */

public class ManyChoose extends Activity implements View.OnClickListener {

    private static final int SHOW_ERROR = 0;
    private static final int GET_ROOM_INFO = 1;
    private static final int GO_RESULT = 2;

    private ImageView backBtn;
    private Button submitBtn;
    private ArrayList<HashMap<String, EditText>> editArr;
    private String buildingNo;
    private TextView numTv;
    private int num = 1;
    private String keyList[] = {"5", "8", "9", "13", "14"};
    private HashMap<String, HashMap<String, Object>> buildingArr;
    private int[] hashCorrectArr = new int[]{0, 32, 48, 56, 60, 62, 63};

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GET_ROOM_INFO:
                    updateRoom((HashMap<String, String>) msg.obj);
                    break;
                case SHOW_ERROR:
                    Toast.makeText(ManyChoose.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case GO_RESULT:
                    goToResultActivity((String) msg.obj);
                default:
                    break;
            }
        }
    };

    void initView(){
        setContentView(R.layout.many_choose);

        backBtn = (ImageView)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

        submitBtn = (Button)findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(this);

        numTv = (TextView) findViewById(R.id.roommate_num);

        editArr = new ArrayList<HashMap<String, EditText>>();
        for (int i = 0; i < 3; i++) {
            HashMap<String, EditText> item = new HashMap<String, EditText>();
            editArr.add(item);
        }
        editArr.get(0).put("id", (EditText) findViewById(R.id.friend1_id));
        editArr.get(0).put("code", (EditText) findViewById(R.id.friend1_code));
        editArr.get(1).put("id", (EditText) findViewById(R.id.friend2_id));
        editArr.get(1).put("code", (EditText) findViewById(R.id.friend2_code));
        editArr.get(2).put("id", (EditText) findViewById(R.id.friend3_id));
        editArr.get(2).put("code", (EditText) findViewById(R.id.friend3_code));
        for (HashMap<String, EditText> item: editArr) {
            for (EditText editText: item.values()) {
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        //当actionId == XX_SEND 或者 XX_DONE时都触发
                        //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                        //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                        if (i == EditorInfo.IME_ACTION_SEND
                                || i == EditorInfo.IME_ACTION_DONE
                                || i == EditorInfo.IME_ACTION_NEXT
                                || (keyEvent != null && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode() && KeyEvent.ACTION_DOWN == keyEvent.getAction())) {
                            //处理事件
                            if (!checkEditText()) {
                                Toast.makeText(ManyChoose.this, "请填写完整", Toast.LENGTH_LONG).show();
                            }
                        }
                        return false;
                    }
                });
            }
        }

        // building
        buildingArr = new HashMap<String, HashMap<String, Object>>();
        for (int i = 0; i < 5; i++) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            buildingArr.put(keyList[i], item);
        }

        // btn
        RelativeLayout building0 = (RelativeLayout)findViewById(R.id.b5);
        building0.setOnClickListener(this);
        buildingArr.get(keyList[0]).put("btn", building0);
        RelativeLayout building1 = (RelativeLayout)findViewById(R.id.b8);
        building1.setOnClickListener(this);
        buildingArr.get(keyList[1]).put("btn", building1);
        RelativeLayout building2 = (RelativeLayout)findViewById(R.id.b9);
        building2.setOnClickListener(this);
        buildingArr.get(keyList[2]).put("btn", building2);
        RelativeLayout building3 = (RelativeLayout)findViewById(R.id.b13);
        building3.setOnClickListener(this);
        buildingArr.get(keyList[3]).put("btn", building3);
        RelativeLayout building4 = (RelativeLayout)findViewById(R.id.b14);
        building4.setOnClickListener(this);
        buildingArr.get(keyList[4]).put("btn", building4);

        // dot
        buildingArr.get(keyList[0]).put("dot", (ImageView)findViewById(R.id.dot5));
        buildingArr.get(keyList[1]).put("dot", (ImageView)findViewById(R.id.dot8));
        buildingArr.get(keyList[2]).put("dot", (ImageView)findViewById(R.id.dot9));
        buildingArr.get(keyList[3]).put("dot", (ImageView)findViewById(R.id.dot13));
        buildingArr.get(keyList[4]).put("dot", (ImageView)findViewById(R.id.dot14));

        // room
        buildingArr.get(keyList[0]).put("room", (TextView)findViewById(R.id.room5));
        buildingArr.get(keyList[1]).put("room", (TextView)findViewById(R.id.room8));
        buildingArr.get(keyList[2]).put("room", (TextView)findViewById(R.id.room9));
        buildingArr.get(keyList[3]).put("room", (TextView)findViewById(R.id.room13));
        buildingArr.get(keyList[4]).put("room", (TextView)findViewById(R.id.room14));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        updateNumText();
        SharedPreferences sharedPreferences = (SharedPreferences)getSharedPreferences("user_info",MODE_PRIVATE);
        String gender = sharedPreferences.getString("gender", "");
        getRoom(gender);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                Intent iSelectDorm = new Intent(this, SelectDorm.class);
                startActivityForResult(iSelectDorm, 1);
                break;
            case R.id.submit_btn:
                if (checkAll()) {
                    SelectRoom();
                } else {
                    Toast.makeText(ManyChoose.this, "请填写完整", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.b5:
                chooseBuilding("5");
                break;
            case R.id.b8:
                chooseBuilding("8");
                break;
            case R.id.b9:
                chooseBuilding("9");
                break;
            case R.id.b13:
                chooseBuilding("13");
                break;
            case R.id.b14:
                chooseBuilding("14");
                break;
            default:
                break;
        }
    }

    private void getRoom(String gender) {
        final Method method = new Method();
        method.getRoom(gender, new MyCallback() {
            @Override
            public void onSuccess(HashMap<String, String> data) {
                if (data != null){
                    Log.d("data",data.toString());

                    Message msg = new Message();
                    msg.what = GET_ROOM_INFO;
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

    //
    private void SelectRoom() {
        SharedPreferences sharedPreferences = (SharedPreferences)getSharedPreferences("user_info",MODE_PRIVATE);
        String stuid = sharedPreferences.getString("stuid", "");
        String stu1id = editArr.get(0).get("id").getText().toString();
        String v1code = editArr.get(0).get("code").getText().toString();
        String stu2id = editArr.get(1).get("id").getText().toString();
        String v2code = editArr.get(1).get("code").getText().toString();
        String stu3id = editArr.get(2).get("id").getText().toString();
        String v3code = editArr.get(2).get("code").getText().toString();
        final Method method = new Method();
        method.SelectRoom(buildingNo, "1", stuid, stu1id, v1code, stu2id, v2code, stu3id, v3code, new MyCallback() {
            @Override
            public void onSuccess(HashMap<String, String> data) {
                Message msg = new Message();
                msg.what = GO_RESULT;
                msg.obj = data.get("result");
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

    private void updateRoom(HashMap<String,String> hashMap){
        for (String key: buildingArr.keySet()) {
            String num = hashMap.get(key);
            TextView room = (TextView) buildingArr.get(key).get("room");
            ImageView dot = (ImageView) buildingArr.get(key).get("dot");
            RelativeLayout btn = (RelativeLayout) buildingArr.get(key).get("btn");
            if (Integer.parseInt(num) > 0) {
                dot.setVisibility(View.VISIBLE);
                room.setText("余量" + num);
                btn.setClickable(true);
            } else {
                dot.setVisibility(View.INVISIBLE);
                room.setText("无余量");
                btn.setClickable(false);
            }
        }
    }

    private void chooseBuilding(String key) {
        buildingNo = key;
        for (String itemKey: buildingArr.keySet()) {
            ImageView dot = (ImageView) buildingArr.get(itemKey).get("dot");
            if (itemKey.equals(key)) {
                dot.setImageResource(R.drawable.dot_active);
            } else {
                dot.setImageResource(R.drawable.dot);
            }
        }
    }

    private void goToResultActivity(String result) {
        if (result.equals("success")){
            Intent intent = new Intent(ManyChoose.this, Result.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(ManyChoose.this, ResultFail.class);
            startActivity(intent);
        }

    }

    private boolean checkAll() {
        int value = translateEditText();
        int result = getHashPosition(value);
        if (result > 0 && result % 2 == 0) {
            num = (result + 1) / 2 + 1;
            updateNumText();
            return true;
        } else {
            return false;
        }
    }

    private boolean checkEditText() {
        int value = translateEditText();
        int result = getHashPosition(value);
        if (result >= 0) {
            if (result % 2 == 0) {
                num = (result + 1) / 2 + 1;
                updateNumText();
            }
            return true;
        } else {
            return false;
        }
    }

    private void updateNumText() {
        numTv.setText("总计" + num + "人");
    }

    private int translateEditText() {
        String result = "";
        for (int i = 0; i < editArr.size(); i++) {
            String id = editArr.get(i).get("id").getText().toString();
            String code = editArr.get(i).get("code").getText().toString();
            if (id.equals("") || id.isEmpty()) {
                result += "0";
            } else {
                result += "1";
            }
            if (code.equals("") || code.isEmpty()) {
                result += "0";
            } else {
                result += "1";
            }
        }
        return Integer.valueOf(result, 2);
    }

    private int getHashPosition(int value) {
        return Arrays.binarySearch(hashCorrectArr, value);
    }
}
