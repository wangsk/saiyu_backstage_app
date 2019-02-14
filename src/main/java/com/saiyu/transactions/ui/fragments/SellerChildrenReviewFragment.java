package com.saiyu.transactions.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.saiyu.transactions.R;
import com.saiyu.transactions.adapters.SellerAdapter;
import com.saiyu.transactions.https.ApiRequest;
import com.saiyu.transactions.https.response.BaseRet;
import com.saiyu.transactions.https.response.BuyerRet;
import com.saiyu.transactions.https.response.NewMsgRet;
import com.saiyu.transactions.interfaces.OnItemClickListener;
import com.saiyu.transactions.ui.activitys.MainActivity;
import com.saiyu.transactions.ui.activitys.SellerPicActivity;
import com.saiyu.transactions.ui.activitys.SellerPretrialActivity;
import com.saiyu.transactions.ui.activitys.SellerReplacePretrialActivity;
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

public class SellerChildrenReviewFragment extends BaseFragment implements OnRefreshListener,OnLoadmoreListener,CallbackUtils.ResponseCallback, OnItemClickListener{

    private SwipeMenuRecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private SellerAdapter sellerAdapter;
    private RelativeLayout rl_title_bar;
    private int code = 0;
    private List<BuyerRet.DatasBean> mItems = new ArrayList<>();
    private ProgressBar pb_loading;
    private boolean isFirst = true;

    public static SellerChildrenReviewFragment newInstance(Bundle bundle) {
        SellerChildrenReviewFragment fragment = new SellerChildrenReviewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        if(getArguments() != null){
            code = getArguments().getInt("position");
        }

        View view = inflater.inflate(R.layout.common_list_layout, container, false);

        pb_loading = (ProgressBar)view.findViewById(R.id.pb_loading);
        pb_loading.setVisibility(View.VISIBLE);

        rl_title_bar = (RelativeLayout)view.findViewById(R.id.rl_title_bar);
        rl_title_bar.setVisibility(View.GONE);

        recyclerView = (SwipeMenuRecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //分割线的颜色
        recyclerView.addItemDecoration(new DashlineItemDivider(20));

        refreshLayout = (SmartRefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        refreshLayout.setEnableLoadmore(false);
//        refreshLayout.autoRefresh();

        sellerAdapter = new SellerAdapter(mItems);
        sellerAdapter.setOnItemClickListener(SellerChildrenReviewFragment.this);
        recyclerView.setAdapter(sellerAdapter);
        sellerAdapter.notifyDataSetChanged();

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
        CallbackUtils.setCallback(SellerChildrenReviewFragment.this);
        if(getArguments() != null){
            code = getArguments().getInt("position");
        }

        if(code == 0){
            ApiRequest.waitingReview("1","SellerChildrenReviewFragment",isFirst);
        } else if(code == 1){
            ApiRequest.waitingReview("2","SellerChildrenReviewFragment",isFirst);
        } else if(code == 2){
            ApiRequest.waitingReview("3","SellerChildrenReviewFragment",isFirst);
        }
        isFirst = false;
    }

    @Override
    public void setOnResponseCallback(String method, BaseRet baseRet) {
        if(baseRet == null || TextUtils.isEmpty(method)){
            return;
        }
        if(method.equals("SellerChildrenReviewFragment")){
            BuyerRet ret = (BuyerRet)baseRet;
            if(ret.getDatas() == null){
                return;
            }
            mItems.clear();
            mItems.addAll(ret.getDatas());

            handler.sendEmptyMessage(1);

        }else if(method.equals("SellerChildrenReviewFragment_newMsg")){
            NewMsgRet ret = (NewMsgRet)baseRet;
            if(ret.getData() == null){
                return;
            }

            Message message = new Message();
            message.what = 2;
            message.obj = ret;
            handler.sendMessage(message);

        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    sellerAdapter.setOnItemClickListener(SellerChildrenReviewFragment.this);
                    recyclerView.setAdapter(sellerAdapter);
                    sellerAdapter.notifyDataSetChanged();

                    break;
                case 2:
                    NewMsgRet ret = (NewMsgRet)msg.obj;
                    String buyerAuditCount = ret.getData().getBuyerAuditCount();
                    String sellerAuditCount = ret.getData().getSellerAuditCount();
                    String withdrawCount = ret.getData().getWithdrawCount();

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
    public void onItemClick(View view, int position) {
        if(code == 0){
            Intent intent = new Intent(mContext,SellerPretrialActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("auditId",mItems.get(position).getAuditId());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        } else if(code == 1){
            Intent intent = new Intent(mContext,SellerReplacePretrialActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("auditId",mItems.get(position).getAuditId());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        } else if(code == 2){
            Intent intent = new Intent(mContext,SellerPicActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("auditId",mItems.get(position).getAuditId());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        if(code == 0){
            ApiRequest.waitingReview("1","SellerChildrenReviewFragment",false);
        } else if(code == 1){
            ApiRequest.waitingReview("2","SellerChildrenReviewFragment",false);
        } else if(code == 2){
            ApiRequest.waitingReview("3","SellerChildrenReviewFragment",false);
        }
        if (refreshLayout != null) {
            refreshlayout.finishLoadmore();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        ApiRequest.newMsg("SellerChildrenReviewFragment_newMsg");
        if(code == 0){
            ApiRequest.waitingReview("1","SellerChildrenReviewFragment",false);
        } else if(code == 1){
            ApiRequest.waitingReview("2","SellerChildrenReviewFragment",false);
        } else if(code == 2){
            ApiRequest.waitingReview("3","SellerChildrenReviewFragment",false);
        }
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
