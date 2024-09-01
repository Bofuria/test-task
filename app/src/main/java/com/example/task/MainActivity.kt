package com.example.task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.task.presentation.main.MainScreen
import com.example.task.presentation.main.MainScreenViewModel
import com.example.task.retrofit.ApiService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mainViewModel = hiltViewModel<MainScreenViewModel>()
            mainViewModel.getAllData()

            MainScreen(
                viewModel = mainViewModel
            )
        }
    }
}