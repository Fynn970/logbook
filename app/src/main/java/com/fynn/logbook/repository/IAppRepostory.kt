package com.fynn.logbook.repository

import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.bean.RecordInfo

interface IAppRepostory {

    suspend fun getExperimentList():MutableList<ExperimentInfo>
    suspend fun saveExperiment(info: ExperimentInfo):Boolean
    suspend fun saveRecord(info:RecordInfo):Boolean
    suspend fun getRecordList(experimentId:Long):MutableList<RecordInfo>
}