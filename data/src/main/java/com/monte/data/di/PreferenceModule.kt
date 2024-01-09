package com.monte.data.di

import com.monte.data.local.PreferenceComponent_PrefsComponent
import org.koin.dsl.module

val preferencesModule = module {

    single { PreferenceComponent_PrefsComponent.getInstance().AppConfigs() }

}