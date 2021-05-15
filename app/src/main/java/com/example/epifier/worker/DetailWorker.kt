package com.example.epifier.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class DetailWorker @AssistedInject constructor(@Assisted context: Context, @Assisted workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.success()
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