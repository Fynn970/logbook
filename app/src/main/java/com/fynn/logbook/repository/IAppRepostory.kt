package com.fynn.logbook.repository

import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.bean.RecordInfo

interface IAppRepostory {

    suspend fun getExperimentList(type:Int):MutableList<ExperimentInfo>
    suspend fun saveExperiment(info: ExperimentInfo):Boolean
    suspend fun saveRecord(info:RecordInfo):Boolean
    suspend fun getRecordList(experimentId:Long):MutableList<RecordInfo>
    suspend fun getExperimentById(id: Long): ExperimentInfo?
    suspend fun updateExperiment(experimentInfo: ExperimentInfo): Boolean
    suspend fun deleteExperiment(id: Long): Boolean

    suspend fun deleteRecordById(id:Long):Boolean
}