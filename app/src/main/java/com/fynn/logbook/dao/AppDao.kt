package com.fynn.logbook.dao

import androidx.room.Dao
import androidx.room.Query
import com.fynn.logbook.bean.ExperimentInfo

@Dao
interface AppDao {

    @Query("SELECT * FROM expermentsamples")
    fun getAllExperment():List<ExperimentInfo>


}