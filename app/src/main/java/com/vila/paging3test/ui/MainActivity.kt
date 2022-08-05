package com.vila.paging3test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.vila.paging3test.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewmodel : MainViewModel by viewModels()
    private val mAdapter = RandomUserPagerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        initRecyclerView()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewmodel.userFlow.collect {
                    Log.d("webservice","datos que llegan ...... $it")
                    mAdapter.submitData(it)
                }
            }
        }
    }

    private fun initRecyclerView(){
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = mAdapter.withLoadStateFooter(footer = UserLoadStateAdapter{
              mAdapter.retry()
            })
        }
    }
}