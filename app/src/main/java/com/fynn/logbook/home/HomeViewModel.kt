package com.fynn.logbook.home

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.util.getApplicationContext
import com.fynn.logbook.util.showToast
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel constructor() : BaseViewMolder<HomeState, HomeIntent>() {


    override fun initUiState(): HomeState {
        return HomeState()
    }

    override fun handleIntent(intent: IUiEvent) {
        when(intent){
            is HomeIntent.ToastInfo -> {
                ToastInfo(intent.str)
            }
        }
    }


    private fun ToastInfo(it: String) {
        viewModelScope.launch {
            showToast(it)
        }
    }

}