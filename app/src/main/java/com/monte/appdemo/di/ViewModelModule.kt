package com.monte.appdemo.di

import com.monte.appdemo.viewmodel.common.CalculatorViewModel
import com.monte.appdemo.viewmodel.common.LoginViewModel
import com.monte.appdemo.viewmodel.common.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { SplashViewModel(
        sessionUseCase = get()) }

    viewModel { LoginViewModel(
        loginValidatorUseCase = get(),
        loginUseCase = get()) }

    viewModel { CalculatorViewModel(
        loginValidatorUseCase = get(),
        getPricesUseCase = get(),
        calculatorUseCase = get()) }

}