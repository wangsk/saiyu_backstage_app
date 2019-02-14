package com.saiyu.transactions.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.saiyu.transactions.ui.fragments.PostalHistoryFragment;
import com.saiyu.transactions.ui.fragments.PostalReviewChildrenFragment;

import java.util.ArrayList;
import java.util.List;

public class PostalReviewTitleAdapter extends FragmentStatePagerAdapter {
    private FragmentManager fm;
    private List<String> tablist;


    public PostalReviewTitleAdapter(FragmentManager fm){
        super(fm);
        this.fm = fm;
        tablist = new ArrayList<>();
        tablist.add("提现受理");
        tablist.add("提现历史");
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if(i == 0){
            fragment = PostalReviewChildrenFragment.newInstance(null);
        } else if(i == 1){
            fragment = PostalHistoryFragment.newInstance(null);
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tablist.get(position);
    }

    @Override
    public int getCount() {
        if (tablist != null && tablist.size() > 0) {
            return tablist.size();
        } else {
            return 0;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }


    /**
     * 在调用notifyDataSetChanged()方法后，随之会触发该方法，根据该方法返回的值来确定是否更新
     * object对象为Fragment，具体是当前显示的Fragment和它的前一个以及后一个
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;   // 返回发生改变，让系统重新加载
        // 系统默认返回的是     POSITION_UNCHANGED，未改变
        // return super.getItemPosition(object);
    }
}
