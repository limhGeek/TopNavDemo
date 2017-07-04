package com.limh.readers.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Function:
 * author: limh
 * time:2017/7/3
 */
class FragmentAdapter(fm: FragmentManager?, var mFragments: MutableList<Fragment>) : FragmentPagerAdapter(fm) {


    override fun getItem(p0: Int): Fragment {
        return mFragments.get(p0)
    }

    override fun getCount(): Int {
        return mFragments.size
    }

}