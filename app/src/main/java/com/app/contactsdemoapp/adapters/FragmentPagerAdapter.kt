package com.app.contactsdemoapp.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.app.contactsdemoapp.fragments.ContactsFragment
import com.app.contactsdemoapp.fragments.MessagesFragment

class FragmentPagerAdapter(
    fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    // Called when viewpager is scrolled, to change fragments
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment
        if (position == 0) {
            fragment = ContactsFragment.newInstance()
        } else {
            fragment = MessagesFragment.newInstance()
        }
        return fragment
    }

    override fun getCount(): Int = 2
}