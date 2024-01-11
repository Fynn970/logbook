package com.fynn.logbook.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewMolder<UiState: IUiState, UiIntent: IUiEvent>: ViewModel() {

    protected val _uiStateFlow = MutableStateFlow(initUiState())
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow.asStateFlow()
    private val _uiIntentFlow: Channel<UiIntent> = Channel()
    val uiIntentFlow: Flow<UiIntent> = _uiIntentFlow.receiveAsFlow()

    protected abstract fun initUiState(): UiState

    protected fun sendUiState(copy:UiState.() -> UiState){
        _uiStateFlow.update{copy(_uiStateFlow.value)}
    }

    protected abstract fun handleIntent(intent: IUiEvent)

    fun sendUiIntent(uiIntent: UiIntent) {
        viewModelScope.launch {
            _uiIntentFlow.send(uiIntent)
        }
    }

    init {
        viewModelScope.launch {
            uiIntentFlow.collect {
                handleIntent(it)
            }
        }
    }

    fun scope(block:suspend ()->Unit){
        viewModelScope.launch {
            try {
                block()
            }catch (e: Exception){
                Log.e("error", e.message + "  ")
            }
        }
    }

}