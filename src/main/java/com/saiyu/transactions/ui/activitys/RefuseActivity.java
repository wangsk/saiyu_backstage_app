package com.saiyu.transactions.ui.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.saiyu.transactions.App;
import com.saiyu.transactions.R;
import com.saiyu.transactions.https.ApiRequest;
import com.saiyu.transactions.https.response.BaseRet;
import com.saiyu.transactions.https.response.MsgRet;
import com.saiyu.transactions.ui.activitys.base.BaseActivity;
import com.saiyu.transactions.utils.CallbackUtils;

public class RefuseActivity extends BaseActivity implements CallbackUtils.ResponseCallback {

    private Button btn_title_back;
    private TextView tv_title_content,tv_refuse;
    private EditText et_refuse;
    private RadioGroup rg_refuse;
    private RadioButton rb_1,rb_2;
    private String auditId;
    private String withdrawId;
    private ProgressBar pb_loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuse_layout);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            auditId = bundle.getString("auditId");
            withdrawId = bundle.getString("withdrawId");
        }

        pb_loading = (ProgressBar)findViewById(R.id.pb_loading);

        et_refuse = (EditText) findViewById(R.id.et_refuse);
        rg_refuse = (RadioGroup) findViewById(R.id.rg_refuse);
        rb_1 = (RadioButton) findViewById(R.id.rb_1);
        rb_2 = (RadioButton) findViewById(R.id.rb_2);

        btn_title_back = (Button) findViewById(R.id.btn_title_back);
        tv_refuse = (TextView) findViewById(R.id.tv_refuse);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_content.setText("审核拒绝原因");
        btn_title_back.setOnClickListener(this);
        tv_refuse.setOnClickListener(this);
        rg_refuse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_1:
                        rb_2.setChecked(false);
                        break;
                    case R.id.rb_2:
                        rb_1.setChecked(false);
                        break;
                }
            }
        });

        if(!TextUtils.isEmpty(withdrawId)){
            rb_1.setVisibility(View.GONE);
            rb_2.setVisibility(View.GONE);
            et_refuse.setHint("请输入原因");
        }

        if (loadingReciver == null) {
            loadingReciver = new LoadingReciver();
            IntentFilter filter = new IntentFilter("sy_close_progressbar");
            mContext.registerReceiver(loadingReciver, filter);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_title_back:
                onBackPressed();
                break;
            case R.id.tv_refuse:
                CallbackUtils.setCallback(RefuseActivity.this);
                if(!TextUtils.isEmpty(auditId)){
                    pb_loading.setVisibility(View.VISIBLE);
                    tv_refuse.setClickable(false);
                    tv_refuse.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                    if(rb_1.isChecked()){
                        ApiRequest.doPretrial(auditId,"2","添加好友失败，请修改后重新发布","RefuseActivity_refuse");
                    } else {
                        String str = et_refuse.getText().toString();
                        ApiRequest.doPretrial(auditId,"2",str,"RefuseActivity_refuse");
                    }
                } else if(!TextUtils.isEmpty(withdrawId)){
                    pb_loading.setVisibility(View.VISIBLE);
                    tv_refuse.setClickable(false);
                    tv_refuse.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                    String str = et_refuse.getText().toString();
                    ApiRequest.doPostal(withdrawId,"2","0",str,"RefuseActivity_doPostal");
                }

                break;
        }
    }

    @Override
    public void setOnResponseCallback(String method, BaseRet baseRet) {
        if(baseRet == null || TextUtils.isEmpty(method)){
            return;
        }
        if(method.equals("RefuseActivity_refuse")){
            Toast.makeText(App.getApp(), "审核拒绝成功", Toast.LENGTH_SHORT).show();
        }else if(method.equals("RefuseActivity_doPostal")){
            MsgRet ret = (MsgRet)baseRet;
            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingReciver != null) {
            mContext.unregisterReceiver(loadingReciver);
            loadingReciver = null;
        }
    }

    private LoadingReciver loadingReciver;

    private class LoadingReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context mContext, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "sy_close_progressbar":
                    pb_loading.setVisibility(View.GONE);
                    tv_refuse.setClickable(true);
                    tv_refuse.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                    break;
            }
        }
    }
}
