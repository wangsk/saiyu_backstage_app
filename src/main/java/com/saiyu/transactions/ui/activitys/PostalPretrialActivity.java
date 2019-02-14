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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.saiyu.transactions.R;
import com.saiyu.transactions.https.ApiRequest;
import com.saiyu.transactions.https.response.BaseRet;
import com.saiyu.transactions.https.response.ManagerRet;
import com.saiyu.transactions.https.response.MsgRet;
import com.saiyu.transactions.https.response.PostalDetailRet;
import com.saiyu.transactions.ui.activitys.base.BaseActivity;
import com.saiyu.transactions.ui.views.ReboundScrollView;
import com.saiyu.transactions.utils.CallbackUtils;

public class PostalPretrialActivity extends BaseActivity implements CallbackUtils.ResponseCallback {
    private Button btn_title_back;
    private TextView tv_title_content,tv_title_right;
    private Button btn_copy_1,btn_copy_2,btn_copy_3;
    private TextView tv_postal_name,tv_postal_account,tv_postal_number;
    private Switch sw,sw_2;
    private TextView tv_more,tv_postal_audit,tv_postal_member,tv_postal_channel,tv_postal_fee,tv_postal_risk
            ,tv_postal_buyermsg,tv_postal_sellermsg,tv_postal_sum,tv_pass,tv_refuse,tv_postal_status,tv_postal_remark
            ,tv_postal_result,btn_postal_result;
    private String withdrawId;
    private String account;
    private String orderNum;
    private ProgressBar pb_loading;
    private boolean flag;
    private RelativeLayout rl_more_layout,rl_2;
    private LinearLayout ll_status,ll_remark,ll_postal_result;
    private ReboundScrollView rs_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postal_pretrial_layout);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            withdrawId = bundle.getString("withdrawId");
            flag = bundle.getBoolean("flag",false);
        }

        ll_postal_result = (LinearLayout)findViewById(R.id.ll_postal_result);
        tv_postal_result = (TextView)findViewById(R.id.tv_postal_result);
        btn_postal_result = (TextView)findViewById(R.id.btn_postal_result);
        btn_postal_result.setOnClickListener(this);

        rs_complete = (ReboundScrollView)findViewById(R.id.rs_complete);

        ll_status = (LinearLayout)findViewById(R.id.ll_status);
        ll_remark = (LinearLayout)findViewById(R.id.ll_remark);
        tv_postal_status = (TextView)findViewById(R.id.tv_postal_status);
        tv_postal_remark = (TextView)findViewById(R.id.tv_postal_remark);

        rl_more_layout = (RelativeLayout)findViewById(R.id.rl_more_layout);
        rl_2 = (RelativeLayout)findViewById(R.id.rl_2);

        pb_loading = (ProgressBar)findViewById(R.id.pb_loading);

        tv_postal_audit = (TextView) findViewById(R.id.tv_postal_audit);
        tv_postal_member = (TextView) findViewById(R.id.tv_postal_member);
        tv_postal_channel = (TextView) findViewById(R.id.tv_postal_channel);
        tv_postal_sum = (TextView) findViewById(R.id.tv_postal_sum);
        tv_postal_fee = (TextView) findViewById(R.id.tv_postal_fee);
        tv_postal_risk = (TextView) findViewById(R.id.tv_postal_risk);
        tv_postal_buyermsg = (TextView) findViewById(R.id.tv_postal_buyermsg);
        tv_postal_sellermsg = (TextView) findViewById(R.id.tv_postal_sellermsg);

        tv_more = (TextView)findViewById(R.id.tv_more);

        sw = (Switch)findViewById(R.id.sw);
        sw_2 = (Switch)findViewById(R.id.sw_2);

        btn_copy_1 = (Button)findViewById(R.id.btn_copy_1);
        btn_copy_2 = (Button)findViewById(R.id.btn_copy_2);
        btn_copy_3 = (Button)findViewById(R.id.btn_copy_3);
        btn_title_back = (Button) findViewById(R.id.btn_title_back);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_postal_name = (TextView) findViewById(R.id.tv_postal_name);
        tv_postal_account = (TextView) findViewById(R.id.tv_postal_account);
        tv_postal_number = (TextView) findViewById(R.id.tv_postal_number);

        tv_pass = (TextView) findViewById(R.id.tv_pass);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tv_pass.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    tv_pass.setClickable(true);
                } else {
                    tv_pass.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                    tv_pass.setClickable(false);
                }
            }
        });
        tv_refuse = (TextView) findViewById(R.id.tv_refuse);

        tv_title_right.setText("资金明细");
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_content.setText("提现审核");
        btn_title_back.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
        btn_copy_1.setOnClickListener(this);
        btn_copy_2.setOnClickListener(this);
        btn_copy_3.setOnClickListener(this);
        tv_pass.setOnClickListener(this);
        tv_refuse.setOnClickListener(this);
        tv_pass.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
        tv_pass.setClickable(false);

        if(flag){
            tv_refuse.setVisibility(View.GONE);
            rl_more_layout.setVisibility(View.GONE);
            rl_2.setVisibility(View.GONE);
            tv_pass.setVisibility(View.GONE);
            ll_remark.setVisibility(View.VISIBLE);
            ll_status.setVisibility(View.VISIBLE);
            tv_title_content.setText("提现历史");
        }

        CallbackUtils.setCallback(PostalPretrialActivity.this);
        if(!TextUtils.isEmpty(withdrawId)){
            ApiRequest.postalDetail(withdrawId,"PostalPretrialActivity_postalDetail");
        }

        if (loadingReciver == null) {
            loadingReciver = new LoadingReciver();
            IntentFilter filter = new IntentFilter("sy_close_progressbar");
            mContext.registerReceiver(loadingReciver, filter);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ApiRequest.getManager("PostalPretrialActivity_getManager");
    }

    @Override
    public void setOnResponseCallback(String method, BaseRet baseRet) {
        if(baseRet == null || TextUtils.isEmpty(method)){
            return;
        }
        if(method.equals("PostalPretrialActivity_postalDetail")){
            PostalDetailRet ret = (PostalDetailRet)baseRet;
            if(ret.getData() == null){
                return;
            }
            tv_postal_remark.setText(ret.getData().getRemarks());
            tv_postal_status.setText(ret.getData().getStatusStr());
            orderNum = ret.getData().getOrderNum();
            account = ret.getData().getUserAccount();
            tv_postal_audit.setText(ret.getData().getOrderNum());
            tv_postal_member.setText(ret.getData().getUserAccount());
            tv_postal_channel.setText(ret.getData().getWithdrawWayName());
            tv_postal_name.setText(ret.getData().getRealName());
            tv_postal_account.setText(ret.getData().getWithdrawAccount());
            tv_postal_sum.setText(ret.getData().getApplyMoney());
            tv_postal_fee.setText(ret.getData().getChargeMoeny());
            tv_postal_number.setText(ret.getData().getSuccMoney());
            tv_postal_risk.setText(ret.getData().getUserRiskLevelStr());
            tv_postal_buyermsg.setText(ret.getData().getBuyerOrderRSettleTotalCount()+ " 笔; "+ ret.getData().getBuyerOrderRSettleTotalMoney() + " 元; ");
            tv_postal_sellermsg.setText(ret.getData().getSellerOrderRSettleTotalCount() + " 笔; " + ret.getData().getSellerOrderRSettleTotalMoney() + " 元; ");


        } else if(method.equals("PostalPretrialActivity_getManager")){
            ManagerRet ret = (ManagerRet)baseRet;
            if(ret.getData() == null){
                return;
            }
            String defaultCount = "我 "+"<font color = \"#ff3300\">" + ret.getData().getRealName() + "</font>" + " 已审核资金正常";
            tv_more.setText(Html.fromHtml(defaultCount));
        } else if(method.equals("PostalPretrialActivity_doPostal")){
            MsgRet ret = (MsgRet)baseRet;
            rs_complete.setVisibility(View.GONE);
            ll_postal_result.setVisibility(View.VISIBLE);

            tv_postal_result.setText("打款结果：" + ret.getMsg());
            tv_title_content.setText("提现受理结果");
            tv_title_right.setVisibility(View.GONE);

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_title_back:
                onBackPressed();
                break;
            case R.id.tv_title_right:
                Intent intent = new Intent(this,CapitalDetailActivity.class);
                Bundle bundle_1 = new Bundle();
                bundle_1.putString("account",account);
                bundle_1.putString("orderNum",orderNum);
                intent.putExtras(bundle_1);
                startActivity(intent);
                break;
            case R.id.btn_copy_1:
                if (!TextUtils.isEmpty(tv_postal_name.getText())) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(tv_postal_name.getText().toString());
                    Toast.makeText(mContext,"复制成功",Toast.LENGTH_SHORT).show();
                } else return;
                break;
            case R.id.btn_copy_2:
                if (!TextUtils.isEmpty(tv_postal_account.getText())) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(tv_postal_account.getText().toString());
                    Toast.makeText(mContext,"复制成功",Toast.LENGTH_SHORT).show();
                } else return;
                break;
            case R.id.btn_copy_3:
                if (!TextUtils.isEmpty(tv_postal_number.getText())) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(tv_postal_number.getText().toString());
                    Toast.makeText(mContext,"复制成功",Toast.LENGTH_SHORT).show();
                } else return;
                break;
            case R.id.tv_pass:
                if(!TextUtils.isEmpty(withdrawId) && sw.isChecked()){
                    pb_loading.setVisibility(View.VISIBLE);
                    tv_pass.setClickable(false);
                    tv_refuse.setClickable(false);
                    tv_pass.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                    if(sw_2.isChecked()){
                        ApiRequest.doPostal(withdrawId,"1","1","","PostalPretrialActivity_doPostal");
                    } else {
                        ApiRequest.doPostal(withdrawId,"1","0","","PostalPretrialActivity_doPostal");
                    }
                } else {
                    Toast.makeText(mContext,"请管理员勾选已审核",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_refuse:
                if(!TextUtils.isEmpty(withdrawId)){
                    Intent intent_2 = new Intent(this,RefuseActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("withdrawId",withdrawId);
                    intent_2.putExtras(bundle);
                    this.startActivity(intent_2);
                }
                break;
            case R.id.btn_postal_result:
                onBackPressed();
                break;
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
