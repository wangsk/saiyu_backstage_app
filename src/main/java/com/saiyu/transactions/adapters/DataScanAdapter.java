package com.saiyu.transactions.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saiyu.transactions.R;
import com.saiyu.transactions.https.response.DataScanBean;
import com.saiyu.transactions.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class DataScanAdapter extends RecyclerView.Adapter<DataScanAdapter.MyHolder>{

    List<DataScanBean> mItems = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public DataScanAdapter(List<DataScanBean> list){
        if(mItems != null){
            mItems.clear();
            mItems.addAll(list);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_datascan_layout, viewGroup, false);
        return new MyHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        if(mItems == null || mItems.size() <= i || mItems.get(i) == null){
            return;
        }
        myHolder.tv_datascan_1.setText(mItems.get(i).getmKey());
        myHolder.tv_datascan_2.setText(mItems.get(i).getmValue());

    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }


    public void refreshData(List<DataScanBean> list) {
        if (list != null) {
            mItems.clear();
            mItems.addAll(list);
            notifyDataSetChanged();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView tv_datascan_1,tv_datascan_2;
        public RelativeLayout rl_datascan_item;

        public MyHolder(View itemView) {
            super(itemView);
            rl_datascan_item = itemView.findViewById(R.id.rl_datascan_item);
            tv_datascan_1 = itemView.findViewById(R.id.tv_datascan_1);
            tv_datascan_2 = itemView.findViewById(R.id.tv_datascan_2);
        }

    }



}
