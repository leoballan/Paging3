package com.vila.paging3test.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vila.paging3test.databinding.LoadStateItemBinding

class UserLoadStateAdapter(private val action :()->Unit): LoadStateAdapter<UserLoadStateAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val view = LoadStateItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class Holder(private val binding: LoadStateItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(loadState: LoadState){
            binding.let {
                it.progress.isVisible = loadState is LoadState.Loading
                it.btRetry.isVisible = loadState !is LoadState.Loading
                it.tvMessage.isVisible = loadState !is LoadState.Loading

                if (loadState is LoadState.Error){
                    it.tvMessage.text = loadState.error.localizedMessage
                }
                it.btRetry.setOnClickListener {
                    action.invoke()
                }
            }
        }
    }


}