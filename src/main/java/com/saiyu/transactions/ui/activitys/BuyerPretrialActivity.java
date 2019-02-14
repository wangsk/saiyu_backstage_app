package com.saiyu.transactions.ui.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.saiyu.transactions.App;
import com.saiyu.transactions.R;
import com.saiyu.transactions.https.ApiRequest;
import com.saiyu.transactions.https.response.BaseRet;
import com.saiyu.transactions.https.response.BuyerReleaseRet;
import com.saiyu.transactions.ui.activitys.base.BaseActivity;
import com.saiyu.transactions.utils.CallbackUtils;

public class BuyerPretrialActivity extends BaseActivity implements CallbackUtils.ResponseCallback {

    private Button btn_title_back,btn_copy_number;
    private TextView tv_title_content,tv_number,tv_auditId,tv_game,tv_count,tv_mobiles,tv_qq,tv_important,tv_word,tv_pass,tv_refuse;
    private String auditId;

    private ProgressBar pb_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_pretrial_layout);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            auditId = bundle.getString("auditId");
        }

        pb_loading = (ProgressBar)findViewById(R.id.pb_loading);

        btn_copy_number = (Button) findViewById(R.id.btn_copy_number);
        btn_title_back = (Button) findViewById(R.id.btn_title_back);

        tv_auditId = (TextView) findViewById(R.id.tv_auditId);
        tv_game = (TextView) findViewById(R.id.tv_game);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_mobiles = (TextView) findViewById(R.id.tv_mobiles);
        tv_qq = (TextView) findViewById(R.id.tv_qq);
        tv_important = (TextView) findViewById(R.id.tv_important);
        tv_pass = (TextView) findViewById(R.id.tv_pass);
        tv_refuse = (TextView) findViewById(R.id.tv_refuse);
        tv_word = (TextView) findViewById(R.id.tv_word);

        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_content.setText("买家发布预审");
        btn_title_back.setOnClickListener(this);
        btn_copy_number.setOnClickListener(this);

        tv_pass.setOnClickListener(this);
        tv_refuse.setOnClickListener(this);


        CallbackUtils.setCallback(BuyerPretrialActivity.this);
        if(!TextUtils.isEmpty(auditId)){
            ApiRequest.buyerRelease(auditId,"BuyerPretrialActivity_buyerRelease");
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
            case R.id.btn_copy_number:
                if (!TextUtils.isEmpty(tv_number.getText())) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(tv_number.getText().toString());
                    Toast.makeText(mContext,"复制成功",Toast.LENGTH_SHORT).show();
                } else return;
                break;
            case R.id.btn_title_back:
                onBackPressed();
                break;
            case R.id.tv_pass:
                if(!TextUtils.isEmpty(auditId)){
                    pb_loading.setVisibility(View.VISIBLE);
                    tv_pass.setClickable(false);
                    tv_refuse.setClickable(false);
                    tv_pass.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                    ApiRequest.doPretrial(auditId,"1","","BuyerPretrialActivity_doPretrial_pass");
                }
                break;
            case R.id.tv_refuse:
                if(!TextUtils.isEmpty(auditId)){
                    Intent intent = new Intent(this,RefuseActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("auditId",auditId);
                    intent.putExtras(bundle);
                    this.startActivity(intent);
                }
                break;
        }
    }
    @Override
    public void setOnResponseCallback(String method, BaseRet baseRet) {
        if(baseRet == null || TextUtils.isEmpty(method)){
            return;
        }
        if(method.equals("BuyerPretrialActivity_buyerRelease")){
            BuyerReleaseRet ret = (BuyerReleaseRet)baseRet;
            if(ret.getData() == null){
                return;
            }

            tv_auditId.setText(ret.getData().getOrderNum());
            tv_number.setText(ret.getData().getReserveAccount());
            tv_game.setText(ret.getData().getProductInfo());
            tv_count.setText(ret.getData().getReserveInfo());
            tv_mobiles.setText("手机:"+ret.getData().getContactMobile());
            tv_qq.setText("QQ  :" + ret.getData().getContactQQ());

            String defaultCount = "<font color = \"#ff3300\">" + ret.getData().getFriendLimitStr() + "</font>";
            tv_important.setText(Html.fromHtml(defaultCount));
            tv_word.setText(ret.getData().getRemarks());

        } else if(method.equals("BuyerPretrialActivity_doPretrial_pass")) {
            Toast.makeText(App.getApp(), "审核成功", Toast.LENGTH_SHORT).show();
            finish();
        }
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
                    tv_pass.setClickable(true);
                    tv_refuse.setClickable(true);
                    tv_pass.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    break;
            }
        }
    }

}
