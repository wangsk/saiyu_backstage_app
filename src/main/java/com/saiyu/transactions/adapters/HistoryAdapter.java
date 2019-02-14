package com.saiyu.transactions.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saiyu.transactions.R;
import com.saiyu.transactions.https.response.HistoryRet;
import com.saiyu.transactions.interfaces.OnItemClickListener;

import java.util.List;

public class HistoryAdapter  extends RecyclerView.Adapter<HistoryAdapter.MyHolder> {
    List<HistoryRet.DatasBean.ItemsBean> mItems;
    private OnItemClickListener mOnItemClickListener;

    public HistoryAdapter(List<HistoryRet.DatasBean.ItemsBean> list){
        this.mItems = list;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history_layout, viewGroup, false);
        return new HistoryAdapter.MyHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyHolder myHolder, final int i) {
        if(mItems == null || mItems.size() <= i || mItems.get(i) == null){
            return;
        }

        myHolder.tv_deal_1.setText("订单号:" + mItems.get(i).getOrderNum());
        String time = mItems.get(i).getAuditTime();
        if(!TextUtils.isEmpty(time)){
            if(time.length() > 11){
                myHolder.tv_deal_2.setText(time.substring(0,11));
                myHolder.tv_deal_7.setText(time.substring(11));
            } else {
                myHolder.tv_deal_2.setText(time);
            }
        }
        if("0".equals(mItems.get(i).getAuditType())){
            myHolder.tv_deal_3.setText("买家发布预审/" + mItems.get(i).getOrderMoney()+"元");
        } else if("1".equals(mItems.get(i).getAuditType())){
            myHolder.tv_deal_3.setText("卖家出售预审/" + mItems.get(i).getOrderMoney()+"元");
        } else if("2".equals(mItems.get(i).getAuditType())){
            myHolder.tv_deal_3.setText("客服代理确认/" + mItems.get(i).getOrderMoney()+"元");
        } else if("3".equals(mItems.get(i).getAuditType())){
            myHolder.tv_deal_3.setText("客服验图确认/" + mItems.get(i).getOrderMoney()+"元");
        } else if("4".equals(mItems.get(i).getAuditType())){
            myHolder.tv_deal_3.setText("代理确认/" + mItems.get(i).getOrderMoney()+"元");
        }

        myHolder.tv_deal_4.setText(mItems.get(i).getOperateName()+ "/" + mItems.get(i).getAuditStatusStr());
        myHolder.tv_deal_5.setText("卖家" + mItems.get(i).getSellerAccount());
        myHolder.tv_deal_6.setText("买家" + mItems.get(i).getBuyerAccount());



    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public void refreshData(List<HistoryRet.DatasBean.ItemsBean> list) {
        if (list != null) {
            mItems.clear();
            mItems.addAll(list);
            notifyDataSetChanged();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView tv_deal_1,tv_deal_2,tv_deal_3,tv_deal_4,tv_deal_5,tv_deal_6,tv_deal_7;
        public LinearLayout ll_datascan_item;

        public MyHolder(View itemView) {
            super(itemView);
            ll_datascan_item = itemView.findViewById(R.id.ll_datascan_item);
            tv_deal_1 = itemView.findViewById(R.id.tv_deal_1);
            tv_deal_2 = itemView.findViewById(R.id.tv_deal_2);
            tv_deal_3 = itemView.findViewById(R.id.tv_deal_3);
            tv_deal_4 = itemView.findViewById(R.id.tv_deal_4);
            tv_deal_5 = itemView.findViewById(R.id.tv_deal_5);
            tv_deal_6 = itemView.findViewById(R.id.tv_deal_6);
            tv_deal_7 = itemView.findViewById(R.id.tv_deal_7);
        }

    }
}
