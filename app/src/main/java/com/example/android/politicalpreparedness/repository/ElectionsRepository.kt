package com.example.android.politicalpreparedness.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsRepository(private val database: ElectionDatabase) {

    val elections: LiveData<List<Election>> = database.electionDao.getAll()
    val savedElections: LiveData<List<Election>> = database.electionDao.getSavedElections()

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            val electionResponseBody = CivicsApi.retrofitService.getElections().await()
            database.electionDao.insertAll(electionResponseBody.elections)
        }
    }

    suspend fun getVoterInfo(electionId: Int, address: String): VoterInfoResponse? {
        try {
            withContext(Dispatchers.IO) {
                return@withContext CivicsApi.retrofitService.getVoterInfo(electionId, address)
                    .await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun saveElection(election: Election) {
        withContext(Dispatchers.IO) {
            database.electionDao.insertElection(election)
        }
    }

    suspend fun removeElection(electionId: Int) {
        withContext(Dispatchers.IO) {
            database.electionDao.deleteById(electionId)
        }
    }

    fun getElectionById(electionId: Int): Election? {
        return elections.value?.find { it.id == electionId }
    }
}