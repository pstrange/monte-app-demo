package com.monte.appdemo.views.base.adapters

import android.view.View
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T, B: ViewDataBinding>(val binder: B) : RecyclerView.ViewHolder(binder.root), View.OnClickListener, View.OnLongClickListener{

    var item: T? = null

    var onItemClick: OnRecyclerItemClick<T>? = null
        set(value){
            itemView.setOnClickListener(this)
            field = value
        }

    var onLongItemClick: OnRecyclerLongItemClick<T>? = null
        set(value){
            itemView.setOnLongClickListener(this)
            field = value
        }

    @CallSuper
    open fun onBindViewHolder(item: T) {
        this.item = item
    }

    @CallSuper
    override fun onClick(view: View?) {
        onItemClick?.onItemClick(view, Event.CLICK(item), absoluteAdapterPosition)
    }

    @CallSuper
    override fun onLongClick(view: View?): Boolean {
        onLongItemClick?.let {
            return it.onLongItemClick(view, item, absoluteAdapterPosition)
        }
        return false
    }
}