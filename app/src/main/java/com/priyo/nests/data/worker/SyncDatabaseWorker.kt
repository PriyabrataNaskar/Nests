package com.priyo.nests.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.priyo.nests.corenetwork.NetworkResult
import com.priyo.nests.data.source.local.datasource.IFilterLocalDataSource
import com.priyo.nests.data.source.remote.datasource.IFilterRemoteDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SyncDatabaseWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val localDataSource: IFilterLocalDataSource,
    private val remoteDataSource: IFilterRemoteDataSource,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        syncLocalDatabase()
        return@withContext Result.success()
    }

    private suspend fun syncLocalDatabase(): Result {
        return when (val filters = remoteDataSource.getFilters()) {
            is NetworkResult.Success -> {
                when (val result = localDataSource.updateFilters(filters = filters.data)) {
                    is NetworkResult.Success -> Result.success()
                    else -> Result.failure()
                }
            }

            else -> {
                Result.failure()
            }
        }
    }

    companion object {
        const val WORK_TAG = "SyncDatabaseWorker"
    }
}
