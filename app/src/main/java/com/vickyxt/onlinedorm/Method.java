package com.vickyxt.onlinedorm;

import android.util.Log;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.acl.Group;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.callback.Callback;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * Created by VickyXT on 2017/12/18.
 */

public class Method {
    String baseUrl = "https://api.mysspku.com/index.php/V1/MobileCourse/";
    final static String TAG = "Method";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public void Login(String username, String password, final MyCallback callback){
        try {
            String url = baseUrl + "Login";
            String parameter = "?username=" + username + "&password=" + password;
            url += parameter;
            Log.d("Method", url);

            OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
            mBuilder.sslSocketFactory(createSSLSocketFactory(), new TrustAllManager());
            mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());

            OkHttpClient client = mBuilder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.d(TAG,e.toString());
                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    // 注：该回调是子线程，非主线程
                    Log.d(TAG,"callback thread id is "+Thread.currentThread().getId());
                    String json = response.body().string();
                    Log.d(TAG,json);
                    HashMap<String,String> map = com.alibaba.fastjson.JSON.parseObject(json, new TypeReference<HashMap<String,String>>() {});
                    String errcode = (String) map.get("errcode");
                    if (errcode.equals("0")) {
                        HashMap<String,String> data = com.alibaba.fastjson.JSON.parseObject(map.get("data"), new TypeReference<HashMap<String,String>>() {});
                        callback.onSuccess(data);
                    } else {
                        callback.onError(errcode);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDetail(String stuid, final MyCallback callback){
        try {
            String url = baseUrl + "getDetail";
            String parameter = "?stuid=" + stuid;
            url += parameter;
            Log.d("Method", url);

            OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
            mBuilder.sslSocketFactory(createSSLSocketFactory(), new TrustAllManager());
            mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());

            OkHttpClient client = mBuilder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    callback.onError(e.toString());
                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    // 注：该回调是子线程，非主线程
                    Log.d(TAG,"callback thread id is "+Thread.currentThread().getId());
                    String json = response.body().string();
                    Log.d(TAG,json);
                    HashMap<String,String> map = com.alibaba.fastjson.JSON.parseObject(json, new TypeReference<HashMap<String,String>>() {});
                    String errcode = (String) map.get("errcode");
                    if (errcode.equals("0")) {
                        HashMap<String,String> data = com.alibaba.fastjson.JSON.parseObject(map.get("data"), new TypeReference<HashMap<String,String>>() {});
                        callback.onSuccess(data);
                    } else {
                        callback.onError(errcode);
                    }
                }
            });
        } catch (Exception e) {
            callback.onError(e.toString());
        }
    }

    public void getRoom(String gender, final MyCallback callback){
        try {
            String url = baseUrl + "getRoom";
            String parameter = "?gender=" + gender;
            url += parameter;
            Log.d("Method", url);

            OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
            mBuilder.sslSocketFactory(createSSLSocketFactory(), new TrustAllManager());
            mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());

            OkHttpClient client = mBuilder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.d(TAG,e.toString());
                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    // 注：该回调是子线程，非主线程
                    Log.d(TAG,"callback thread id is "+Thread.currentThread().getId());
                    String json = response.body().string();
                    Log.d(TAG,json);
                    HashMap<String,String> map = com.alibaba.fastjson.JSON.parseObject(json, new TypeReference<HashMap<String,String>>() {});
                    String errcode = (String) map.get("errcode");
                    if (errcode.equals("0")) {
                        HashMap<String,String> data = com.alibaba.fastjson.JSON.parseObject(map.get("data"), new TypeReference<HashMap<String,String>>() {});
                        callback.onSuccess(data);
                    } else {
                        callback.onError(errcode);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SelectRoom(String buildingNo, String num, String stuid, String stu1id, String v1code, String stu2id,
                           String v2code, String stu3id, String v3code, final MyCallback callback){
        try {
            String url = baseUrl + "SelectRoom";
            Log.d("Method", url);

            String parameter = "{'num':'" + num + "',"
                    + "'stuid':'" + stuid + "',"
                    + "'stu1id':'" + stu1id + "',"
                    + "'v1code':'" + v1code + "',"
                    + "'stu2id':'" + stu2id + "',"
                    + "'v2code':'" + v2code + "',"
                    + "'stu3id':'" + stu3id + "',"
                    + "'v3code':'" + v3code + "',"
                    + "'buildingNo':'" + buildingNo + "'}";
            OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
            mBuilder.sslSocketFactory(createSSLSocketFactory(), new TrustAllManager());
            mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());

            OkHttpClient client = mBuilder.build();
            RequestBody body = RequestBody.create(JSON, parameter);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.d(TAG,e.toString());
                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    // 注：该回调是子线程，非主线程
                    Log.d(TAG,"callback thread id is "+Thread.currentThread().getId());
                    String json = response.body().string();
                    Log.d(TAG,json);
                    HashMap<String,String> map = com.alibaba.fastjson.JSON.parseObject(json, new TypeReference<HashMap<String,String>>() {});
                    String errcode = (String) map.get("errcode");
                    if (errcode.equals("0")) {
                        callback.onSuccess(null);
                    } else {
                        callback.onError(errcode);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
                throws java.security.cert.CertificateException {

        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
                throws java.security.cert.CertificateException {

        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
