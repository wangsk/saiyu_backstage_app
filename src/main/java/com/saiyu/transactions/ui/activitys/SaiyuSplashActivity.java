package com.saiyu.transactions.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.saiyu.transactions.R;
import com.saiyu.transactions.consts.ConstValue;
import com.saiyu.transactions.ui.activitys.base.BaseActivity;
import com.saiyu.transactions.utils.LogUtils;
import com.saiyu.transactions.utils.SPUtils;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_splash)
public class SaiyuSplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtils.print("token == " + SPUtils.getString(ConstValue.ACCESS_TOKEN,""));
        if(!TextUtils.isEmpty(SPUtils.getString(ConstValue.AUTO_LOGIN_FLAG,"")) && !TextUtils.isEmpty(SPUtils.getString(ConstValue.ACCESS_TOKEN,""))){
            Intent intentlogin = new Intent(SaiyuSplashActivity.this,
                    MainActivity.class);
            SaiyuSplashActivity.this.startActivity(intentlogin);
            SaiyuSplashActivity.this.finish();

        } else {
            Intent intent = new Intent(this,LoginActivity_.class);
            startActivity(intent);
            SaiyuSplashActivity.this.finish();
        }
    }

}
