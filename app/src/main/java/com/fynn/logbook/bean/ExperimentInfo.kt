package com.fynn.logbook.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "expermentsamples")
data class ExperimentInfo(
    @PrimaryKey(autoGenerate = true) val mExperimentId: Long = 0,
    @ColumnInfo(name = "ear_tag_number") var mEarTagNumber: String,
//    @ColumnInfo(name = "room_number") val mRoomNumber: String,
    @ColumnInfo(name = "animal_state") var mAnimalState:Int = 1, //1为存活，0为死亡
    @ColumnInfo(name = "animal_merchants") var animalMerchants: String,
    @ColumnInfo(name = "day_interval") var mDayInterval:Int,
    @ColumnInfo(name = "new_record_create_date") var mNewRecordCreateDate: Long=0,
    @ColumnInfo(name = "update_date") var mUpdateDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "create_date") var mCreateDate: Long = System.currentTimeMillis()
):Serializable{
    override fun toString(): String {
        return "ExperimentInfo(mExperimentId=$mExperimentId, mEarTagNumber='$mEarTagNumber', mAnimalState=$mAnimalState, animalMerchants='$animalMerchants', mDayInterval=$mDayInterval, mNewRecordCreateDate=$mNewRecordCreateDate, mUpdateDate=$mUpdateDate, mCreateDate=$mCreateDate)"
    }
}