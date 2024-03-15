package com.fynn.logbook.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.bean.RecordInfo

@Dao
interface AppDao {

    @Query("SELECT * FROM expermentsamples")
    suspend fun getAllExperment():MutableList<ExperimentInfo>

    @Query("SELECT * FROM expermentsamples WHERE animal_state = :type")
    suspend fun getTypeExperment(type: Int):MutableList<ExperimentInfo>

    @Insert(entity = ExperimentInfo::class)
    suspend fun saveExperment(info: ExperimentInfo):Long

    @Transaction
    @Insert(entity = RecordInfo::class)
    suspend fun saveRecord(info: RecordInfo): Long

    @Query("SELECT * FROM RECORDINFO WHERE experiment_id = :experimentId")
    suspend fun getRecordByExperimentId(experimentId: Long):MutableList<RecordInfo>

    @Query("SELECT * FROM RECORDINFO WHERE mRecordId = :id")
    suspend fun getRecordByRecordId(id:Long):RecordInfo?

    @Query("SELECT * FROM EXPERMENTSAMPLES WHERE mExperimentId = :id")
    suspend fun getExperimentById(id:Long):ExperimentInfo?

    @Query("UPDATE EXPERMENTSAMPLES SET new_record_create_date = :newRecordCreateTime WHERE mExperimentId = :experimentId")
    suspend fun updateExperimentNewRecordTimeById(experimentId: Long, newRecordCreateTime: Long)

    @Update
    suspend fun updateExperiment(experimentInfo: ExperimentInfo):Int

    @Query("DELETE FROM EXPERMENTSAMPLES WHERE mExperimentId = :id")
    suspend fun deleteExperimentById(id:Long):Int

    @Query("DELETE FROM RECORDINFO WHERE experiment_id = :id")
    suspend fun deleteRecordByExperimentId(id:Long):Int
    @Query("DELETE FROM RECORDINFO WHERE mRecordId = :id")
    suspend fun deleteRecordById(id:Long):Int

    @Query("SELECT * FROM RECORDINFO WHERE experiment_id = :id and create_date = (SELECT MAX(create_date) FROM recordinfo)")
    suspend fun getLastCreateRecordByExperimentId(id: Long):RecordInfo?
}