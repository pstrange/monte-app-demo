package com.monte.appdemo.views.base.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.monte.appdemo.R
import com.monte.appdemo.databinding.DialogLoaderProgressBinding

object ProgressDialog {

    private var loaderProgressDialog: LoaderProgressDialog? = null

    fun show(
        fragmentManager: FragmentManager,
        message: String? = null,
    ) {
        dismiss()
        try {
            loaderProgressDialog = LoaderProgressDialog.newInstance(message).also {
                it.show(fragmentManager, "progress_dialog")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun dismiss() {
        try {
            loaderProgressDialog?.let {
                loaderProgressDialog?.dismissAllowingStateLoss()
                loaderProgressDialog = null
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

}

class LoaderProgressDialog : DialogFragment() {

    private val message by lazy {
        arguments?.getString(MESSAGE) ?: getString(R.string.alert_title_loading)
    }

    companion object {
        private const val MESSAGE = "message"
        fun newInstance(message: String? = null) =
            LoaderProgressDialog().apply {
                if (message != null)
                    arguments = Bundle().apply {
                        putString(MESSAGE, message)
                    }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCancelable(false)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.attributes?.windowAnimations = R.style.DialogFragmentAnimation
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogLoaderProgressBinding.inflate(inflater, container, false)
        binding.message = message
        return binding.root
    }

}
