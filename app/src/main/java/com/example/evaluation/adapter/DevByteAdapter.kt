package com.example.evaluation.adapter

import android.view.LayoutInflater

import android.view.ViewGroup


import androidx.annotation.LayoutRes

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluation.R
import com.example.evaluation.databinding.ItemBinding
import com.example.evaluation.domain.DevByteVideo


class DevByteAdapter() : RecyclerView.Adapter<DevByteViewHolder>() {


    var videos: List<DevByteVideo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder {
        val withDataBinding: ItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            DevByteViewHolder.LAYOUT,
            parent,
            false
        )
        return DevByteViewHolder(withDataBinding)
    }

    override fun getItemCount() = videos.size


    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.video = videos[position]

        }
    }

}


class DevByteViewHolder(val viewDataBinding: ItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.item
    }
}