package com.fauzimaulana.storyapp.core.data

import com.fauzimaulana.storyapp.core.data.source.remote.ApiResponse
import com.fauzimaulana.storyapp.core.vo.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(loadData(apiResponse.data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error("No data found"))
            }
            is ApiResponse.Error -> {
                onFetchFailed()
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract fun loadData(data: RequestType): ResultType

    fun asFlow(): Flow<Resource<ResultType>> = result
}