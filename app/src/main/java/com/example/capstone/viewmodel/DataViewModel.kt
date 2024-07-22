package com.example.capstone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.model.DataItem
import com.example.capstone.repository.DataRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: String) : UiState()
    data class Error(val message: String): UiState()
}

class DataViewModel : ViewModel() {
    private val dataRepository = DataRepository()

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState
    fun fetchData() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val call = dataRepository.fetchData()
            call.enqueue(object : Callback<List<DataItem>> {
                override fun onFailure(call: Call<List<DataItem>>, t: Throwable) {
                    _uiState.value = UiState.Error(t.message ?: "Unknown error")
                }

                override fun onResponse(
                    call: Call<List<DataItem>>,
                    response: Response<List<DataItem>>
                ) {

                    if (response.isSuccessful) {
                        val dataString = response.body()?.joinToString(separator = "") { it.body } ?: ""
                        _uiState.value = UiState.Success(dataString)
                    } else {
                        _uiState.value = UiState.Error(response.message())
                    }
                }
            })
        }
    }

    private fun List<DataItem>.toString(): String {
        val sb = StringBuilder()
        forEach {
            sb.append(it.body)
        }
        return sb.toString()
    }

}