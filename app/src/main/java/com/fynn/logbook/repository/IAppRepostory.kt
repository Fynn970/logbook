package com.fynn.logbook.repository

import com.fynn.logbook.bean.ExperimentInfo

interface IAppRepostory {

    suspend fun getExperimentList():MutableList<ExperimentInfo>
    suspend fun saveExperiment(info: ExperimentInfo):Boolean
}