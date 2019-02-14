package com.saiyu.transactions.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.saiyu.transactions.R;
import com.saiyu.transactions.adapters.DataScanAdapter;
import com.saiyu.transactions.https.ApiRequest;
import com.saiyu.transactions.https.response.BaseRet;
import com.saiyu.transactions.https.response.DataScanBean;
import com.saiyu.transactions.https.response.DataScanRet;
import com.saiyu.transactions.https.response.NewMsgRet;
import com.saiyu.transactions.ui.activitys.MainActivity;
import com.saiyu.transactions.ui.fragments.base.BaseFragment;
import com.saiyu.transactions.ui.views.DashlineItemDivider;
import com.saiyu.transactions.utils.CallbackUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DataScanFragment extends BaseFragment implements OnRefreshListener,OnLoadmoreListener,CallbackUtils.ResponseCallback {

    private Button btn_title_back;
    private TextView tv_title_content,tv_title_right;

    private SwipeMenuRecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private DataScanAdapter dataScanAdapter;
    private ProgressBar pb_loading;

    private List<DataScanBean> dataScanBeanList = new ArrayList<>();
    private boolean isFirst = true;

    public static DataScanFragment newInstance(Bundle bundle) {
        DataScanFragment fragment = new DataScanFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.common_list_layout, container, false);

        pb_loading = (ProgressBar)view.findViewById(R.id.pb_loading);
        pb_loading.setVisibility(View.VISIBLE);

        btn_title_back = (Button) view.findViewById(R.id.btn_title_back);
        tv_title_content = (TextView) view.findViewById(R.id.tv_title_content);
        tv_title_right = (TextView) view.findViewById(R.id.tv_title_right);

        recyclerView = (SwipeMenuRecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //分割线的颜色
        recyclerView.addItemDecoration(new DashlineItemDivider(20));


        dataScanAdapter = new DataScanAdapter(dataScanBeanList);
        recyclerView.setAdapter(dataScanAdapter);
        dataScanAdapter.notifyDataSetChanged();
        refreshLayout = (SmartRefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        refreshLayout.setEnableLoadmore(false);
//        refreshLayout.autoRefresh();

        btn_title_back.setVisibility(View.GONE);
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setText("退出登录");
        tv_title_content.setText("平台数据概览");

        tv_title_right.setOnClickListener(this);

        if (loadingReciver == null) {
            loadingReciver = new LoadingReciver();
            IntentFilter filter = new IntentFilter("sy_close_progressbar");
            mContext.registerReceiver(loadingReciver, filter);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (loadingReciver != null) {
            mContext.unregisterReceiver(loadingReciver);
            loadingReciver = null;
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        CallbackUtils.setCallback(DataScanFragment.this);
        ApiRequest.scanData("DataScanFragment_scanData",isFirst);
        isFirst = false;
    }

    @Override
    public void setOnResponseCallback(String method, BaseRet baseRet) {
        if(baseRet == null || TextUtils.isEmpty(method)){
            return;
        }
        if(method.equals("DataScanFragment_newMsg")){
            NewMsgRet ret = (NewMsgRet)baseRet;
            if(ret.getData() == null){
                return;
            }
            Message message = new Message();
            message.what = 2;
            message.obj = ret;
            handler.sendMessage(message);

        }else if(method.equals("DataScanFragment_scanData")){
            DataScanRet ret = (DataScanRet)baseRet;
            Message message = new Message();
            message.obj = ret;
            message.what = 1;
            handler.sendMessage(message);

        }

    }
    private void getData(List<DataScanBean> list,String key,String value){
        DataScanBean dataScanBean = new DataScanBean();
        dataScanBean.setmKey(key);
        dataScanBean.setmValue(value);
        list.add(dataScanBean);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    DataScanRet ret = (DataScanRet)msg.obj;
                    List<DataScanBean> list = new ArrayList<>();

                    getData(list,"今日注册用户",ret.getData().getRegUserTodayCount());
                    getData(list,"总注册用户",ret.getData().getRegUserCount());
                    getData(list,"激活买家",ret.getData().getActivatedBuyerCount());
                    getData(list,"激活卖家",ret.getData().getActivatedSellerCount());
                    getData(list,"寄售订单",ret.getData().getSuccessOrderCount());
                    getData(list,"寄售总量",ret.getData().getSuccessQBCount());
                    getData(list,"预定订单",ret.getData().getOrderCount());
                    getData(list,"预定总量",ret.getData().getOrderQBCount());
                    getData(list,"单日服务费",ret.getData().getServiceMoneyToDay());
                    getData(list,"当月服务费总计",ret.getData().getServiceMoneyMonth());

                    if(dataScanBeanList != null){
                        dataScanBeanList.clear();
                        dataScanBeanList.addAll(list);
                    }

                    dataScanAdapter = new DataScanAdapter(dataScanBeanList);
                    recyclerView.setAdapter(dataScanAdapter);
                    dataScanAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    NewMsgRet ret_2 = (NewMsgRet)msg.obj;
                    String buyerAuditCount = ret_2.getData().getBuyerAuditCount();
                    String sellerAuditCount = ret_2.getData().getSellerAuditCount();
                    String withdrawCount = ret_2.getData().getWithdrawCount();

                    try {
                        ((MainActivity) mContext).getBottomBar().getItem(1).setUnreadCount(Integer.parseInt(buyerAuditCount));
                    }catch (Exception e){

                    }
                    try {
                        ((MainActivity) mContext).getBottomBar().getItem(2).setUnreadCount(Integer.parseInt(sellerAuditCount));
                    }catch (Exception e){

                    }
                    try {
                        ((MainActivity) mContext).getBottomBar().getItem(3).setUnreadCount(Integer.parseInt(withdrawCount));
                    }catch (Exception e){

                    }

                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title_right:
                showdialog();
                break;
        }
    }

    private void showdialog() {
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:

                        ApiRequest.unLogin(mContext);

                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        break;
//                   case Dialog.BUTTON_NEUTRAL:
//                        Toast.makeText(context, "忽略" , Toast.LENGTH_SHORT).show();
//                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("退出当前账号:"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
        builder.create().show();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
//        ApiRequest.scanData("DataScanFragment_scanData",false);
        if (refreshLayout != null) {
            refreshlayout.finishLoadmore();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        CallbackUtils.setCallback(DataScanFragment.this);
        ApiRequest.newMsg("DataScanFragment_newMsg");
        ApiRequest.scanData("DataScanFragment_scanData",isFirst);
        if (refreshLayout != null) {
            refreshlayout.finishRefresh();
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
                    break;
            }
        }
    }

}
