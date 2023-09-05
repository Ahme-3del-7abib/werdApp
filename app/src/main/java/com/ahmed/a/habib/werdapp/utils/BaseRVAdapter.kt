package com.ahmed.a.habib.werdapp.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter

abstract class BaseRVAdapter<T : Any, VB : ViewDataBinding>(callback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BaseRVAdapter.BaseViewHolder<VB>>(callback) {

    abstract fun getLayout(): Int

    var listener: ((view: View, item: T, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder<VB>(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayout(),
            parent,
            false
        )
    )

    class BaseViewHolder<VB : ViewDataBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)
}