package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(elections: List<Election>)

    @Query("SELECT * FROM election_table")
    fun getAll(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table WHERE id = :id")
    fun getItemById(id: Int): LiveData<Election>

    @Query("DELETE FROM election_table where id=:id")
    fun deleteById(id: Int)

    @Query("DELETE FROM election_table")
    fun clear()

    @Query("SELECT * FROM election_table where isSaved = 1")
    fun getSavedElections(): LiveData<List<Election>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertElection(election: Election)

}