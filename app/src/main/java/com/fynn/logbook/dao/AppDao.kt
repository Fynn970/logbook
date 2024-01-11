package com.fynn.logbook.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fynn.logbook.bean.ExperimentInfo

@Dao
interface AppDao {

    @Query("SELECT * FROM expermentsamples")
    suspend fun getAllExperment():MutableList<ExperimentInfo>

    @Insert(entity = ExperimentInfo::class)
    suspend fun saveExperment(info: ExperimentInfo):Long

}