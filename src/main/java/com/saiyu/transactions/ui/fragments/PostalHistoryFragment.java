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
import android.widget.Toast;

import com.saiyu.transactions.R;
import com.saiyu.transactions.adapters.PostalAdapter;
import com.saiyu.transactions.https.ApiRequest;
import com.saiyu.transactions.https.response.BaseRet;
import com.saiyu.transactions.https.response.NewMsgRet;
import com.saiyu.transactions.https.response.PostalRet;
import com.saiyu.transactions.interfaces.OnItemClickListener;
import com.saiyu.transactions.ui.activitys.MainActivity;
import com.saiyu.transactions.ui.activitys.PostalPretrialActivity;
import com.saiyu.transactions.ui.fragments.base.BaseFragment;
import com.saiyu.transactions.ui.views.DashlineItemDivider;
import com.saiyu.transactions.utils.CallbackUtils;
import com.saiyu.transactions.utils.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostalHistoryFragment extends BaseFragment implements OnRefreshListener,OnLoadmoreListener,CallbackUtils.ResponseCallback, OnItemClickListener {

    private RelativeLayout rl_title_bar;

    private SwipeMenuRecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private PostalAdapter postalAdapter;
    private List<PostalRet.DatasBean.ItemsBean> mItems = new ArrayList<>();
    private int page = 1;
    private int pageSize = 30;
    private int totalPage;
    private ProgressBar pb_loading;
    private boolean isFirst = true;

    public static PostalHistoryFragment newInstance(Bundle bundle) {
        PostalHistoryFragment fragment = new PostalHistoryFragment();
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

        rl_title_bar = (RelativeLayout) view.findViewById(R.id.rl_title_bar);
        rl_title_bar.setVisibility(View.GONE);

        recyclerView = (SwipeMenuRecyclerView)view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //分割线的颜色
        recyclerView.addItemDecoration(new DashlineItemDivider(20));

        refreshLayout = (SmartRefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
//        refreshLayout.autoRefresh();

        postalAdapter = new PostalAdapter(mItems,true);
        postalAdapter.setOnItemClickListener(PostalHistoryFragment.this);
        recyclerView.setAdapter(postalAdapter);
        postalAdapter.notifyDataSetChanged();

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
        CallbackUtils.setCallback(PostalHistoryFragment.this);
        ApiRequest.postalHistoryList(page + "",pageSize + "","PostalHistoryFragment",isFirst);
        isFirst = false;
    }

    @Override
    public void setOnResponseCallback(String method, BaseRet baseRet) {
        if(baseRet == null || TextUtils.isEmpty(method)){
            return;
        }
        if(method.equals("PostalHistoryFragment")){
            PostalRet ret = (PostalRet)baseRet;
            if(ret.getData() == null){
                return;
            }
            if(ret.getData().getItems() == null){
                return;
            }
            int totalCount = ret.getData().getCount();

            totalPage = totalCount/pageSize + 1;
            LogUtils.print("历史总记录:" + totalCount + " ;总页码：" + totalPage);

            mItems.clear();
            mItems.addAll(ret.getData().getItems());

            handler.sendEmptyMessage(1);

        }else if(method.equals("DataScanFragment_newMsg")){
            NewMsgRet ret = (NewMsgRet)baseRet;
            if(ret.getData() == null){
                return;
            }
            String buyerAuditCount = ret.getData().getBuyerAuditCount();
            String sellerAuditCount = ret.getData().getSellerAuditCount();
            String withdrawCount = ret.getData().getWithdrawCount();
            if(!"0".equals(buyerAuditCount)){
                try {
                    ((MainActivity) mContext).getBottomBar().getItem(1).setUnreadCount(Integer.parseInt(buyerAuditCount));
                }catch (Exception e){

                }
            }
            if(!"0".equals(sellerAuditCount)){
                try {
                    ((MainActivity) mContext).getBottomBar().getItem(2).setUnreadCount(Integer.parseInt(sellerAuditCount));
                }catch (Exception e){

                }
            }
            if(!"0".equals(withdrawCount)){
                try {
                    ((MainActivity) mContext).getBottomBar().getItem(3).setUnreadCount(Integer.parseInt(withdrawCount));
                }catch (Exception e){

                }
            }

        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(mContext,PostalPretrialActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("withdrawId",mItems.get(position).getWithdrawId());
        bundle.putBoolean("flag",true);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    postalAdapter = new PostalAdapter(mItems,true);
                    postalAdapter.setOnItemClickListener(PostalHistoryFragment.this);
                    recyclerView.setAdapter(postalAdapter);
                    postalAdapter.notifyDataSetChanged();

                    break;
            }
        }
    };

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

        if(page > 1){
            page--;
            ApiRequest.postalHistoryList(page + "",pageSize + "","PostalHistoryFragment",false);
        } else {
            Toast.makeText(mContext,"已经是第一页",Toast.LENGTH_SHORT).show();
        }
        if (refreshLayout != null) {
            refreshlayout.finishRefresh();
        }

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

        if(page < totalPage){
            page++;
            ApiRequest.postalHistoryList(page + "",pageSize + "","PostalHistoryFragment",false);
        } else {
            Toast.makeText(mContext,"已经是最后一页",Toast.LENGTH_SHORT).show();
        }
        if (refreshLayout != null) {
            refreshlayout.finishLoadmore();
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
