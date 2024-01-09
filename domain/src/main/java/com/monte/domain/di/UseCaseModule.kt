package com.monte.domain.di

import com.monte.domain.usecases.*
import org.koin.dsl.module

val useCasesModule = module {

    single { LoginUseCase(
        hostApi = get(),
        preferences = get()) }

    single { LoginValidatorUseCase(
        nonEmpty = get()) }

    single { GetPricesUseCase(
        hostApi = get()) }

    single { CalculatorUseCase(
        hostApi = get()) }

    single { SessionUseCase(
        preferences = get()) }

}