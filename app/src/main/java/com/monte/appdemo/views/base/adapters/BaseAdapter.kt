package com.monte.appdemo.views.base.adapters

import android.view.animation.AnimationUtils
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.monte.appdemo.views.base.utils.basicDiffUtil

@Suppress("LeakingThis")
abstract class BaseAdapter<T, B: ViewDataBinding> : RecyclerView.Adapter<BaseViewHolder<T, B>>(){

    var onRecyclerItemClick: OnRecyclerItemClick<T>? = null
    var items: List<T> by basicDiffUtil(
        areItemsTheSame = evaluateAreItemsTheSame(),
        areContentsTheSame = evaluateAreContentTheSame())

    override fun getItemCount(): Int {
        return items.size
    }

    @CallSuper
    override fun onBindViewHolder(holder: BaseViewHolder<T, B>, position: Int) {
        val res = animationResource()
        if(res != -1) holder.binder.root.animation =
            AnimationUtils.loadAnimation(holder.binder.root.context, res)
        holder.onItemClick = onRecyclerItemClick
        holder.onBindViewHolder(items[position])
    }

    open fun animationResource() : Int = -1
    open fun evaluateAreItemsTheSame() : (oldItem: T, newItem: T) -> Boolean = { old, new -> old == new }
    open fun evaluateAreContentTheSame() : (oldItem: T, newItem: T) -> Boolean = { old, new -> old == new }
}