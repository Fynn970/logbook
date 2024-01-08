package com.fynn.logbook.home

import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.base.IUiState


data class HomeState(
    val count: Int = 0,
    val recycleViewState: RecycleViewUiState = RecycleViewUiState.INIT,
    val str: String = ""

) : IUiState

sealed class RecycleViewUiState {
    object INIT : RecycleViewUiState()
    data class SUCCESS(val models: MutableList<String>) : RecycleViewUiState()
}


sealed class HomeIntent : IUiEvent {

    object ToastInfoTwo : HomeIntent()
    data class ToastInfo(val str: String) : HomeIntent()
}


