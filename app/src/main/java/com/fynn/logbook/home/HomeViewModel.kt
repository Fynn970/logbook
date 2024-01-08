package com.fynn.logbook.home

import androidx.lifecycle.viewModelScope
import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.vm.HomeUiState
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewMolder<HomeState, HomeIntent>() {


    override fun initUiState(): HomeState {
        return HomeState()
    }

    override fun handleIntent(intent: IUiEvent) {
        when(intent){
            is HomeIntent.ToastInfo -> {
                ToastInfo()
            }
        }
    }


    private fun ToastInfo(){
        viewModelScope.launch {
            _uiStateFlow.update {
                it.copy(str = "sdasdfvadw")
            }
        }
    }

}