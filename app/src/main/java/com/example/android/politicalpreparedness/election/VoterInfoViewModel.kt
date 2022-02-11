package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.launch

class VoterInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ElectionsRepository(ElectionDatabase.getInstance(application))

    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    private val _election = MutableLiveData<Election>()
    val election: LiveData<Election>
        get() = _election

    private val _url = MutableLiveData<String>()
    val url: LiveData<String>
        get() = _url

    fun getVoterInfo() {
        viewModelScope.launch {
            val result = repository.getVoterInfo(election.value?.id ?: 0, election.value?.division.toString())
            if (result != null) {
                _voterInfo.postValue(result)
            }
        }
    }

    fun saveElection(election: Election) {
        _election.value?.isSaved = !election.isSaved
        viewModelScope.launch {
            repository.saveElection(election)
        }
    }

    fun removeElection(electionId: Int) {
        viewModelScope.launch {
            repository.removeElection(electionId)
        }
    }

    fun onClickUrl(url: String) {
        _url.value = url
    }

}