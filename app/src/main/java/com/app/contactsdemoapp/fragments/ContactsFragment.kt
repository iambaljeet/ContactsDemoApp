package com.app.contactsdemoapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.contactsdemoapp.R
import com.app.contactsdemoapp.activity.MessageActivity
import com.app.contactsdemoapp.adapters.MyContactsRecyclerViewAdapter
import com.app.contactsdemoapp.models.ContactsModelDTO
import com.app.contactsdemoapp.utility.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_contacts_layout.*
import java.util.*

class ContactsFragment : Fragment() {
    private val contactsList: ArrayList<ContactsModelDTO> = ArrayList()

    private var myContactsRecyclerViewAdapter: MyContactsRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts_layout, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()

        getContacts()
    }

    fun getContacts() {
        val fileName = Constants.CONTACTSJSON
        val jsonString = context?.assets?.open(fileName)?.bufferedReader().use{
            it?.readText() }
        val collectionType = object : TypeToken<List<ContactsModelDTO>>() {}.type
        val contacts = Gson()
            .fromJson(jsonString, collectionType) as List<ContactsModelDTO>

        contactsList.addAll(contacts)

        myContactsRecyclerViewAdapter = MyContactsRecyclerViewAdapter(contactsList, this)
        contacts_recyclerview.layoutManager = LinearLayoutManager(context)
        contacts_recyclerview.adapter = myContactsRecyclerViewAdapter
    }

    fun onContactSelected(item: ContactsModelDTO) {
        MessageActivity.launchActivity(context, item)
    }

    companion object {
//        Create & return instance of fragment
        fun newInstance(): Fragment {
            var fragment: Fragment? = null
            if (fragment == null) {
                fragment = ContactsFragment()
            }
            return fragment
        }
    }
}