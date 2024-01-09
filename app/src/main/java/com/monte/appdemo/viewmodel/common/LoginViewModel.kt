package com.monte.appdemo.viewmodel.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.monte.appdemo.viewmodel.base.SimpleProcessViewModel
import com.monte.appdemo.views.base.utils.ProcessStep
import com.monte.domain.models.common.FormValidator
import com.monte.domain.models.common.Outcome
import com.monte.domain.models.response.Auth
import com.monte.domain.usecases.LoginUseCase
import com.monte.domain.usecases.LoginValidatorUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginValidatorUseCase: LoginValidatorUseCase,
    private val loginUseCase: LoginUseCase
) : SimpleProcessViewModel() {

    private val _validForm = MutableLiveData<FormValidator?>()
    val validFormData: LiveData<FormValidator?> get() = _validForm
    val userErrorData = MutableLiveData<FormValidator?>()
    val passErrorData = MutableLiveData<FormValidator?>()

    private fun cleanValidators(){
        userErrorData.value = null
        passErrorData.value = null
    }

    fun validateForm(user: String, password: String) {
        cleanValidators()
        when (val result = loginValidatorUseCase(user, password).value) {
            is FormValidator.Continue ->
                _validForm.value = result
            is FormValidator.UserError ->
                userErrorData.value = result
            is FormValidator.PassError ->
                passErrorData.value = result
            else -> Unit
        }
    }

    private val _login = MutableLiveData<ProcessStep<Auth>?>()
    val loginData: LiveData<ProcessStep<Auth>?> get() = _login

    fun login(identifier: String, password: String) = viewModelScope.launch {
        _login.value = ProcessStep.Loading()
        when (val result = loginUseCase(identifier, password)) {
            is Outcome.Success -> {
                _login.value = ProcessStep.Finished(result.data)
            }
            is Outcome.Error   ->
                _login.value = ProcessStep.Error(result.message)
            else -> Unit
        }
    }

}