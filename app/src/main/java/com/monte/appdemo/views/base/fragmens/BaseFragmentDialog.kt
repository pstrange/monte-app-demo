package com.monte.appdemo.views.base.fragmens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.monte.appdemo.R
import com.monte.appdemo.views.base.dialogs.ProgressDialog

abstract class BaseFragmentDialog<T : ViewDataBinding>(@LayoutRes val layoutId: Int) : DialogFragment() {

    lateinit var binding: T
    private var hasInitializedRootView = false
    private var rootView: View? = null

    companion object {
        @JvmStatic
        inline fun <reified T : Fragment> getInstance(vararg params: Pair<String, Any>): T =
            T::class.java.newInstance().apply { arguments = bundleOf(*params) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Theme_MonteAppDemo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        dialog?.window?.attributes?.windowAnimations = R.style.Theme_AnimDialog
        return getPersistentView(inflater, container, savedInstanceState, layoutId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        if(!hasInitializedRootView){
            hasInitializedRootView = true
            setupObservers()
        }
    }

    private fun getPersistentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, layout: Int): View? {
        savedInstanceState?.isEmpty
        if (rootView == null) {
            // Inflate the layout for this fragment
            binding = DataBindingUtil.inflate(inflater, layout, container, false)
            rootView = binding.root
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }

        return rootView
    }

    fun setupToolbar(toolBar: Toolbar? = null, title: String? = "", menu: Int? = null, listener: Toolbar.OnMenuItemClickListener? = null){
        toolBar?.let { validToolbar ->
            menu?.let {
                validToolbar.inflateMenu(menu)
                validToolbar.setOnMenuItemClickListener(listener)
            }
            validToolbar.setNavigationOnClickListener {
                dismiss()
            }
            title?.let {
                validToolbar.title = title
            }
        }
    }

    protected abstract fun setupViews()
    protected abstract fun setupObservers()

    fun showProgressDialog(msg: String? = null) {
        try {
            ProgressDialog.show(childFragmentManager, msg)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun dismissProgressDialog() {
        ProgressDialog.dismiss()
    }

}