package com.app.messagedemoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.app.messagedemoapp.R
import com.app.messagedemoapp.models.MessagesModel
import com.app.messagedemoapp.utility.Utility
import kotlinx.android.synthetic.main.messages_list_item.view.*

class MessagesRecyclerViewAdapter : RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder>() {

    private var messagesList: List<MessagesModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.messages_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = messagesList[position]
        setData(holder, item)
    }

    private fun setData(
        holder: ViewHolder,
        item: MessagesModel
    ) {
        holder.itemUserName.text = item.user_name
        holder.itemUserNumber.text = item.user_number
        holder.itemMessage.text = item.message_text
        holder.itemDate.text = item.date_millis.let { Utility.dateFormatter(it) }
    }

    fun newDataInserted(messages: List<MessagesModel>) {
        this.messagesList = messages
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = messagesList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemUserName: AppCompatTextView = view.item_user_name
        val itemUserNumber: AppCompatTextView = view.item_user_number
        val itemMessage: AppCompatTextView = view.item_message
        val itemDate: AppCompatTextView = view.item_date
    }
}