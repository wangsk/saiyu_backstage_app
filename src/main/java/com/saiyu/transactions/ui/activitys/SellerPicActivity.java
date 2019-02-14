package com.saiyu.transactions.ui.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.saiyu.transactions.App;
import com.saiyu.transactions.R;
import com.saiyu.transactions.https.ApiRequest;
import com.saiyu.transactions.https.response.BaseRet;
import com.saiyu.transactions.https.response.SellerPicRet;
import com.saiyu.transactions.https.response.SellerReplaceRet;
import com.saiyu.transactions.ui.activitys.base.BaseActivity;
import com.saiyu.transactions.ui.views.PhotoViewDialog;
import com.saiyu.transactions.utils.CallbackUtils;

public class SellerPicActivity extends BaseActivity  implements CallbackUtils.ResponseCallback {

    private Button btn_title_back;
    private TextView tv_title_content,tv_pass,tv_refuse;
    private ImageView photoView_1,photoView_2,photoView_3;
    private String url_pic_RechargeSucc;
    private String url_pic_TradeInfo;
    private String url_pic_BillRecord;
    private RelativeLayout rl_more_layout;
    private TextView tv_more;
    private LinearLayout ll_1,ll_2;
    private TextView tv_productInfo,tv_reserveInfo,tv_succQBCount,tv_succMoney,tv_confirmTypeStr,tv_rechargeTime,
            tv_finishTime,tv_receiveOrderNum,tv_onceLimit,tv_buyerAccount,tv_buyerRealName,tv_sellerAccount,tv_sellerRealName,
            tv_createTime,tv_penaltyMoney,tv_autoConfirmTime,tv_averageConfirmTime,tv_place,tv_mobile,tv_email,tv_qq,tv_qq_psw,tv_orderAccount;

    private String auditId;
    private ProgressBar pb_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_pic_layout);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            auditId = bundle.getString("auditId");
        }

        pb_loading = (ProgressBar)findViewById(R.id.pb_loading);

        ll_1 = (LinearLayout)findViewById(R.id.ll_1);
        ll_2 = (LinearLayout)findViewById(R.id.ll_2);
        rl_more_layout = (RelativeLayout)findViewById(R.id.rl_more_layout);
        tv_more = (TextView)findViewById(R.id.tv_more);

        tv_pass = (TextView) findViewById(R.id.tv_pass);
        tv_refuse = (TextView) findViewById(R.id.tv_refuse);
        tv_qq = (TextView) findViewById(R.id.tv_qq);
        tv_qq_psw = (TextView) findViewById(R.id.tv_qq_psw);

        tv_place = (TextView)findViewById(R.id.tv_place);
        tv_mobile = (TextView)findViewById(R.id.tv_mobile);
        tv_email = (TextView)findViewById(R.id.tv_email);

        tv_orderAccount = (TextView)findViewById(R.id.tv_orderAccount);
        tv_productInfo = (TextView)findViewById(R.id.tv_productInfo);
        tv_reserveInfo = (TextView)findViewById(R.id.tv_reserveInfo);
        tv_succQBCount = (TextView)findViewById(R.id.tv_succQBCount);
        tv_succMoney = (TextView)findViewById(R.id.tv_succMoney);
        tv_confirmTypeStr = (TextView)findViewById(R.id.tv_confirmTypeStr);
        tv_rechargeTime = (TextView)findViewById(R.id.tv_rechargeTime);
        tv_finishTime = (TextView)findViewById(R.id.tv_finishTime);
        tv_receiveOrderNum = (TextView)findViewById(R.id.tv_receiveOrderNum);
        tv_onceLimit = (TextView)findViewById(R.id.tv_onceLimit);
        tv_buyerAccount = (TextView)findViewById(R.id.tv_buyerAccount);
        tv_buyerRealName = (TextView)findViewById(R.id.tv_buyerRealName);
        tv_sellerAccount = (TextView)findViewById(R.id.tv_sellerAccount);
        tv_sellerRealName = (TextView)findViewById(R.id.tv_sellerRealName);
        tv_createTime = (TextView)findViewById(R.id.tv_createTime);
        tv_penaltyMoney = (TextView)findViewById(R.id.tv_penaltyMoney);
        tv_autoConfirmTime = (TextView)findViewById(R.id.tv_autoConfirmTime);
        tv_averageConfirmTime = (TextView)findViewById(R.id.tv_averageConfirmTime);

        rl_more_layout.setOnClickListener(this);

        btn_title_back = (Button) findViewById(R.id.btn_title_back);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_content.setText("验图确认预审");
        btn_title_back.setOnClickListener(this);

        photoView_1 = (ImageView)findViewById(R.id.pv_1);
        photoView_2 = (ImageView)findViewById(R.id.pv_2);
        photoView_3 = (ImageView) findViewById(R.id.pv_3);
        photoView_1.setOnClickListener(this);
        photoView_2.setOnClickListener(this);
        photoView_3.setOnClickListener(this);
        tv_pass.setOnClickListener(this);
        tv_refuse.setOnClickListener(this);

        CallbackUtils.setCallback(SellerPicActivity.this);
        if(!TextUtils.isEmpty(auditId)){
            ApiRequest.customerPicPretrial(auditId,"SellerPicActivity_customerPicPretrial");
        }

        if (loadingReciver == null) {
            loadingReciver = new LoadingReciver();
            IntentFilter filter = new IntentFilter("sy_close_progressbar");
            mContext.registerReceiver(loadingReciver, filter);
        }
    }
    @Override
    public void setOnResponseCallback(String method, BaseRet baseRet) {
        if(baseRet == null || TextUtils.isEmpty(method)){
            return;
        }
        if(method.equals("SellerPicActivity_customerPicPretrial")){
            SellerPicRet ret = (SellerPicRet)baseRet;
            if(ret.getData() == null){
                return;
            }

            tv_orderAccount.setText(ret.getData().getSellerAccount());

            tv_email.setText(ret.getData().getNoticeEmail());
            tv_mobile.setText(ret.getData().getNoticeMobile());
            tv_qq.setText(ret.getData().getBillQQNum());
            tv_qq_psw.setText(ret.getData().getBillQQPwd());

            tv_succMoney.setText(ret.getData().getSuccMoney());
            String defaultCount = ""+"<font color = \"#ff3300\">" + ret.getData().getConfirmTypeStr() + "</font>";
            tv_confirmTypeStr.setText(Html.fromHtml(defaultCount));

            tv_productInfo.setText(ret.getData().getProductInfo());
            tv_reserveInfo.setText(ret.getData().getReserveInfo());
            tv_succQBCount.setText(ret.getData().getSuccQBCount());

            tv_rechargeTime.setText(ret.getData().getRechargeTime());
            tv_finishTime.setText(ret.getData().getFinishTime());
            tv_receiveOrderNum.setText(ret.getData().getReceiveOrderNum());
            tv_onceLimit.setText(ret.getData().getOnceLimit());
            tv_buyerAccount.setText(ret.getData().getBuyerAccount());
            tv_buyerRealName.setText(ret.getData().getBuyerRealName());
            tv_sellerAccount.setText(ret.getData().getSellerAccount());
            tv_sellerRealName.setText(ret.getData().getSellerRealName());
            tv_createTime.setText(ret.getData().getCreateTime());
            tv_penaltyMoney.setText(ret.getData().getPenaltyMoney());
            tv_autoConfirmTime.setText(ret.getData().getAutoConfirmTime());
            tv_averageConfirmTime.setText(ret.getData().getAverageConfirmTime());
            url_pic_BillRecord = ret.getData().getPic_BillRecord();
            url_pic_RechargeSucc = ret.getData().getPic_RechargeSucc();
            url_pic_TradeInfo = ret.getData().getPic_TradeInfo();

            tv_place.setText(ret.getData().getOftenLoginArea());


            if(!TextUtils.isEmpty(url_pic_BillRecord)){
                Glide.with(App.getApp())
                        .load(url_pic_RechargeSucc)
                        .error(R.mipmap.ic_launcher)
                        .into(photoView_1);
            }
            if(!TextUtils.isEmpty(url_pic_RechargeSucc)){
                Glide.with(App.getApp())
                        .load(url_pic_TradeInfo)
                        .error(R.mipmap.ic_launcher)
                        .into(photoView_2);
            }
            if(!TextUtils.isEmpty(url_pic_TradeInfo)){
                Glide.with(App.getApp())
                        .load(url_pic_BillRecord)
                        .error(R.mipmap.ic_launcher)
                        .into(photoView_3);
            }
        } else if(method.equals("SellerPicActivity_doPretrial_pass")){
            Toast.makeText(App.getApp(), "审核成功", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(method.equals("SellerPicActivity_refuse")){
            Toast.makeText(App.getApp(), "审核拒绝成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_title_back:
                onBackPressed();
                break;
            case R.id.pv_1:
                if(!TextUtils.isEmpty(url_pic_RechargeSucc)){
                    PhotoViewDialog photoViewDialog = new PhotoViewDialog(mContext);
                    photoViewDialog.setmUrl(url_pic_RechargeSucc);
                    photoViewDialog.show();
                }

                break;
            case R.id.pv_2:
                if(!TextUtils.isEmpty(url_pic_TradeInfo)){
                    PhotoViewDialog photoViewDialog = new PhotoViewDialog(mContext);
                    photoViewDialog.setmUrl(url_pic_TradeInfo);
                    photoViewDialog.show();
                }

                break;
            case R.id.pv_3:
                if(!TextUtils.isEmpty(url_pic_BillRecord)){
                    PhotoViewDialog photoViewDialog = new PhotoViewDialog(mContext);
                    photoViewDialog.setmUrl(url_pic_BillRecord);
                    photoViewDialog.show();
                }

                break;
            case R.id.rl_more_layout:
                if(ll_1.getVisibility() == View.GONE){
                    ll_1.setVisibility(View.VISIBLE);
                    ll_2.setVisibility(View.GONE);
                    tv_more.setText("更多订单信息");
                } else {
                    ll_1.setVisibility(View.GONE);
                    ll_2.setVisibility(View.VISIBLE);
                    tv_more.setText("返回订单信息");
                }
                break;
            case R.id.tv_pass:
                if(!TextUtils.isEmpty(auditId)){
                    pb_loading.setVisibility(View.VISIBLE);
                    tv_pass.setClickable(false);
                    tv_refuse.setClickable(false);
                    tv_pass.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                    ApiRequest.doPretrial(auditId,"1","打款给卖家","SellerPicActivity_doPretrial_pass");
                }
                break;
            case R.id.tv_refuse:
                if(!TextUtils.isEmpty(auditId)){
                    pb_loading.setVisibility(View.VISIBLE);
                    tv_pass.setClickable(false);
                    tv_refuse.setClickable(false);
                    tv_refuse.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                    ApiRequest.doPretrial(auditId,"2","改为买家确认","SellerPicActivity_refuse");
                }
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
                    tv_refuse.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                    break;
            }
        }
    }
}
