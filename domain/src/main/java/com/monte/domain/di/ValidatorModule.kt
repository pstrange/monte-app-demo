package com.monte.domain.di

import com.monte.domain.R
import com.monte.domain.validators.RuleNonEmpty
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val validatorModule = module {
	single { RuleNonEmpty(
		androidContext().getString(R.string.error_empty_msj)) }
}