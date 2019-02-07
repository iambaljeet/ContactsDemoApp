package com.app.contactsdemoapp.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.app.contactsdemoapp.R
import com.app.contactsdemoapp.adapters.FragmentPagerAdapter
import com.app.contactsdemoapp.fragments.ContactsFragment
import com.app.contactsdemoapp.fragments.MessagesFragment
import com.app.contactsdemoapp.models.ContactsModelDTO
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.top_toolbar_layout.*

class MainActivity : AppCompatActivity() {

//    Changing viewpager and Title when Bottom navigation option is changed
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.contacts_navigation -> {
                home_title_text_view.setText(R.string.contacts)
                viewpager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.messages_navigation -> {
                home_title_text_view.setText(R.string.messages)
                viewpager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        viewpager.adapter = FragmentPagerAdapter(supportFragmentManager)

//        Changing bottom navigation items when view pager is scrolled
        viewpager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    navigation.selectedItemId = R.id.contacts_navigation
                } else  {
                    navigation.selectedItemId = R.id.messages_navigation
                }
            }
        })
    }
}