package com.vickyxt.onlinedorm;

import android.util.Log;
import android.view.View;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by VickyXT on 2017/12/18.
 */

public class Method {
    String baseUrl = "https://api.mysspku.com/index.php/V1/MobileCourse/";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void Login(final String username, final String password){
        String url = baseUrl + "Login";
        Log.d("Method", url);

        String json = "{'username':'" + username + "',"
                + "'password':'" + password + "'}";

        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Log.d("Method", body.toString());
        Log.d("Method", request.toString());
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
