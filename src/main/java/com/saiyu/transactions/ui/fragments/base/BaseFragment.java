package com.saiyu.transactions.ui.fragments.base;

import android.app.Activity;
import android.view.View;

import me.yokeyword.fragmentation.SupportFragment;

public class BaseFragment extends SupportFragment implements View.OnClickListener {
    protected static Activity mContext;

    @Override
    public void onAttach(Activity context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        hideSoftInput();
    }

    @Override
    public void onClick(View v) {

    }
}
