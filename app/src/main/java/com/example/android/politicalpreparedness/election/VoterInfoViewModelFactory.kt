package com.example.android.politicalpreparedness.election

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R

class VoterInfoViewModelFactory(val app: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java)) {
            return VoterInfoViewModel(app) as T
        }
        throw IllegalArgumentException(app.getString(R.string.viewmodel_factory_error))
    }
}