package com.fynn.logbook.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.bean.RecordInfo

@Dao
interface AppDao {

    @Query("SELECT * FROM expermentsamples")
    suspend fun getAllExperment():MutableList<ExperimentInfo>

    @Insert(entity = ExperimentInfo::class)
    suspend fun saveExperment(info: ExperimentInfo):Long

    @Insert(entity = RecordInfo::class)
    suspend fun saveRecord(info: RecordInfo): Long

    @Query("SELECT * FROM RECORDINFO WHERE experiment_id = experiment_id")
    suspend fun getRecordByExperimentId(experimentId: Long):MutableList<RecordInfo>
}