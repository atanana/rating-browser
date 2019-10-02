package com.example.android.ratingbrowser.data

sealed class StateWrapper<T> {
    class Loading<T> : StateWrapper<T>()
    data class Error<T>(val message: String) : StateWrapper<T>()
    data class Ok<T>(val data: T) : StateWrapper<T>()
}