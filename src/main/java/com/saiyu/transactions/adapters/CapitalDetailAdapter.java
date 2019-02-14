package com.saiyu.transactions.adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saiyu.transactions.App;
import com.saiyu.transactions.R;
import com.saiyu.transactions.https.response.CapitalRet;
import com.saiyu.transactions.interfaces.OnItemClickListener;

import java.util.List;

public class CapitalDetailAdapter extends RecyclerView.Adapter<CapitalDetailAdapter.MyHolder>  {
    List<CapitalRet.DatasBean.ItemsBean> mItems;
    private OnItemClickListener mOnItemClickListener;
    private String orderNum;

    public CapitalDetailAdapter(List<CapitalRet.DatasBean.ItemsBean> list,String orderNum){
        this.mItems = list;
        this.orderNum = orderNum;
    }

    @NonNull
    @Override
    public CapitalDetailAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_capital_detail_layout, viewGroup, false);
        return new CapitalDetailAdapter.MyHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull CapitalDetailAdapter.MyHolder myHolder, final int i) {
        if(mItems == null || mItems.size() <= i || mItems.get(i) == null){
            return;
        }

        if(!TextUtils.isEmpty(mItems.get(i).getOrderNum())){
            myHolder.tv_deal_1.setText("相关单号:"+mItems.get(i).getOrderNum());
        } else {
            myHolder.tv_deal_1.setText("相关单号:");
        }

        myHolder.tv_deal_8.setText("项目类型:"+mItems.get(i).getBizNote());
        String time = mItems.get(i).getCreateTime();

        myHolder.tv_deal_3.setText("余额:" + mItems.get(i).getCurrentMoney() + "    " + mItems.get(i).getBizTypeStr());

        if("0".equals(mItems.get(i).getType())){
            String defaultCount = "<font color = \"#ff3300\">" +"+"+ mItems.get(i).getMoney() + "</font>";
            myHolder.tv_deal_4.setText(Html.fromHtml(defaultCount));
        } else if("1".equals(mItems.get(i).getType())){
            String defaultCount = "<font color = \"#31908f\">"+"-"+ mItems.get(i).getMoney() + "</font>";
            myHolder.tv_deal_4.setText(Html.fromHtml(defaultCount));
        }

        if(!TextUtils.isEmpty(time)){
            if(time.length() > 11){
                myHolder.tv_deal_2.setText(time.substring(0,11));
                myHolder.tv_deal_7.setText(time.substring(11));
            } else {
                myHolder.tv_deal_2.setText(time);
            }
        }

        if(orderNum.equals(mItems.get(i).getOrderNum())){
            ViewCompat.setBackground(myHolder.ll_datascan_item, ContextCompat.getDrawable(App.getApp(), R.color.pink));
            myHolder.tv_deal_1.setTextColor(App.getApp().getResources().getColor(R.color.white));
            myHolder.tv_deal_2.setTextColor(App.getApp().getResources().getColor(R.color.white));
            myHolder.tv_deal_3.setTextColor(App.getApp().getResources().getColor(R.color.white));
            myHolder.tv_deal_7.setTextColor(App.getApp().getResources().getColor(R.color.white));
            myHolder.tv_deal_8.setTextColor(App.getApp().getResources().getColor(R.color.white));

        } else {
            ViewCompat.setBackground(myHolder.ll_datascan_item, ContextCompat.getDrawable(App.getApp(), R.color.white));
            myHolder.tv_deal_1.setTextColor(App.getApp().getResources().getColor(R.color.list_text_lighter));
            myHolder.tv_deal_2.setTextColor(App.getApp().getResources().getColor(R.color.list_text_lighter));
            myHolder.tv_deal_3.setTextColor(App.getApp().getResources().getColor(R.color.list_text_deep));
            myHolder.tv_deal_7.setTextColor(App.getApp().getResources().getColor(R.color.list_text_lighter));
            myHolder.tv_deal_8.setTextColor(App.getApp().getResources().getColor(R.color.list_text_lighter));
        }

        myHolder.ll_datascan_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(v,i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public void refreshData(List<CapitalRet.DatasBean.ItemsBean> list) {
        if (list != null) {
            mItems.clear();
            mItems.addAll(list);
            notifyDataSetChanged();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView tv_deal_1,tv_deal_2,tv_deal_3,tv_deal_4,tv_deal_7,tv_deal_8;
        public LinearLayout ll_datascan_item;

        public MyHolder(View itemView) {
            super(itemView);
            ll_datascan_item = itemView.findViewById(R.id.ll_datascan_item);
            tv_deal_1 = itemView.findViewById(R.id.tv_deal_1);
            tv_deal_2 = itemView.findViewById(R.id.tv_deal_2);
            tv_deal_3 = itemView.findViewById(R.id.tv_deal_3);
            tv_deal_4 = itemView.findViewById(R.id.tv_deal_4);
            tv_deal_7 = itemView.findViewById(R.id.tv_deal_7);
            tv_deal_8 = itemView.findViewById(R.id.tv_deal_8);
        }

    }
}
