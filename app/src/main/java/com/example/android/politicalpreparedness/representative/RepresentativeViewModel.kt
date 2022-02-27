package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel: ViewModel() {

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address> = _address

    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>> = _representatives

    fun updateAddress(address: Address){
        _address.value = address
        getRepresentatives()
    }

    fun getRepresentatives() {
        viewModelScope.launch {
            address.value?.let {
                val (offices, officials) = CivicsApi.retrofitService.getRepresentatives(it.toFormattedString()).await()
                _representatives.value = offices.flatMap { office ->  office.getRepresentatives(officials)}
            }
        }
    }
}
