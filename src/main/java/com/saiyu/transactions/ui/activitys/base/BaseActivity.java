package com.saiyu.transactions.ui.activitys.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.saiyu.transactions.cashe.CacheActivity;

import me.yokeyword.fragmentation.SupportActivity;

public class BaseActivity extends SupportActivity implements View.OnClickListener {

    protected Activity mContext = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        CacheActivity.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        CacheActivity.removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }
}
