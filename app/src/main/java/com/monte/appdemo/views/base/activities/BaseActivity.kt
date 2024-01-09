package com.monte.appdemo.views.base.activities

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.monte.appdemo.views.base.dialogs.ProgressDialog

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    AppCompatActivity() {

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    fun showProgressDialog(msg: String? = null) {
        try {
            ProgressDialog.show(supportFragmentManager, msg)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun dismissProgressDialog() {
        ProgressDialog.dismiss()
    }

}