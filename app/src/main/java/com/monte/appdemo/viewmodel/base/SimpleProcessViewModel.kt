package com.monte.appdemo.viewmodel.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monte.appdemo.views.base.utils.ProcessStep
import kotlinx.coroutines.cancelChildren

open class SimpleProcessViewModel: ViewModel() {

    @Suppress("PropertyName")
    protected val _step = MutableLiveData<ProcessStep<Any>?>()
    val stepData: LiveData<ProcessStep<Any>?> get() = _step

    protected fun process(onLoading: () -> Unit) {
        _step.value = ProcessStep.Loading()
        onLoading()
        _step.value = null
    }

    override fun onCleared() {
        viewModelScope.coroutineContext.cancelChildren()
    }
}