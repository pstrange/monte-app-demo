package com.monte.appdemo.viewmodel.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.monte.appdemo.viewmodel.base.SimpleProcessViewModel
import com.monte.appdemo.views.base.utils.ProcessStep
import com.monte.domain.models.common.FormValidator
import com.monte.domain.models.common.Outcome
import com.monte.domain.models.response.CalcResult
import com.monte.domain.models.response.Price
import com.monte.domain.usecases.CalculatorUseCase
import com.monte.domain.usecases.GetPricesUseCase
import com.monte.domain.usecases.LoginValidatorUseCase
import kotlinx.coroutines.launch

class CalculatorViewModel(
    private val loginValidatorUseCase: LoginValidatorUseCase,
    private val getPricesUseCase: GetPricesUseCase,
    private val calculatorUseCase: CalculatorUseCase
) : SimpleProcessViewModel() {

    private val _validForm = MutableLiveData<FormValidator?>()
    val validFormData: LiveData<FormValidator?> get() = _validForm
    val typeErrorData = MutableLiveData<FormValidator?>()
    val weightErrorData = MutableLiveData<FormValidator?>()

    private fun cleanValidators(){
        typeErrorData.value = null
        weightErrorData.value = null
    }

    fun validateForm(type: String, weight: String) {
        cleanValidators()
        when (val result = loginValidatorUseCase(type, weight).value) {
            is FormValidator.Continue ->
                _validForm.value = result
            is FormValidator.UserError ->
                typeErrorData.value = result
            is FormValidator.PassError ->
                weightErrorData.value = result
            else -> Unit
        }
    }

    private val _calculate = MutableLiveData<ProcessStep<CalcResult>?>()
    val calculateData: LiveData<ProcessStep<CalcResult>?> get() = _calculate

    fun calculate(identifier: String, weight: String) = viewModelScope.launch {
        _calculate.value = ProcessStep.Loading()
        when (val result = calculatorUseCase(identifier, weight.toInt())) {
            is Outcome.Success -> {
                _calculate.value = ProcessStep.Finished(result.data)
            }
            is Outcome.Error   ->
                _calculate.value = ProcessStep.Error(result.message)
            else -> Unit
        }
    }

    private val _catalogue = MutableLiveData<ProcessStep<List<Price>>?>()
    val catalogueData: LiveData<ProcessStep<List<Price>>?> get() = _catalogue
    var pricesData: List<Price>? = null

    fun getCatalogue() = viewModelScope.launch {
        _catalogue.value = ProcessStep.Loading()
        when (val result = getPricesUseCase()) {
            is Outcome.Success -> {
                _catalogue.value = ProcessStep.Finished(result.data)
            }
            is Outcome.Error   ->
                _catalogue.value = ProcessStep.Error(result.message)
            else -> Unit
        }
    }
}