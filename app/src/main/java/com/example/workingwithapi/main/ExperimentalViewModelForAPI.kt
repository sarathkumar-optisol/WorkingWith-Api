package com.example.workingwithapi.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.workingwithapi.util.DispatcherProvider

/**
 * Created by SARATH on 03-05-2021
 */
class ExperimentalViewModelForAPI @ViewModelInject constructor(
        private val repository: MainRepository,
        private val dispatchers : DispatcherProvider
) : ViewModel() {



}