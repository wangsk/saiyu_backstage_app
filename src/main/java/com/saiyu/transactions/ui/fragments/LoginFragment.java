package com.saiyu.transactions.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.saiyu.transactions.R;
import com.saiyu.transactions.cashe.CacheActivity;
import com.saiyu.transactions.consts.ConstValue;
import com.saiyu.transactions.https.ApiRequest;
import com.saiyu.transactions.https.response.BaseRet;
import com.saiyu.transactions.https.response.LoginRet;
import com.saiyu.transactions.ui.activitys.MainActivity;
import com.saiyu.transactions.ui.fragments.base.BaseFragment;
import com.saiyu.transactions.utils.CallbackUtils;
import com.saiyu.transactions.utils.CountDownTimerUtils;
import com.saiyu.transactions.utils.SPUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_login_layout)
public class LoginFragment extends BaseFragment implements TextWatcher, CallbackUtils.ResponseCallback{

    @ViewById
    Button  btn_msg_count, btn_login;
    @ViewById
    EditText et_username, et_password, et_msgcode;
    @ViewById
    ImageView img_delename, img_delepsw;
    private static CountDownTimerUtils countDownTimerUtils;
    @ViewById
    RadioButton rb_mobile,rb_email;
    @ViewById
    RadioGroup rg_selector;
    private String userName,userPassword;

    public static LoginFragment newInstance(Bundle bundle) {
        LoginFragment_ fragment = new LoginFragment_();
        fragment.setArguments(bundle);
        return fragment;
    }

    @AfterViews
    void afterView(){
        CallbackUtils.setCallback(this);
        rg_selector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_email:
                        rb_mobile.setChecked(false);
                        break;
                    case R.id.rb_mobile:
                        rb_email.setChecked(false);
                        break;
                }
            }
        });

        countDownTimerUtils = new CountDownTimerUtils(btn_msg_count, 60000, 1000);

        btn_msg_count.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        img_delename.setOnClickListener(this);
        img_delepsw.setOnClickListener(this);

        et_username.addTextChangedListener(this);

        if (loadingReciver == null) {
            loadingReciver = new LoadingReciver();
            IntentFilter filter = new IntentFilter("sy_close_msg");
            mContext.registerReceiver(loadingReciver, filter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (loadingReciver != null) {
            try {
                mContext.unregisterReceiver(loadingReciver);
                loadingReciver = null;
            } catch (Exception e){

            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_msg_count:
                userName = et_username.getText().toString().trim();
                userPassword = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(mContext, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(rb_mobile.isChecked()){
                    ApiRequest.sendVcode(userName,userPassword,"phone","login_sendVcode");
                } else {
                    ApiRequest.sendVcode(userName,userPassword,"email","login_sendVcode");
                }
                countDownTimerUtils.start();

                break;
            case R.id.btn_login:
                userName = et_username.getText().toString().trim();
                userPassword = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(mContext, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String str = et_msgcode.getText().toString().trim();

                if (TextUtils.isEmpty(str)) {
                    Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(rb_mobile.isChecked()){
                    ApiRequest.accountLogin(userName,userPassword,"phone",str,"login_accountLogin");
                } else {
                    ApiRequest.accountLogin(userName,userPassword,"email",str,"login_accountLogin");
                }


                break;
            case R.id.img_delename:
                et_username.setText("");
                break;
            case R.id.img_delepsw:
                et_password.setText("");
                break;
        }
    }

    @Override
    public void setOnResponseCallback(String method, BaseRet baseRet) {
        if(baseRet == null || TextUtils.isEmpty(method)){
            return;
        }
        if(method.equals("login_accountLogin")){
            LoginRet ret = (LoginRet) baseRet;
            if(ret.getData() == null){
                return;
            }

            SPUtils.putString("accessToken", ret.getData().getAccessToken());
            SPUtils.putString(ConstValue.AUTO_LOGIN_FLAG, ConstValue.PWD_LOGIN);

            Intent intentlogin = new Intent(mContext,
                    MainActivity.class);
            mContext.startActivity(intentlogin);
            mContext.finish();

        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String text = s.toString();
        if (text.length() == 0) {
            et_password.setText("");
            et_msgcode.setText("");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onBackPressedSupport() {
        CacheActivity.finishActivity();
        System.exit(0);
        return true;
    }

    private LoadingReciver loadingReciver;

    private class LoadingReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context mContext, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "sy_close_msg":
                    countDownTimerUtils.onFinish();
                    break;
            }
        }
    }

}
