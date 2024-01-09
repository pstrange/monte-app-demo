package com.monte.appdemo.di

import com.monte.appdemo.viewmodel.common.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { SplashViewModel(
        sessionUseCase = get()) }

}