package com.jetbrains.handson.mpp.myapplication.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CarParkDao {
    @Query("select * from carparkdatabase")
    fun getCarParks(): LiveData<List<CarParkWithExtraInfoDataBase>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCarPark(vararg carPark: CarParkDataBase)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllExtraInfo(vararg extraInfo: ExtraInfoDatabase)

    fun insertAll(
        carParkList: List<CarParkDataBase>,
        extraInfoList: List<ExtraInfoDatabase>
    ) {
        insertAllCarPark(*carParkList.toTypedArray())
        insertAllExtraInfo(*extraInfoList.toTypedArray())
    }

}

@Database(entities = [CarParkDataBase::class, ExtraInfoDatabase::class], version = 1)
abstract class CarParksDatabase : RoomDatabase() {
    abstract val carParkDao: CarParkDao
}

private lateinit var INSTANCE: CarParksDatabase

fun getDatabase(context: Context): CarParksDatabase {
    synchronized(CarParksDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CarParksDatabase::class.java,
                "videos"
            ).build()
        }
    }
    return INSTANCE
}
