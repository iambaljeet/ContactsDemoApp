package com.app.messagedemoapp.adapters

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.messagedemoapp.R
import com.app.messagedemoapp.fragments.ui.contacts.ContactsFragment
import com.app.messagedemoapp.models.ContactsModelDTO
import kotlinx.android.synthetic.main.contacts_list_item.view.*
import java.util.*

class MyContactsRecyclerViewAdapter(
    private var contactsFragment: ContactsFragment
) : RecyclerView.Adapter<MyContactsRecyclerViewAdapter.ContactViewHolder>() {

    private var contactsList: List<ContactsModelDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_list_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = contactsList[position]
        setData(item, holder)
    }

    private fun setData(
        item: ContactsModelDTO,
        holder: ContactViewHolder
    ) {
        val namePrefix = item.firstName?.get(0).toString()

        holder.itemName.text = "${item.firstName} ${item.lastName}"
        holder.itemNumber.text = item.phoneNumber
        holder.itemAvatar.text = namePrefix

        val shapeDrawable = ShapeDrawable(OvalShape())
        shapeDrawable.intrinsicHeight = 100
        shapeDrawable.intrinsicWidth = 100
        val rnd = Random()
        shapeDrawable.paint.color =
            Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

        holder.itemAvatar.background = shapeDrawable

        holder.contactItemLayout.setOnClickListener {
            contactsFragment.onContactSelected(
                item
            )
        }
    }

    fun newDataInserted(contacts: List<ContactsModelDTO>) {
        this.contactsList = contacts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: AppCompatTextView = view.item_name
        val itemNumber: AppCompatTextView = view.item_number
        val contactItemLayout: ConstraintLayout = view.contact_item_layout
        val itemAvatar: AppCompatTextView = view.item_avatar
    }
}
