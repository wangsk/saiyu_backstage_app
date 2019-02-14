package com.saiyu.transactions;

import android.app.Application;

import com.saiyu.transactions.utils.LogUtils;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

public class App extends Application {

    private static App mApp;
    public static App getApp(){return mApp;}

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        //开启信鸽的日志输出，线上版本不建议调用
//        XGPushConfig.enableDebug(this, false);

          /*
        注册信鸽服务的接口
        如果仅仅需要发推送消息调用这段代码即可
        */
        XGPushManager.registerPush(getApplicationContext(),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        LogUtils.print("+++ register push sucess. token:" + data);

                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        LogUtils.print(
                                "+++ register push fail. token:" + data
                                        + ", errCode:" + errCode + ",msg:"
                                        + msg);
                    }
                });

    }

}
