package com.monte.data.di

import com.monte.data.repository.AuthRepositoryImpl
import com.monte.data.repository.LoansRepositoryImpl
import com.monte.data.repository.PreferenceRepositoryImpl
import com.monte.domain.repository.AuthRepository
import com.monte.domain.repository.LoansRepository
import com.monte.domain.repository.PreferencesRepository
import org.koin.dsl.module

val repositoriesModule = module {

    single<PreferencesRepository> {
        PreferenceRepositoryImpl(preferences = get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
	        apiAuth = get(),
	        loginMapper = get())
    }

    single<LoansRepository> {
        LoansRepositoryImpl(
	        apiLoans = get(),
	        catalogueMapper = get(),
	        calculatorMapper = get())
    }

}
