package com.saiyu.transactions.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saiyu.transactions.R;

import com.saiyu.transactions.adapters.BuyerTitleAdapter;
import com.saiyu.transactions.ui.fragments.base.BaseFragment;


public class BuyerReviewFragment extends BaseFragment {


    private BuyerTitleAdapter buyerTitleAdapter;
    private TabLayout layout_tab;
    ViewPager view_pager;

    public static BuyerReviewFragment newInstance(Bundle bundle) {
        BuyerReviewFragment fragment = new BuyerReviewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.common_list_fragment_layout, container, false);

        layout_tab = (TabLayout)view.findViewById(R.id.layout_tab);
        view_pager = (ViewPager)view.findViewById(R.id.view_pager);

        if (buyerTitleAdapter == null) {
            buyerTitleAdapter = new BuyerTitleAdapter(getChildFragmentManager());
        }
        view_pager.setAdapter(buyerTitleAdapter);
        layout_tab.setupWithViewPager(view_pager);

        return view;
    }

}
