package com.monte.data.di

import com.monte.data.mappers.*
import org.koin.dsl.module

val mappersModule = module {

	single { CalculatorMapper() }
	single { LoginMapper() }
	single { CatalogueMapper() }

}