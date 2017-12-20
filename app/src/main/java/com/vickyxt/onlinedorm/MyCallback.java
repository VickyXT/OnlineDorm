package com.vickyxt.onlinedorm;

import java.util.HashMap;

/**
 * Created by nmchgx on 2017/12/20.
 */

public interface MyCallback {
    public void onSuccess( HashMap<String,String> data ) ;

    public void onError( String error ) ;
}
