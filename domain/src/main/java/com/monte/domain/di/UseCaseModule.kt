package com.monte.domain.di

import com.monte.domain.usecases.CalculatorUseCase
import com.monte.domain.usecases.GetPricesUseCase
import com.monte.domain.usecases.LoginUseCase
import com.monte.domain.usecases.SessionUseCase
import org.koin.dsl.module

val useCasesModule = module {

    single { LoginUseCase(
        hostApi = get(),
        preferences = get()) }

    single { GetPricesUseCase(
        hostApi = get()) }

    single { CalculatorUseCase(
        hostApi = get()) }

    single { SessionUseCase(
        preferences = get()) }

}