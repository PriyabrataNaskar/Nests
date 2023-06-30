package com.priyo.nests.data.source.local.datasource

import com.priyo.nests.corenetwork.ERROR.UNKNOWN_ERROR
import com.priyo.nests.corenetwork.NetworkResult
import com.priyo.nests.data.mapper.RealmFilterMapper
import com.priyo.nests.data.source.local.dao.FacilityDao
import com.priyo.nests.data.source.local.dao.FiltersDao
import com.priyo.nests.domain.model.Filters
import io.realm.Realm
import javax.inject.Inject

class FilterLocalDataSource @Inject constructor() : IFilterLocalDataSource {

    override suspend fun getFilters(): NetworkResult<Filters> {
        try {
            val realm = Realm.getDefaultInstance()
            val filtersModel = realm.where(FiltersDao::class.java).findFirst()

            filtersModel?.let {
                val filters = RealmFilterMapper.toFilters(it)
                return NetworkResult.Success(
                    filters,
                )
            }
            realm.close()
            return NetworkResult.Error(
                message = UNKNOWN_ERROR,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return NetworkResult.Error(
                message = e.message,
            )
        }
    }

    override suspend fun updateFilters(filters: Filters): NetworkResult<Any> {
        try {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()

            RealmFilterMapper.toFiltersDao(filters).apply {
                realm.copyToRealmOrUpdate(this)
            }
            // Commit the transaction
            realm.commitTransaction()
            // Close the Realm instance
            realm.close()
            return NetworkResult.Success(Any())
        } catch (e: Exception) {
            e.printStackTrace()
            return NetworkResult.Error(
                message = e.message,
            )
        }
    }

    override suspend fun deleteAllFilters(): NetworkResult<Any> {
        return try {
            val realm = Realm.getDefaultInstance()

            realm.beginTransaction()
            realm.delete(FacilityDao::class.java)
            realm.commitTransaction()
            realm.close()
            NetworkResult.Success(Any())
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResult.Error(
                message = e.message,
            )
        }
    }
}
