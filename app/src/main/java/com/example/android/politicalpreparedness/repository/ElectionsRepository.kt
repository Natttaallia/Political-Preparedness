package com.example.android.politicalpreparedness.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ElectionsRepository(private val database: ElectionDatabase) {

    val elections: LiveData<List<Election>> = database.electionDao.getAll()
    val savedElections: LiveData<List<Election>> = database.electionDao.getSavedElections()

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            val electionResponseBody = CivicsApi.retrofitService.getElections().await()
            database.electionDao.insertAll(electionResponseBody.elections)
        }
    }

    suspend fun getVoterInfo(electionId: Int, address: Division): VoterInfoResponse? {
        return try {
            withContext(Dispatchers.IO) {
                CivicsApi.retrofitService.getVoterInfo(electionId, address.toFormattedString())
                    .await()
            }
        } catch (ex: Exception) {
            null
        }
    }

    suspend fun updateElection(election: Election) {
        withContext(Dispatchers.IO) {
            database.electionDao.insertElection(election)
        }
    }
}