package com.jetbrains.handson.mpp.myapplication.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jetbrains.handson.mpp.myapplication.database.getDatabase
import com.jetbrains.handson.mpp.myapplication.repository.CarParkRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, parameters: WorkerParameters) :
    CoroutineWorker(appContext, parameters) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val carParkRepository = CarParkRepository(database)
        return try {
            carParkRepository.refreshCarPark()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}