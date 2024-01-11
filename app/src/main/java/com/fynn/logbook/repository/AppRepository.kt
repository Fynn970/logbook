package com.fynn.logbook.repository

import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.dao.AppDao
import com.fynn.logbook.dao.AppDb
import com.fynn.logbook.util.getApplicationContext

class AppRepository:IAppRepostory {

    private fun getAppDb():AppDao{
        return AppDb.getDB(getApplicationContext()).experimentDao()
    }

    override suspend fun getExperimentList(): MutableList<ExperimentInfo> {
        return getAppDb().getAllExperment();
    }

    override suspend fun saveExperiment(info: ExperimentInfo): Boolean {
        return getAppDb().saveExperment(info) != -1L
    }


}