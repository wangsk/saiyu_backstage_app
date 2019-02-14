package com.saiyu.transactions.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saiyu.transactions.R;
import com.saiyu.transactions.https.response.BuyerRet;
import com.saiyu.transactions.interfaces.OnItemClickListener;

import java.util.List;

public class SellerAdapter  extends RecyclerView.Adapter<SellerAdapter.MyHolder> {
    List<BuyerRet.DatasBean> mItems;
    private OnItemClickListener mOnItemClickListener;

    public SellerAdapter(List<BuyerRet.DatasBean> list){
        if(list == null){
            return;
        }
        this.mItems = list;
    }

    @NonNull
    @Override
    public SellerAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_deal_layout, viewGroup, false);
        return new SellerAdapter.MyHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull SellerAdapter.MyHolder myHolder, final int i) {
        if(mItems == null || mItems.size() <= i || mItems.get(i) == null){
            return;
        }
        myHolder.tv_deal_1.setText("订单号:"+mItems.get(i).getOrderNum());
        myHolder.tv_deal_2.setText(mItems.get(i).getProductName());
        myHolder.tv_deal_3.setText(mItems.get(i).getSellerAccount());
        myHolder.tv_deal_4.setText(mItems.get(i).getOrderQBCount()+" Q币/" + mItems.get(i).getDiscount()+"折");

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

    public void refreshData(List<BuyerRet.DatasBean> list) {
        if (list != null) {
            mItems.clear();
            mItems.addAll(list);
            notifyDataSetChanged();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView tv_deal_1,tv_deal_2,tv_deal_3,tv_deal_4;
        public LinearLayout ll_datascan_item;

        public MyHolder(View itemView) {
            super(itemView);
            ll_datascan_item = itemView.findViewById(R.id.ll_datascan_item);
            tv_deal_1 = itemView.findViewById(R.id.tv_deal_1);
            tv_deal_2 = itemView.findViewById(R.id.tv_deal_2);
            tv_deal_3 = itemView.findViewById(R.id.tv_deal_3);
            tv_deal_4 = itemView.findViewById(R.id.tv_deal_4);
        }

    }
}
