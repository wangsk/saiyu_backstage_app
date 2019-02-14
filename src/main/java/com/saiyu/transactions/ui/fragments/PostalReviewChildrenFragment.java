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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostalReviewChildrenFragment extends BaseFragment implements CallbackUtils.ResponseCallback, OnItemClickListener, OnRefreshListener, OnLoadmoreListener {

    private SwipeMenuRecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private PostalAdapter postalAdapter;
    private RelativeLayout rl_title_bar;
    private int code = 0;
    private List<PostalRet.DatasBean.ItemsBean> mItems = new ArrayList<>();
    private ProgressBar pb_loading;
    private boolean isFirst = true;

    public static PostalReviewChildrenFragment newInstance(Bundle bundle) {
        PostalReviewChildrenFragment fragment = new PostalReviewChildrenFragment();
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

        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        pb_loading.setVisibility(View.VISIBLE);

        rl_title_bar = (RelativeLayout) view.findViewById(R.id.rl_title_bar);
        rl_title_bar.setVisibility(View.GONE);

        recyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //分割线的颜色
        recyclerView.addItemDecoration(new DashlineItemDivider(20));

        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
//        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        refreshLayout.setEnableLoadmore(false);

        postalAdapter = new PostalAdapter(mItems,false);
        postalAdapter.setOnItemClickListener(PostalReviewChildrenFragment.this);
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
        CallbackUtils.setCallback(PostalReviewChildrenFragment.this);
        if (getArguments() != null) {
            code = getArguments().getInt("position");
        }

        if (code == 0) {
            ApiRequest.postalList("PostalReviewChildrenFragment", isFirst);
        } else if (code == 1) {
            ApiRequest.postalList( "PostalReviewChildrenFragment", isFirst);
        }
        isFirst = false;
    }


    @Override
    public void setOnResponseCallback(String method, BaseRet baseRet) {
        if (baseRet == null || TextUtils.isEmpty(method)) {
            return;
        }
        if (method.equals("PostalReviewChildrenFragment")) {
            PostalRet ret = (PostalRet) baseRet;
            if (ret.getData() == null || ret.getData().getItems() == null) {
                return;
            }
            mItems.clear();
            mItems.addAll(ret.getData().getItems());

            handler.sendEmptyMessage(1);

        }else if(method.equals("PostalReviewChildrenFragment_newMsg")){
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

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(mContext,PostalPretrialActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("withdrawId",mItems.get(position).getWithdrawId());
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    postalAdapter = new PostalAdapter(mItems,false);
                    postalAdapter.setOnItemClickListener(PostalReviewChildrenFragment.this);
                    recyclerView.setAdapter(postalAdapter);
                    postalAdapter.notifyDataSetChanged();

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
    public void onLoadmore(RefreshLayout refreshlayout) {
        if (code == 0) {
            ApiRequest.postalList( "PostalReviewChildrenFragment", false);
        } else if (code == 1) {
            ApiRequest.postalList( "PostalReviewChildrenFragment", false);
        }
        if (refreshLayout != null) {
            refreshlayout.finishLoadmore();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        ApiRequest.newMsg("PostalReviewChildrenFragment_newMsg");
        if (code == 0) {
            ApiRequest.postalList( "PostalReviewChildrenFragment", false);
        } else if (code == 1) {
            ApiRequest.postalList( "PostalReviewChildrenFragment", false);
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
