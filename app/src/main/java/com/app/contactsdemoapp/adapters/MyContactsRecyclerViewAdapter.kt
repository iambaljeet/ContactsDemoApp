package com.app.contactsdemoapp.adapters

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.contactsdemoapp.R
import com.app.contactsdemoapp.fragments.ContactsFragment


import com.app.contactsdemoapp.models.ContactsModelDTO

import kotlinx.android.synthetic.main.contacts_list_item.view.*

class MyContactsRecyclerViewAdapter(
    private var contactsList: ArrayList<ContactsModelDTO>,
    private var contactsFragment: ContactsFragment
) : RecyclerView.Adapter<MyContactsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = contactsList[position]
        holder.item_name.text = """${item.firstName} ${item.lastName}"""
        holder.item_number.text = item.phoneNumber

        holder.contact_item_layout.setOnClickListener { contactsFragment.onContactSelected(item) }
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val item_name: TextView = view.item_name
        val item_number: TextView = view.item_number
        val contact_item_layout: CardView = view.contact_item_layout
    }
}
