package com.fynn.logbook.repository

import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.bean.RecordInfo
import com.fynn.logbook.dao.AppDao
import com.fynn.logbook.dao.AppDb
import com.fynn.logbook.util.getApplicationContext

class AppRepository:IAppRepostory {

    private fun getAppDb():AppDao{
        return AppDb.getDB(getApplicationContext()).experimentDao()
    }

    override suspend fun getExperimentList(type:Int): MutableList<ExperimentInfo> {
        return getAppDb().getTypeExperment(type);
    }

    override suspend fun saveExperiment(info: ExperimentInfo): Boolean {
        return getAppDb().saveExperment(info) != -1L
    }

    override suspend fun saveRecord(info: RecordInfo): Boolean {
        val experimentInfo = getAppDb().getExperimentById(info.mExperimentId);
        experimentInfo?.let {
            val id = getAppDb().saveRecord(info)
            if (id != -1L) {
                val result = getAppDb().getRecordByRecordId(id)
                result?.let {
                    getAppDb().updateExperimentNewRecordTimeById(result.mExperimentId, result.mRecordCreateDate)
                    return true
                }
            }
        }
        return false
    }

    override suspend fun getRecordList(experimentId: Long): MutableList<RecordInfo> {
        val experimentInfo = getAppDb().getExperimentById(experimentId);
        experimentInfo?.let {
            return getAppDb().getRecordByExperimentId(experimentId)
        }
        return mutableListOf()
    }

    override suspend fun getExperimentById(id: Long): ExperimentInfo? {
        return getAppDb().getExperimentById(id)
    }

    override suspend fun updateExperiment(experimentInfo: ExperimentInfo): Boolean {
        return getAppDb().updateExperiment(experimentInfo) == 1
    }

    override suspend fun deleteExperiment(id: Long): Boolean {
        getAppDb().deleteRecordByExperimentId(id)
        return getAppDb().deleteExperimentById(id) > 0
    }

    override suspend fun deleteRecordById(id: Long): Boolean {
        val deleteResult = getAppDb().deleteRecordById(id) > 0
        if (deleteResult){

        }
        return
    }


}