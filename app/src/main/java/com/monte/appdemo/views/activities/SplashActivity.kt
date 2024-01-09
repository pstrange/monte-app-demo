package com.monte.appdemo.views.activities

import android.annotation.SuppressLint
import android.os.Bundle
import com.monte.appdemo.R
import com.monte.appdemo.databinding.ActivitySplashBinding
import com.monte.appdemo.viewmodel.common.SplashViewModel
import com.monte.appdemo.views.base.activities.BaseActivity
import com.monte.appdemo.views.base.utils.openActivity
import com.monte.domain.models.common.StepResult
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash){

	private val viewModel: SplashViewModel by viewModel()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel.viewData.observe(this, ::stepObserver)
		viewModel.validateNextStep()
	}

	private fun stepObserver(step: StepResult?){
		when(step){
			is StepResult.RequireSession -> {
				finish()
				openActivity<AuthActivity>()
			}
			is StepResult.Continue -> {
				finish()
				openActivity<HomeActivity>()
			}
			else -> Unit
		}
	}
}