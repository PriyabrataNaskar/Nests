package com.priyo.nests.domain.usecase

import com.priyo.nests.core.coroutine.CoroutineDispatcherProvider
import com.priyo.nests.core.result.UiResult
import com.priyo.nests.corenetwork.ERROR
import com.priyo.nests.corenetwork.NetworkResult
import com.priyo.nests.corenetwork.executeSafeCall
import com.priyo.nests.domain.model.Filters
import com.priyo.nests.domain.repository.IFilterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchFiltersUseCase @Inject constructor(
    private val repository: IFilterRepository,
    private val coroutineContextController: CoroutineDispatcherProvider,
) {

    operator fun invoke(): Flow<UiResult<Filters, Exception>> =
        flow {
            executeSafeCall(
                block = {
                    when (
                        val result = repository.getFilters()
                    ) {
                        is NetworkResult.Success -> {
                            emit(UiResult.Success(result.data))
                        }

                        is NetworkResult.Error -> {
                            val errorMessage = result.throwable?.message.orEmpty()
                            emit(
                                UiResult.Error(
                                    Exception(
                                        errorMessage,
                                    ),
                                ),
                            )
                        }
                    }
                },
                error = {
                    emit(UiResult.Error(it))
                },
            )
        }.catch {
            emit(
                UiResult.Error(
                    Exception(
                        ERROR.SOMETHING_WENT_WRONG,
                    ),
                ),
            )
        }.flowOn(coroutineContextController.dispatcherIO)
}
