package com.app.messagedemoapp.fragments.ui.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.messagedemoapp.models.ContactsModelDTO
import com.app.messagedemoapp.repository.ContactsRepository

class ContactsViewModel : ViewModel() {
    fun getContacts(): MutableLiveData<List<ContactsModelDTO>> {
        return ContactsRepository.getContacts()
    }
}