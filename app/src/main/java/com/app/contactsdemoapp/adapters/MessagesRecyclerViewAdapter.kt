package com.app.contactsdemoapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.contactsdemoapp.R
import com.app.contactsdemoapp.fragments.MessagesFragment
import com.app.contactsdemoapp.models.MessagesModel

import kotlinx.android.synthetic.main.messages_list_item.view.*

class MessagesRecyclerViewAdapter(
    private var messagesList: List<MessagesModel>?,
    private val messagesFragment: MessagesFragment
) : RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.messages_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = messagesList?.get(position)
        holder.item_user_name.text = item?.user_name
        holder.item_user_number.text = item?.user_number
        holder.item_message.text = item?.message_text
        holder.item_date.text = item?.date
    }

    override fun getItemCount(): Int = messagesList?.size ?: 0

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val item_user_name: TextView = view.item_user_name
        val item_user_number: TextView = view.item_user_number
        val item_message: TextView = view.item_message
        val item_date: TextView = view.item_date
    }
}