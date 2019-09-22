package com.app.messagedemoapp.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ContactsModelDTO(

    @field:SerializedName("last_name")
    val lastName: String? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("first_name")
    val firstName: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(lastName)
        parcel.writeString(phoneNumber)
        parcel.writeValue(id)
        parcel.writeString(firstName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContactsModelDTO> {
        override fun createFromParcel(parcel: Parcel): ContactsModelDTO {
            return ContactsModelDTO(parcel)
        }

        override fun newArray(size: Int): Array<ContactsModelDTO?> {
            return arrayOfNulls(size)
        }
    }
}