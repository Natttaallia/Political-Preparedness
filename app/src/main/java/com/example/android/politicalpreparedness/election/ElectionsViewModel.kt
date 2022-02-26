package com.example.android.politicalpreparedness.election

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.launch

class ElectionsViewModel(context: Context) : ViewModel() {

    private val database = ElectionDatabase.getInstance(context)
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