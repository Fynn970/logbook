package com.fynn.logbook.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "recordinfo", foreignKeys = [ForeignKey(entity = ExperimentInfo::class,
    parentColumns = ["mExperimentId"],
    childColumns = ["mRecordId"])]
)
data class RecordInfo (
    @PrimaryKey(autoGenerate = true) val mRecordId:Long = 0,
    @ColumnInfo(name = "experiment_id") val mExperimentId: Long,
    @ColumnInfo(name = "record_data") var mRecordData:String
    )