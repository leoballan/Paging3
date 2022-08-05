package com.vila.paging3test.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vila.paging3test.R
import com.vila.paging3test.databinding.ItemUserBinding
import com.vila.randomusertest.domain.models.User

class RandomUserPagerAdapter() :
    PagingDataAdapter<User, RandomUserPagerAdapter.Holder>(MyDiffCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = getItem(position).let { holder.bind(it) }
    }

    object MyDiffCallBack : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem == newItem

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.userName == newItem.userName && oldItem.email == newItem.email
        }
    }

    inner class Holder(private val viewBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(user: User?) {
            user?.let {
                with(viewBinding) {
                    this.tvUserName.text = it.userName
                    Glide.with(viewBinding.root.context)
                        .load(it.picture.thumbnail)
                        .circleCrop()
                        .placeholder(R.drawable.ic_baseline_downloading_24)
                        .into(this.image)
                }
            }
        }
    }
}