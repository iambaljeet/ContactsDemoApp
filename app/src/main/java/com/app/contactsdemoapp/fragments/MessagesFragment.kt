package com.app.contactsdemoapp.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.contactsdemoapp.R
import com.app.contactsdemoapp.adapters.MessagesRecyclerViewAdapter
import com.app.contactsdemoapp.database.MessagesDao
import com.app.contactsdemoapp.database.MessagesDatabase
import com.app.contactsdemoapp.models.ContactsModelDTO
import com.app.contactsdemoapp.models.MessagesModel
import kotlinx.android.synthetic.main.fragment_messages_layout.*

class MessagesFragment : Fragment() {
    private var messagesRecyclerViewAdapter: MessagesRecyclerViewAdapter? = null

    var messagesDatabase: MessagesDatabase? = null
    var messagesDao: MessagesDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_messages_layout, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()

        getMessages()
    }

    fun getMessages() {
        messagesDatabase = context?.let { MessagesDatabase.getDatabase(it) }

        if (messagesDatabase != null) {
            messagesDao = messagesDatabase?.messagesDao()

            messagesDao?.getAllMessages()?.observe(this, Observer { t -> setData(t) })
        }
    }

    private fun setData(list: List<MessagesModel>?) {
        if (list != null && list.isNotEmpty()) {
            no_data_text_view.visibility = View.GONE
            messages_recycler_view.visibility = View.VISIBLE

            messagesRecyclerViewAdapter = MessagesRecyclerViewAdapter(list, this)
            messages_recycler_view.layoutManager = LinearLayoutManager(context)
            messages_recycler_view.adapter = messagesRecyclerViewAdapter
        } else {
            no_data_text_view.visibility = View.VISIBLE
            messages_recycler_view.visibility = View.GONE
        }
    }

    companion object {
//        Create & returns instance of fragment
        fun newInstance(): Fragment {
            var fragment: Fragment? = null
            if (fragment == null) {
                fragment = MessagesFragment()
            }
            return fragment
        }
    }
}