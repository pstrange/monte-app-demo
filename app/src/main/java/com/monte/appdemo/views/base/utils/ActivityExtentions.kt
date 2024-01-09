package com.monte.appdemo.views.base.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher

inline fun <reified T : Activity> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

inline fun <reified T : Activity> Context.openActivity(
    args: Bundle? = null,
    flags: Int = Intent.FLAG_ACTIVITY_SINGLE_TOP,
    noinline init: Intent.() -> Unit = {}
) {
    startActivity(newIntent<T>(this).apply {
        if (args != null)
            putExtras(args)

//        this.flags = flags
    })
}

inline fun <reified T : Activity> Context.openActivityAndClear(
    args: Bundle? = null,
    flags: Int = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK,
    noinline init: Intent.() -> Unit = {}
) {
    startActivity(newIntent<T>(this).apply {
        if (args != null)
            putExtras(args)

        this.flags = flags
    })
}

inline fun <reified T : Activity> Context.openActivityForResult(
    launcher: ActivityResultLauncher<Intent>,
    noinline init: Intent.() -> Unit = {}){
    val intent = newIntent<T>(this)
    intent.init()
    launcher.launch(intent)
}
