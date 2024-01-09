package com.monte.appdemo.viewmodel.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.monte.appdemo.viewmodel.base.SimpleProcessViewModel
import com.monte.domain.models.common.Outcome
import com.monte.domain.models.common.StepResult
import com.monte.domain.usecases.SessionUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SplashViewModel(
    private val sessionUseCase: SessionUseCase,
) : SimpleProcessViewModel() {

    private val _view = MutableLiveData<StepResult?>()
    val viewData: LiveData<StepResult?> get() = _view

    fun validateNextStep() = viewModelScope.launch {
        Calendar.getInstance().timeInMillis.let { startTime ->
            when (val result = sessionUseCase()) {
                is Outcome.Success -> {
                    delay(getRemainingTime(startTime))
                    _view.value = result.value
                }
                else -> Unit
            }
        }
    }

    private fun getRemainingTime(startTime: Long, splashDurationTime: Long = 3000L): Long{
        val totalTime = Calendar.getInstance().timeInMillis - startTime
        return if(totalTime in 1 until splashDurationTime)
            splashDurationTime - totalTime else 0L
    }

}