package com.vila.paging3test.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vila.paging3test.data.RandomUserApi
import com.vila.paging3test.data.models.toUserList
import com.vila.paging3test.data.repositories.Repository.Companion.NETWORK_PAGE_SIZE
import com.vila.randomusertest.domain.models.User
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val GITHUB_STARTING_PAGE_INDEX = 1


class DataSource  constructor(private val userApi: RandomUserApi): PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        return try {
            val response = userApi.getRamdomUsers(position, params.loadSize)
            val repos = response.toUserList()
            Log.d("webservice","datos en el datasource ...... $response")

            Log.d("webservice","datos en el datasource ...... $repos")
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}