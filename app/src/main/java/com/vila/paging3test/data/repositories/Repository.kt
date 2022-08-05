package com.vila.paging3test.data.repositories

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vila.paging3test.data.RandomUserApi
import com.vila.paging3test.data.datasource.DataSource
import com.vila.randomusertest.domain.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


class Repository @Inject constructor
    (private val apiService:RandomUserApi ) {

    fun getSearchResultStream(): Flow<PagingData<User>> {
        Log.d("webservice","dentro del repository")
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { DataSource(apiService) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}