package com.vila.paging3test.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import com.vila.paging3test.data.repositories.Repository
import com.vila.randomusertest.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _dataState = MutableStateFlow<List<User>>(listOf())
    val dataState = _dataState.asStateFlow()
    val userFlow = repository.getSearchResultStream().cachedIn(viewModelScope)

    init {
        fetchData()
    }
    fun fetchData(){
        viewModelScope.launch {
            repository.getSearchResultStream().collect {
                Log.d("webservice","this is th data receibed")
               // _dataState.value = it.
            }
        }

    }
}