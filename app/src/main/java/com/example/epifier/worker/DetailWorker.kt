package com.example.epifier.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.supervisorScope
import java.util.concurrent.TimeUnit

@HiltWorker
class DetailWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) :
    CoroutineWorker(context, workerParams) {

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override suspend fun doWork(): Result {
        return try {
            //To Do: Mock Network calls by using WorkerFactory Impl
            supervisorScope {
                delay(3000)
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        private const val BASE_64_ENCODED_PAN = "key_pan_data"

        fun buildRequest(pan: String): OneTimeWorkRequest {

            val inputData = Data.Builder()
                .putString(BASE_64_ENCODED_PAN, pan)
                .build()

            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()

            return OneTimeWorkRequest.Builder(DetailWorker::class.java)
                .setInputData(inputData)
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 15, TimeUnit.MINUTES)
                .build()
        }
    }
}