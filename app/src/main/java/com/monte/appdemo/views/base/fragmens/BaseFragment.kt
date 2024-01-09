package com.monte.appdemo.views.base.fragmens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.monte.appdemo.views.base.dialogs.ProgressDialog

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment() {

    lateinit var binding: T
    private var hasInitializedRootView = false
    private var rootView: View? = null

    companion object {
        @JvmStatic
        inline fun <reified T : Fragment> getInstance(vararg params: Pair<String, Any>): T =
            T::class.java.newInstance().apply { arguments = bundleOf(*params) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
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

    fun setupToolbar(toolBar: Toolbar? = null, title: String? = "", displayHomeAsUp: Boolean = true){
        toolBar?.let {
            val parentActivity = (requireActivity() as AppCompatActivity)
            parentActivity.setSupportActionBar(it)
            parentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUp)
            parentActivity.supportActionBar?.setDisplayShowTitleEnabled(!title.isNullOrEmpty())
            it.title = title
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun addMenuProvider(menuProvider: MenuProvider? = null){
        menuProvider?.let {
            val menuHost: MenuHost = (requireActivity() as AppCompatActivity)
                menuHost.addMenuProvider(
                    it, viewLifecycleOwner,
                    Lifecycle.State.RESUMED)
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