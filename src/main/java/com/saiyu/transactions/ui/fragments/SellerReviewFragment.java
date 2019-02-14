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
import com.saiyu.transactions.adapters.SellerTitleAdapter;
import com.saiyu.transactions.ui.fragments.base.BaseFragment;


public class SellerReviewFragment extends BaseFragment {

    private SellerTitleAdapter sellerTitleAdapter;
    private TabLayout layout_tab;
    ViewPager view_pager;

    public static SellerReviewFragment newInstance(Bundle bundle) {
        SellerReviewFragment fragment = new SellerReviewFragment();
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

        if (sellerTitleAdapter == null) {
            sellerTitleAdapter = new SellerTitleAdapter(getChildFragmentManager());
        }
        view_pager.setAdapter(sellerTitleAdapter);
        layout_tab.setupWithViewPager(view_pager);
        return view;
    }

}
