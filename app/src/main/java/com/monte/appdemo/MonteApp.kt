package com.monte.appdemo

import android.app.Application
import com.monte.appdemo.di.viewModelModule
import com.monte.data.di.mappersModule
import com.monte.data.di.preferencesModule
import com.monte.data.di.repositoriesModule
import com.monte.data.di.serviceModule
import com.monte.data.local.PreferenceComponent_PrefsComponent
import com.monte.domain.di.useCasesModule
import com.monte.domain.di.validatorModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MonteApp : Application() {

	override fun onCreate() {
		super.onCreate()

		PreferenceComponent_PrefsComponent.init(this)

		startKoin {
			androidContext(this@MonteApp)
			modules(
				serviceModule,
				preferencesModule,
				mappersModule,
				repositoriesModule,
				validatorModule,
				useCasesModule,
				viewModelModule)
		}
	}

}