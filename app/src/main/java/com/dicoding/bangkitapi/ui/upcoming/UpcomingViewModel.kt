package com.dicoding.bangkitapi.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bangkitapi.data.response.EventResponse
import com.dicoding.bangkitapi.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _eventData = MutableLiveData<EventResponse>()
    val eventData: LiveData<EventResponse> get() = _eventData

    fun getEventsActive() {
        ApiConfig.apiService.getEventsActive(active = 1).enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    _eventData.value = response.body()
                } else {
                    // Tangani error jika perlu
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
            }
        })
    }
}