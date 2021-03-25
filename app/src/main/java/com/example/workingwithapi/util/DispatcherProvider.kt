package com.example.workingwithapi.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * interface for coroutines Dispatchers
 */

interface DispatcherProvider {
    val main : CoroutineDispatcher
    val io : CoroutineDispatcher
    val default : CoroutineDispatcher
    val unconfined : CoroutineDispatcher
}