package com.example.android.politicalpreparedness.election

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.launch

class VoterInfoViewModel(context: Context) : ViewModel() {

    private val database = ElectionDatabase.getInstance(context)
    private val repository = ElectionsRepository(database)

    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    private val _election = MutableLiveData<Election>()
    val election: LiveData<Election>
        get() = _election

    private val _url = MutableLiveData<String>()
    val url: LiveData<String>
        get() = _url

    fun getVoterInfo(division: Division, electionId: Int, isSaved: Boolean) {
        viewModelScope.launch {
            val result = repository.getVoterInfo(electionId, division)
            result?.election?.isSaved = isSaved
            _voterInfo.postValue(result)
            _election.postValue(result?.election)
        }
    }

    fun updateElection(election: Election) {
        _election.postValue(_election.value?.apply { isSaved = !election.isSaved })
        viewModelScope.launch {
            repository.updateElection(election)
        }
    }

    fun onClickUrl(url: String) {
        _url.value = url
    }

}