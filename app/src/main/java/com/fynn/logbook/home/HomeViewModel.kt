package com.fynn.logbook.home

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.fynn.logbook.base.BaseViewMolder
import com.fynn.logbook.base.IUiEvent
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.repository.AppRepository
import com.fynn.logbook.repository.IAppRepostory
import com.fynn.logbook.util.getApplicationContext
import com.fynn.logbook.util.showToast
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel constructor(val repository: IAppRepostory) :
    BaseViewMolder<HomeState, HomeIntent>() {


    override fun initUiState(): HomeState {
        return HomeState()
    }

    override fun handleIntent(intent: IUiEvent) {
        when (intent) {
            is HomeIntent.GetAllExperiment -> {
                getAllExperiment()
            }
            is HomeIntent.SaveExperiment-> {
                saveExperimentInfo(intent.info)
            }
        }
    }

    private fun saveExperimentInfo(info:ExperimentInfo){
        scope {
            val saveExperiment = repository.saveExperiment(info)
            if (saveExperiment){
                val list: MutableList<ExperimentInfo> = uiStateFlow.value.list.toMutableList()
                list.add(info)
                sendUiState {
                    copy(list = list)
                }
            }else{
                showToast("保存失败")
            }
        }
    }

    private fun getAllExperiment() {

        viewModelScope.launch {
            try {

                val list = repository.getExperimentList()
                sendUiState {
                    copy(list = list)
                }
            } catch (e: Exception) {

            }
        }
    }


    private fun ToastInfo(it: String) {
        viewModelScope.launch {
            showToast(it)
        }
    }

}