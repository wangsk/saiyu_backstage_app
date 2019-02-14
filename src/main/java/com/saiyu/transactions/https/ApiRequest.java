package com.saiyu.transactions.https;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.saiyu.transactions.App;
import com.saiyu.transactions.consts.ConstValue;
import com.saiyu.transactions.https.request.RequestParams;
import com.saiyu.transactions.https.response.BuyerReleaseRet;
import com.saiyu.transactions.https.response.BuyerReplacePretrialRet;
import com.saiyu.transactions.https.response.BuyerRet;
import com.saiyu.transactions.https.response.CapitalRet;
import com.saiyu.transactions.https.response.DataScanRet;
import com.saiyu.transactions.https.response.HistoryRet;
import com.saiyu.transactions.https.response.LoginRet;
import com.saiyu.transactions.https.response.ManagerRet;
import com.saiyu.transactions.https.response.MsgRet;
import com.saiyu.transactions.https.response.NewMsgRet;
import com.saiyu.transactions.https.response.PostalDetailRet;
import com.saiyu.transactions.https.response.PostalRet;
import com.saiyu.transactions.https.response.SellerPicRet;
import com.saiyu.transactions.https.response.SellerReplaceRet;
import com.saiyu.transactions.https.response.SellerSellRet;
import com.saiyu.transactions.ui.activitys.LoginActivity_;
import com.saiyu.transactions.utils.CallbackUtils;
import com.saiyu.transactions.utils.LogUtils;
import com.saiyu.transactions.utils.SPUtils;
import com.tencent.android.tpush.XGPushConfig;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiRequest {

    //用账号密码登陆
    public static void accountLogin(String name, String pwd, String codeType, String code, final String callBackKey) {
        //信鸽推送id
        String xgPushId = XGPushConfig.getToken(App.getApp());
        LogUtils.print("xgPushId == "+xgPushId);

        RequestParams requestParams = new RequestParams();
        requestParams.put("name", name);
        requestParams.put("pwd", pwd);
        requestParams.put("codeType", codeType);
        requestParams.put("code", code);
        requestParams.put("xgPushId", xgPushId);

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.login(requestParams.getBody())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginRet>() {
                    @Override
                    public void onCompleted() {
                    }


                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(LoginRet ret) {
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200 || ret.getData() == null) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);

                    }
                });
    }

    public static void unLogin(final Context mContext) {
        RequestParams requestParams = new RequestParams();
        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();
        apiService.unLogin(requestParams.getBody())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MsgRet>() {
                    @Override
                    public void onCompleted() {
                    }


                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        SPUtils.putString("accessToken", "");
                        SPUtils.putString( ConstValue.AUTO_LOGIN_FLAG, "");
                        Intent intent = new Intent(mContext, LoginActivity_.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mContext.startActivity(intent);

                    }

                    @Override
                    public void onNext(MsgRet ret) {
                        SPUtils.putString("accessToken", "");
                        SPUtils.putString( ConstValue.AUTO_LOGIN_FLAG, "");
                        Intent intent = new Intent(mContext, LoginActivity_.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mContext.startActivity(intent);
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200 || ret.getData() == null) {
                            return;
                        }

                    }
                });
    }

    //
    /*
     * 发送验证码
     * type phone:手机；email：邮箱
     *
     * **/
    public static void sendVcode(String name, String pwd, String codeType, final String callBackKey) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();
        RequestParams requestParams = new RequestParams();
        requestParams.put("name", name);//手机号/邮箱
        requestParams.put("pwd", pwd);//1：手机；2：邮箱；
        requestParams.put("codeType", codeType);

        RequestBody body = requestParams.getBody();
        apiService.sendVCode(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MsgRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                        Intent intentshowShort = new Intent();
                        intentshowShort.setAction("sy_close_msg");
                        App.getApp().sendBroadcast(intentshowShort);
                    }

                    @Override
                    public void onNext(MsgRet ret) {
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_msg");
                            App.getApp().sendBroadcast(intentshowShort);
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(App.getApp(), "验证码已发送，请注意查收！", Toast.LENGTH_SHORT).show();

//                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }

    public static void scanData(final String callBackKey, final boolean isFirst) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.scanData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DataScanRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                        if(isFirst){
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_progressbar");
                            App.getApp().sendBroadcast(intentshowShort);
                        }
                    }

                    @Override
                    public void onNext(DataScanRet ret) {
                        if(isFirst){
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_progressbar");
                            App.getApp().sendBroadcast(intentshowShort);
                        }
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }

    public static void waitingReview(String auditType, final String callBackKey, final boolean isFirst) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.waitingReview(auditType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BuyerRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                        if(isFirst){
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_progressbar");
                            App.getApp().sendBroadcast(intentshowShort);
                        }
                    }

                    @Override
                    public void onNext(BuyerRet ret) {
                        if(isFirst){
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_progressbar");
                            App.getApp().sendBroadcast(intentshowShort);
                        }
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }

    public static void historyOrder(String page,String pageSize, final String callBackKey, final boolean isFirst) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.historyOrder(page,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HistoryRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                        if(isFirst){
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_progressbar");
                            App.getApp().sendBroadcast(intentshowShort);
                        }
                    }

                    @Override
                    public void onNext(HistoryRet ret) {
                        if(isFirst){
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_progressbar");
                            App.getApp().sendBroadcast(intentshowShort);
                        }
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }

    public static void buyerRelease(String auditId,final String callBackKey) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.buyerRelease(auditId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BuyerReleaseRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BuyerReleaseRet ret) {
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }
    public static void buyerReplacePretrial(String auditId, final String callBackKey) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.buyerReplacePretrial(auditId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BuyerReplacePretrialRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BuyerReplacePretrialRet ret) {
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }
    public static void sellerSell(String auditId, final String callBackKey) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.sellerSell(auditId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SellerSellRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(SellerSellRet ret) {
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }
    public static void customerRepacePretrial(String auditId, final String callBackKey) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.customerRepacePretrial(auditId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SellerReplaceRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(SellerReplaceRet ret) {
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }
    public static void customerPicPretrial(String auditId, final String callBackKey) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.customerPicPretrial(auditId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SellerPicRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(SellerPicRet ret) {
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }
    public static void doPretrial(String auditId, String auditStatus,String auditRemarks, final String callBackKey) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        RequestParams requestParams = new RequestParams();
        requestParams.put("auditId", auditId);//手机号/邮箱
        requestParams.put("auditStatus", auditStatus);//1：手机；2：邮箱；
        requestParams.put("auditRemarks", auditRemarks);

        RequestBody body = requestParams.getBody();

        apiService.doPretrial(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MsgRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                        Intent intentshowShort = new Intent();
                        intentshowShort.setAction("sy_close_progressbar");
                        App.getApp().sendBroadcast(intentshowShort);
                    }

                    @Override
                    public void onNext(MsgRet ret) {
                        Intent intentshowShort = new Intent();
                        intentshowShort.setAction("sy_close_progressbar");
                        App.getApp().sendBroadcast(intentshowShort);
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }

    public static void getManager(final String callBackKey) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.getManager()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ManagerRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ManagerRet ret) {
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }

    public static void postalList(final String callBackKey, final boolean isFirst) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.postalList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PostalRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                        if(isFirst){
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_progressbar");
                            App.getApp().sendBroadcast(intentshowShort);
                        }
                    }

                    @Override
                    public void onNext(PostalRet ret) {
                        if(isFirst){
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_progressbar");
                            App.getApp().sendBroadcast(intentshowShort);
                        }
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }

    public static void postalDetail(String withdrawId, final String callBackKey) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.postalDetail(withdrawId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PostalDetailRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(PostalDetailRet ret) {
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }

    public static void doPostal(String withdrawId, String status,String isMakeMoney,String remarks, final String callBackKey) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        RequestParams requestParams = new RequestParams();
        requestParams.put("withdrawId", withdrawId);
        requestParams.put("status", status);
        requestParams.put("isMakeMoney", isMakeMoney);
        requestParams.put("remarks", remarks);

        RequestBody body = requestParams.getBody();

        apiService.doPostal(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MsgRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                        Intent intentshowShort = new Intent();
                        intentshowShort.setAction("sy_close_progressbar");
                        App.getApp().sendBroadcast(intentshowShort);
                    }

                    @Override
                    public void onNext(MsgRet ret) {
                        Intent intentshowShort = new Intent();
                        intentshowShort.setAction("sy_close_progressbar");
                        App.getApp().sendBroadcast(intentshowShort);
                        if (ret == null) {
                            return;
                        }
//                        if (ret.getCode() != 200) {
//                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_LONG).show();
//                            return;
//                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }

    public static void postalHistoryList(String page, String pageSize, final String callBackKey, final boolean isFirst) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.postalHistoryList(page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PostalRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                        if(isFirst){
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_progressbar");
                            App.getApp().sendBroadcast(intentshowShort);
                        }
                    }

                    @Override
                    public void onNext(PostalRet ret) {
                        if(isFirst){
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_progressbar");
                            App.getApp().sendBroadcast(intentshowShort);
                        }
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }

    public static void capitalList( String account,String page, String pageSize, final String callBackKey, final boolean isFirst) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.capitalList(account,page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CapitalRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();
                        if(isFirst){
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_progressbar");
                            App.getApp().sendBroadcast(intentshowShort);
                        }
                    }

                    @Override
                    public void onNext(CapitalRet ret) {
                        if(isFirst){
                            Intent intentshowShort = new Intent();
                            intentshowShort.setAction("sy_close_progressbar");
                            App.getApp().sendBroadcast(intentshowShort);
                        }
                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }

    public static void newMsg(final String callBackKey) {

        ApiService apiService = ApiRetrofit.getRetrofit().getApiService();

        apiService.newMsg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewMsgRet>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.print("onError == " + e.toString());
                        Toast.makeText(App.getApp(), "请求失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(NewMsgRet ret) {

                        if (ret == null) {
                            return;
                        }
                        if (ret.getCode() != 200) {
                            Toast.makeText(App.getApp(), ret.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        CallbackUtils.doResponseCallBackMethod(callBackKey, ret);
                    }
                });
    }

}
