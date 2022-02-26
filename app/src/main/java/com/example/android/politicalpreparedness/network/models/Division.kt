package com.example.android.politicalpreparedness.network.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Division(
        val id: String,
        val country: String,
        val state: String
) : Parcelable {
        fun toFormattedString(): String {
                var output = country.plus("\n")
                if (state.isNotEmpty()) {
                        output = output.plus(state).plus("\n")
                }
                return output
        }
}