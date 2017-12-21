package com.vickyxt.onlinedorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by VickyXT on 2017/12/21.
 */

public class ManyChoose extends Activity implements View.OnClickListener {

    private ImageView backBtn;
    private Button submitBtn;
    private ArrayList<HashMap<String, EditText>> editArr;

    void initView(){
        setContentView(R.layout.many_choose);

        backBtn = (ImageView)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

        submitBtn = (Button)findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(this);

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

    private boolean checkEditText() {
        boolean hasEmpty = false;
        boolean isLastFull = true;
        for (int i = 0; i < editArr.size(); i++) {
            String id = editArr.get(i).get("id").getText().toString();
            String code = editArr.get(i).get("code").getText().toString();
            boolean isIdEmpty = id.equals("") || id.isEmpty();
            boolean isCodeEmpty = code.equals("") || code.isEmpty();

            // 单行 id未填写 code填写
            if (isIdEmpty && !isCodeEmpty) {
                hasEmpty = true;
                break;
            }

            // 前一行不满 本行有填写
            if (i > 0 && !isLastFull && !(isIdEmpty && isCodeEmpty)) {
                hasEmpty = true;
                break;
            }

            // 本行已满
            if (!isIdEmpty && !isCodeEmpty) {
                isLastFull = true;
            } else {
                isLastFull = false;
            }
        }
        return !hasEmpty;
    }
}
