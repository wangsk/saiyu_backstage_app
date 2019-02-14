package com.saiyu.transactions.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.saiyu.transactions.App;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.UUID;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Utils {

    public static void reqPermission(Activity activity) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            String uuid = getMyUUID();

                            SPUtils.putString("UUID", uuid);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    //获取UUID
    public static String getMyUUID() {

        final TelephonyManager tm = (TelephonyManager) App.getApp().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;

        tmDevice = "" + tm.getDeviceId();

        tmSerial = "" + tm.getSimSerialNumber();

        androidId = "" + Settings.Secure.getString(App.getApp().getContentResolver(), Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

        String uniqueId = deviceUuid.toString();

        return uniqueId;
    }

}
