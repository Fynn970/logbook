package com.fynn.logbook.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expermentsamples")
data class ExperimentInfo(
    @PrimaryKey val mExperimentId:String,
    @ColumnInfo(name = "experiment_name") val mName: String,
    @ColumnInfo(name = "create_date") val mCreateDate: String
)