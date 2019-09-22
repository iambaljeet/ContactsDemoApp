package com.app.messagedemoapp.fragments.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.messagedemoapp.R
import com.app.messagedemoapp.activity.ui.message.MessageActivity
import com.app.messagedemoapp.adapters.MyContactsRecyclerViewAdapter
import com.app.messagedemoapp.models.ContactsModelDTO
import com.app.messagedemoapp.utility.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_contacts_layout.*
import java.util.*

class ContactsFragment : Fragment() {
    private val contactsList: ArrayList<ContactsModelDTO> = ArrayList()

    private var myContactsRecyclerViewAdapter: MyContactsRecyclerViewAdapter? = null

    private lateinit var contactsViewModel: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts_layout, container, false)
        contactsViewModel =
            ViewModelProviders.of(this, CustomViewModelFactory()).get(ContactsViewModel::class.java)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        myContactsRecyclerViewAdapter = MyContactsRecyclerViewAdapter(this)
        contacts_recyclerview.layoutManager =
            LinearLayoutManager(context)
        contacts_recyclerview.adapter = myContactsRecyclerViewAdapter
        contacts_recyclerview.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        getContacts()
    }

    private fun getContacts() {
        val contactsLiveData = contactsViewModel.getContacts()

        contactsLiveData.observe(this, androidx.lifecycle.Observer { contacts ->
            myContactsRecyclerViewAdapter?.newDataInserted(contacts)
        })
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