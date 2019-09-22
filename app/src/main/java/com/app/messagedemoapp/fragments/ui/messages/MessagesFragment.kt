package com.app.messagedemoapp.fragments.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.messagedemoapp.R
import com.app.messagedemoapp.adapters.MessagesRecyclerViewAdapter
import com.app.messagedemoapp.models.MessagesModel
import com.app.messagedemoapp.utility.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_messages_layout.*

class MessagesFragment : Fragment() {
    private var messagesRecyclerViewAdapter: MessagesRecyclerViewAdapter? = null


    lateinit var messagesViewModel: MessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_messages_layout, container, false)
        messagesViewModel = ViewModelProviders.of(this, CustomViewModelFactory()).get(
            MessagesViewModel::class.java
        )
        return view
    }

    override fun onStart() {
        super.onStart()

        getMessages()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        messagesRecyclerViewAdapter = MessagesRecyclerViewAdapter()
        messages_recycler_view.layoutManager =
            LinearLayoutManager(context)
        messages_recycler_view.adapter = messagesRecyclerViewAdapter
        messages_recycler_view.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun getMessages() {
        messagesViewModel.getAllMessages()
            ?.observe(this, Observer { messages -> setData(messages) })
    }

    private fun setData(messagesList: List<MessagesModel>?) {
        if (messagesList != null && messagesList.isNotEmpty()) {
            no_data_text_view.visibility = View.GONE
            messages_recycler_view.visibility = View.VISIBLE

            messagesRecyclerViewAdapter?.newDataInserted(messagesList)
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