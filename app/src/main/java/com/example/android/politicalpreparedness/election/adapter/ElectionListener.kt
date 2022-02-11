package com.example.android.politicalpreparedness.election.adapter

import com.example.android.politicalpreparedness.network.models.Election

class ElectionListener(val listener: (Election) -> Unit) {
    fun onClick(data: Election) = listener(data)
}