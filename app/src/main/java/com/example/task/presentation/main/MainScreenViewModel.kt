package com.example.task.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.task.domain.entities.Item
import com.example.task.domain.entities.ServerData
import com.example.task.retrofit.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    private fun currentState(): MainScreenState = _state.value

    private var currentItemId = 0

    fun getAllData() {
        val call = apiService.getAllData()
        call.enqueue(
            object: Callback<ServerData> {
                override fun onResponse(
                    call: Call<ServerData>,
                    response: Response<ServerData>
                ) {
                    if(response.isSuccessful) {
                        val items = response.body() as ServerData
                        val oldState = currentState()
                        val newState = oldState.copy(
                            initialData = items,
                            size = response.body()!!.data.size
                            )
                        _state.tryEmit(newState)
                        currentItemId = _state.value.initialData.data[0].id.toInt()
                        getResource()
                        Log.i("INFO", "Item fetched successfully")
                    }
                }
                override fun onFailure(call: Call<ServerData>, t: Throwable) {
                    Log.e("Error", t.toString())
                }
            }
        )
    }

    fun getResource() {
        val call = apiService.getDataById(currentItemId.toString())
        call.enqueue(
            object: Callback<Item> {
                override fun onResponse(call: Call<Item>, response: Response<Item>) {
                    if(response.isSuccessful) {
                        val receivedItem = response.body() as Item

                        if(currentState().size <= currentItemId) {
                            currentItemId = 0
                        }

                        if(receivedItem.type == "game") {
                            currentItemId++
                            getResource()
                        } else {
                            currentItemId++
                        }
                        val oldState = currentState()
                        val newState = oldState.copy(currentItem = receivedItem)
                        _state.tryEmit(newState)
                    }
                }

                override fun onFailure(call: Call<Item>, t: Throwable) {
                    Log.e("Error", t.toString())
                }
            }
        )
    }
}