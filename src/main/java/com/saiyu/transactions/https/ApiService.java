package com.saiyu.transactions.https;

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

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    //登录
    @POST("manager/login")
    Observable<LoginRet> login(@Body RequestBody body);

    //退出登录
    @POST("manager/signOut")
    Observable<MsgRet> unLogin(@Body RequestBody body);

    //发送验证码
    @POST("manager/sendCode")
    Observable<MsgRet> sendVCode(@Body RequestBody body);

    //数据概览
    @GET("statistics/overview")
    Observable<DataScanRet> scanData();

    //待审核订单
    @GET("orderAudit/waitHandleList")
    Observable<BuyerRet> waitingReview(@Query("auditType") String auditType);

    //审核订单历史
    @GET("orderAudit/history")
    Observable<HistoryRet> historyOrder(@Query("page") String page
                                     , @Query("pageSize") String pageSize);

    //买家发布预审信息
    @GET("orderAudit/orderAuditInfo")
    Observable<BuyerReleaseRet> buyerRelease(@Query("auditId") String auditId);
    //买家代理预审信息
    @GET("orderAudit/orderAgentConfirmAudit")
    Observable<BuyerReplacePretrialRet> buyerReplacePretrial(@Query("auditId") String auditId);
    //卖家出售预审信息
    @GET("orderAudit/receiveSubmitAudit")
    Observable<SellerSellRet> sellerSell(@Query("auditId") String auditId);
    //客服代理确认审核信息
    @GET("orderAudit/agentConfirmAudit")
    Observable<SellerReplaceRet> customerRepacePretrial(@Query("auditId") String auditId);
    //客服验图确认审核信息
    @GET("orderAudit/picConfirmAudit")
    Observable<SellerPicRet> customerPicPretrial(@Query("auditId") String auditId);
    //审核订单
    @POST("orderAudit/handleAudit")
    Observable<MsgRet> doPretrial(@Body RequestBody body);

    //管理员信息
    @GET("manager/managerInfo")
    Observable<ManagerRet> getManager();

    //提现审核受理
    @GET("financeWithdraw/waitHandleList")
    Observable<PostalRet> postalList();
    //提现信息
    @GET("financeWithdraw/withdrawInfo")
    Observable<PostalDetailRet> postalDetail(@Query("withdrawId") String withdrawId);
    //处理提现受理
    @POST("financeWithdraw/handleWithdraw")
    Observable<MsgRet> doPostal(@Body RequestBody body);
    //提现历史
    @GET("financeWithdraw/history")
    Observable<PostalRet> postalHistoryList(@Query("page") String page
            , @Query("pageSize") String pageSize);
    //资金明细
    @GET("financeRecord/record")
    Observable<CapitalRet> capitalList(@Query("account") String account,
                                       @Query("page") String page
            , @Query("pageSize") String pageSize);

    //未处理数据数量
    @GET("statistics/untreatedCount")
    Observable<NewMsgRet> newMsg();


}
