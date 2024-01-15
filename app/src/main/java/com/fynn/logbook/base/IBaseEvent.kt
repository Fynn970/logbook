package com.fynn.logbook.base

import androidx.annotation.Keep

@Keep
interface IUiState

@Keep
interface IUiEvent


sealed class LoadUIState{
//    val loading: Boolean = false,
//    val onError: String
    data class Loading(val isShow: Boolean): LoadUIState()
    data class onError(val errorMsg: String): LoadUIState()
}