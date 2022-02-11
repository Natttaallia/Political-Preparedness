package com.example.android.politicalpreparedness.repository

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ElectionsRepository(private val database: ElectionDatabase) {

    val elections: LiveData<List<Election>> = database.electionDao.getAll()
    val savedElections: LiveData<List<Election>> = database.electionDao.getSavedElections()

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            val electionResponseBody = CivicsApi.retrofitService.getElections().await()
            database.electionDao.insertAll(electionResponseBody.elections)
        }
    }
}