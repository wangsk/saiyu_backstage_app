package com.saiyu.transactions.utils;

import android.util.Log;

import com.saiyu.transactions.consts.ConstValue;

public class LogUtils {

    public static void print(String msg){
        if(ConstValue.flag){
            Log.i("123456",msg);
        }
    }
}
