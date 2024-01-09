package com.monte.appdemo.views.activities

import android.annotation.SuppressLint
import android.os.Bundle
import com.monte.appdemo.R
import com.monte.appdemo.databinding.ActivityAuthBinding
import com.monte.appdemo.views.base.activities.BaseActivity

@SuppressLint("CustomSplashScreen")
class AuthActivity : BaseActivity<ActivityAuthBinding>(R.layout.activity_auth){

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

}