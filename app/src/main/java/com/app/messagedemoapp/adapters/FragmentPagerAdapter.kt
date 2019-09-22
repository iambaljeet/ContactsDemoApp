package com.app.messagedemoapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.app.messagedemoapp.fragments.ui.contacts.ContactsFragment
import com.app.messagedemoapp.fragments.ui.messages.MessagesFragment

class FragmentPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // Called when viewpager is scrolled, to change fragments
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            ContactsFragment.newInstance()
        } else {
            MessagesFragment.newInstance()
        }
    }

    override fun getCount(): Int = 2
}