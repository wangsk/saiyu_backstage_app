package com.saiyu.transactions.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.saiyu.transactions.R;
import com.saiyu.transactions.https.ApiRequest;
import com.saiyu.transactions.https.response.BaseRet;
import com.saiyu.transactions.https.response.NewMsgRet;
import com.saiyu.transactions.ui.activitys.base.BaseActivity;
import com.saiyu.transactions.ui.fragments.BuyerReviewFragment;
import com.saiyu.transactions.ui.fragments.DataScanFragment;
import com.saiyu.transactions.ui.fragments.PostalReviewFragment;
import com.saiyu.transactions.ui.fragments.HistoryReviewFragment;
import com.saiyu.transactions.ui.fragments.SellerReviewFragment;
import com.saiyu.transactions.ui.views.BottomBar;
import com.saiyu.transactions.ui.views.BottomBarTab;
import com.saiyu.transactions.utils.CallbackUtils;

import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements BottomBar.OnTabSelectedListener,CallbackUtils.ResponseCallback{

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public static final int FIVE = 4;

    private BottomBar bottomBar;
    private SupportFragment[] mFragments = new SupportFragment[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        SupportFragment firstFragment = findFragment(DataScanFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = DataScanFragment.newInstance(null);
            mFragments[SECOND] = BuyerReviewFragment.newInstance(null);
            mFragments[THIRD] = SellerReviewFragment.newInstance(null);
            mFragments[FOURTH] = PostalReviewFragment.newInstance(null);
            mFragments[FIVE] = HistoryReviewFragment.newInstance(null);
            loadMultipleRootFragment(R.id.main_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH],
                    mFragments[FIVE]);

        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(BuyerReviewFragment.class);
            mFragments[THIRD] = findFragment(SellerReviewFragment.class);
            mFragments[FOURTH] = findFragment(PostalReviewFragment.class);
            mFragments[FIVE] = findFragment(HistoryReviewFragment.class);
        }


        bottomBar = (BottomBar)findViewById(R.id.bottomBar);
        bottomBar.addItem(new BottomBarTab(this, R.mipmap.data, "数据概览"))
                .addItem(new BottomBarTab(this, R.mipmap.buyer, "买家审核"))
                .addItem(new BottomBarTab(this, R.mipmap.seller, "卖家审核"))
                .addItem(new BottomBarTab(this, R.mipmap.postal, "提现审核"))
                .addItem(new BottomBarTab(this, R.mipmap.history, "审核历史"));
        bottomBar.setOnTabSelectedListener(this);

        CallbackUtils.setCallback(this);
        ApiRequest.newMsg("DataScanFragment_newMsg");

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

        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2:
                    NewMsgRet ret_2 = (NewMsgRet)msg.obj;
                    String buyerAuditCount = ret_2.getData().getBuyerAuditCount();
                    String sellerAuditCount = ret_2.getData().getSellerAuditCount();
                    String withdrawCount = ret_2.getData().getWithdrawCount();

                    try {
                        setUnread(1,Integer.parseInt(buyerAuditCount));
                        ((MainActivity) mContext).getBottomBar().getItem(1).setUnreadCount(Integer.parseInt(buyerAuditCount));
                    }catch (Exception e){

                    }
                    try {
                        setUnread(2,Integer.parseInt(sellerAuditCount));
                        ((MainActivity) mContext).getBottomBar().getItem(2).setUnreadCount(Integer.parseInt(sellerAuditCount));
                    }catch (Exception e){

                    }
                    try {
                        setUnread(3,Integer.parseInt(withdrawCount));
                        ((MainActivity) mContext).getBottomBar().getItem(3).setUnreadCount(Integer.parseInt(withdrawCount));
                    }catch (Exception e){

                    }

                    break;
            }
        }
    };

    public void setUnread(int tab, int num) {
        bottomBar.getItem(tab).setUnreadCount(num);
    }

    public BottomBar getBottomBar() {
        return bottomBar;
    }

    @Override
    public void onTabSelected(int position, int prePosition) {
        showHideFragment(mFragments[position], mFragments[prePosition]);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onBackPressedSupport() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}
