package com.saiyu.transactions.ui.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.saiyu.transactions.R;
import com.saiyu.transactions.adapters.CapitalDetailAdapter;
import com.saiyu.transactions.https.ApiRequest;
import com.saiyu.transactions.https.response.BaseRet;
import com.saiyu.transactions.https.response.CapitalRet;
import com.saiyu.transactions.interfaces.OnItemClickListener;
import com.saiyu.transactions.ui.activitys.base.BaseActivity;
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

public class CapitalDetailActivity extends BaseActivity implements OnRefreshListener,OnLoadmoreListener,CallbackUtils.ResponseCallback, OnItemClickListener{

    private Button btn_title_back;
    private TextView tv_title_content,tv_doback;

    private SwipeMenuRecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private CapitalDetailAdapter capitalDetailAdapter;
    private List<CapitalRet.DatasBean.ItemsBean> mItems = new ArrayList<>();
    private int page = 1;
    private int pageSize = 30;
    private int totalPage;
    private ProgressBar pb_loading;
    private String account;
    private String orderNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list_layout);

        InitPopWindow();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            account = bundle.getString("account");
            orderNum = bundle.getString("orderNum");
        }

        pb_loading = (ProgressBar)findViewById(R.id.pb_loading);
        pb_loading.setVisibility(View.VISIBLE);

        btn_title_back = (Button) findViewById(R.id.btn_title_back);
        btn_title_back.setOnClickListener(this);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_doback = (TextView) findViewById(R.id.tv_doback);
        tv_doback.setOnClickListener(this);
        tv_doback.setVisibility(View.VISIBLE);

        recyclerView = (SwipeMenuRecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //分割线的颜色
        recyclerView.addItemDecoration(new DashlineItemDivider(20));

        refreshLayout = (SmartRefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
//        refreshLayout.autoRefresh();

        tv_title_content.setText("资金明细");

        capitalDetailAdapter = new CapitalDetailAdapter(mItems,orderNum);
        capitalDetailAdapter.setOnItemClickListener(CapitalDetailActivity.this);
        recyclerView.setAdapter(capitalDetailAdapter);
        capitalDetailAdapter.notifyDataSetChanged();

        if (loadingReciver == null) {
            loadingReciver = new LoadingReciver();
            IntentFilter filter = new IntentFilter("sy_close_progressbar");
            mContext.registerReceiver(loadingReciver, filter);
        }

        CallbackUtils.setCallback(this);
        if (!TextUtils.isEmpty(account)) {

            ApiRequest.capitalList(account,page + "",pageSize + "","CapitalDetailActivity",true);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadingReciver != null) {
            mContext.unregisterReceiver(loadingReciver);
            loadingReciver = null;
        }
    }

    @Override
    public void setOnResponseCallback(String method, BaseRet baseRet) {
        if(baseRet == null || TextUtils.isEmpty(method)){
            return;
        }
        if(method.equals("CapitalDetailActivity")){
            CapitalRet ret = (CapitalRet)baseRet;
            if(ret.getData() == null || ret.getData().getItems() == null){
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



        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    capitalDetailAdapter = new CapitalDetailAdapter(mItems,orderNum);
                    capitalDetailAdapter.setOnItemClickListener(CapitalDetailActivity.this);
                    recyclerView.setAdapter(capitalDetailAdapter);
                    capitalDetailAdapter.notifyDataSetChanged();
                    try{
                        for(int i = 0; i < mItems.size(); i++){
                            if(orderNum.equals(mItems.get(i).getOrderNum())){
                                recyclerView.scrollToPosition(i);
                                break;
                            }
                        }
                    }catch (Exception e){

                    }


                    break;
            }
        }
    };

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        if(page > 1){
            page--;
            ApiRequest.capitalList(account,page + "",pageSize + "","CapitalDetailActivity",false);
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
            ApiRequest.capitalList(account,page + "",pageSize + "","CapitalDetailActivity",false);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.tv_doback || v.getId() == R.id.btn_title_back){
            onBackPressedSupport();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        ShowPopWindow(mItems.get(position));
    }


    private PopupWindow mPopupWindow;

    private View mPopView;
    private TextView tv_pop_type,tv_pop_class,tv_pop_person,tv_pop_order;

    private void InitPopWindow() {
        // TODO Auto-generated method stub
        // 将布局文件转换成View对象，popupview 内容视图
        mPopView = getLayoutInflater().inflate(R.layout.popwindow_layout, null);
        // 将转换的View放置到 新建一个popuwindow对象中
        mPopupWindow = new PopupWindow(mPopView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // 点击popuwindow外让其消失
//        mPopupWindow.setOutsideTouchable(true);
        // mpopupWindow.setBackgroundDrawable(background);
        LinearLayout ll_pop = (LinearLayout)mPopView.findViewById(R.id.ll_pop);
        tv_pop_type = (TextView)mPopView.findViewById(R.id.tv_pop_type);
        tv_pop_class = (TextView)mPopView.findViewById(R.id.tv_pop_class);
        tv_pop_person = (TextView)mPopView.findViewById(R.id.tv_pop_person);
        tv_pop_order = (TextView)mPopView.findViewById(R.id.tv_pop_order);
        ll_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
            }
        });

    }

    public void ShowPopWindow(CapitalRet.DatasBean.ItemsBean bean) {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            // 设置PopupWindow 显示的形式 底部或者下拉等
            // 在某个位置显示
            mPopupWindow.showAtLocation(mPopView, Gravity.BOTTOM, 0, 30);
            // 作为下拉视图显示
            // mPopupWindow.showAsDropDown(mPopView, Gravity.CENTER, 200, 300);
            tv_pop_type.setText(bean.getBizTypeStr());
            tv_pop_class.setText(bean.getBizNote());
            tv_pop_person.setText(bean.getOperateName());
            tv_pop_order.setText(bean.getOrderNum());
        }

    }

}
