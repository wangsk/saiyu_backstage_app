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
import com.saiyu.transactions.adapters.PostalReviewTitleAdapter;
import com.saiyu.transactions.ui.fragments.base.BaseFragment;

public class PostalReviewFragment extends BaseFragment{

    private PostalReviewTitleAdapter postalReviewTitleAdapter;
    private TabLayout layout_tab;
    ViewPager view_pager;

    public static PostalReviewFragment newInstance(Bundle bundle) {
        PostalReviewFragment fragment = new PostalReviewFragment();
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

        if (postalReviewTitleAdapter == null) {
            postalReviewTitleAdapter = new PostalReviewTitleAdapter(getChildFragmentManager());
        }
        view_pager.setAdapter(postalReviewTitleAdapter);
        layout_tab.setupWithViewPager(view_pager);

        return view;
    }

}
