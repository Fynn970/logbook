package com.fynn.logbook.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "expermentsamples")
data class ExperimentInfo(
    @PrimaryKey(autoGenerate = true) val mExperimentId: Long = 0,
    @ColumnInfo(name = "ear_tag_number") val mEarTagNumber: String,
    @ColumnInfo(name = "process_name") val mProcessName: String = "",
//    @ColumnInfo(name = "room_number") val mRoomNumber: String,
    @ColumnInfo(name = "animal_state") val mAnimalState:Int = 1, //1为存活，0为死亡
    @ColumnInfo(name = "animal_merchants") val animalMerchants: String,
    @ColumnInfo(name = "day_interval") val mDayInterval:Int,
    @ColumnInfo(name = "new_record_create_date") val mNewRecordCreateDate: Long=0,
    @ColumnInfo(name = "update_date") val mUpdateDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "create_date") val mCreateDate: Long = System.currentTimeMillis()
)