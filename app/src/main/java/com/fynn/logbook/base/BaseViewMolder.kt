package com.fynn.logbook.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewMolder<UiState : IUiState, UiIntent : IUiEvent> : ViewModel() {

    protected val _uiStateFlow = MutableStateFlow(initUiState())
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow.asStateFlow()
    private val _uiIntentFlow: Channel<UiIntent> = Channel()
    val uiIntentFlow: Flow<UiIntent> = _uiIntentFlow.receiveAsFlow()
    private val _loadUiIntentFlow: Channel<LoadUIState> = Channel()
    val loadUiIntentFlow: Flow<LoadUIState> = _loadUiIntentFlow.receiveAsFlow()
    private val _dataSharedFlow = MutableSharedFlow<UiState>()
    val dataSharedFlow: SharedFlow<UiState> = _dataSharedFlow.asSharedFlow()
    protected abstract fun initUiState(): UiState

    protected fun sendUiState(copy: UiState.() -> UiState) {
        _uiStateFlow.update { copy(_uiStateFlow.value) }
    }

    protected suspend fun sendDataShared(state:UiState){
        _dataSharedFlow.emit(state)
    }
//    protected suspend fun sendDataShared(state: UiState){
////        viewModelScope.launch {
//        _dataSharedFlow.emit(state)
////        }
//    }

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

    /**
     * 发送当前加载状态：Loading、Error
     */
    private fun sendLoadUiIntent(loadUiIntent: LoadUIState) {
        viewModelScope.launch {
            _loadUiIntentFlow.send(loadUiIntent)
        }
    }

    fun scope(
        isShowLoading: Boolean = true, requestFail: String.() -> Unit = {
            sendLoadUiIntent(LoadUIState.onError(this))
        }, block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (isShowLoading) {
                    sendLoadUiIntent(LoadUIState.Loading(true))
                }
                block()
            } catch (e: Exception) {
                requestFail.invoke(e.message.toString())
                Log.e("error", e.message + "  ")
            } finally {
                if (isShowLoading) {
                    sendLoadUiIntent(LoadUIState.Loading(false))
                }
            }
        }
    }

}