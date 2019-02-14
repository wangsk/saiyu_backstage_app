package com.saiyu.transactions.utils;

import com.saiyu.transactions.https.response.BaseRet;

public class CallbackUtils {

    public interface ResponseCallback{
        public void setOnResponseCallback(String method,BaseRet baseRet);
    }

    private static ResponseCallback mResponseCallback;

    public static void setCallback(ResponseCallback callback){
        mResponseCallback = callback;
    }

    public static void doResponseCallBackMethod(String method,BaseRet baseRet){
        if(mResponseCallback != null){
            mResponseCallback.setOnResponseCallback(method,baseRet);
        }
    }

}
