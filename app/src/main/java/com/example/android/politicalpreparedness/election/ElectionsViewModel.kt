package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.launch

class ElectionsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = ElectionDatabase.getInstance(application)
    private val electionsRepository = ElectionsRepository(database)

    val upcomingElections = electionsRepository.elections
    val savedElections = electionsRepository.savedElections

    private val _navigateToVoterInformation = MutableLiveData<Election>()
    val navigateToVoterInformation: LiveData<Election>
        get() = _navigateToVoterInformation

    init {
        viewModelScope.launch { electionsRepository.refreshElections() }
    }

    fun navigateToVoterInformation(election: Election) {
        _navigateToVoterInformation.value = election
    }

    fun navigateToVoterInformationCompleted() {
        _navigateToVoterInformation.value = null
    }

}