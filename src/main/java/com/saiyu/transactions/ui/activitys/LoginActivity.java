package com.saiyu.transactions.ui.activitys;

import android.os.Bundle;

import com.saiyu.transactions.R;
import com.saiyu.transactions.ui.activitys.base.BaseActivity;
import com.saiyu.transactions.ui.fragments.LoginFragment_;

import org.androidannotations.annotations.EActivity;


@EActivity(R.layout.base_frame_layout)
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadRootFragment(R.id.fr_base_container, LoginFragment_.newInstance(null));
    }

}
