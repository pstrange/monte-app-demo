package com.monte.appdemo.views.fragments

import android.view.View
import android.view.View.OnClickListener
import com.monte.appdemo.R
import com.monte.appdemo.databinding.FragmentLoginBinding
import com.monte.appdemo.viewmodel.common.LoginViewModel
import com.monte.appdemo.views.base.dialogs.MessageDialog
import com.monte.appdemo.views.base.fragmens.BaseFragment
import com.monte.appdemo.views.base.utils.ProcessStep
import com.monte.domain.models.common.FormValidator
import com.monte.domain.models.response.Auth
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login), OnClickListener {

	private val viewModel: LoginViewModel by viewModel()

	override fun setupViews() {
		binding.inputEmail.error = viewModel.userErrorData
		binding.inputPass.error = viewModel.passErrorData
		binding.lifecycleOwner = this

		binding.buttonContinue.setOnClickListener(this)
		binding.inputPass.iconInput.setOnClickListener(this)
	}

	override fun setupObservers() {
		viewModel.validFormData.observe(this, ::validationObserver)
		viewModel.loginData.observe(this, ::loginObserver)
	}

	override fun onClick(view: View) {
		when(view.id){
			R.id.button_continue -> {
				viewModel.validateForm(
					binding.inputEmail.email.text.toString(),
					binding.inputPass.password.text.toString())
			}
			R.id.icon_input -> {
				binding.inputPass.showPass = binding.inputPass.showPass != true
			}
			else -> Unit
		}
	}

	private fun validationObserver(result: FormValidator?){
		when(result){
			is FormValidator.Continue -> {
				viewModel.login(
					binding.inputEmail.email.text.toString(),
					binding.inputPass.password.text.toString())
			}
			else -> Unit
		}
	}

	private fun loginObserver(step: ProcessStep<Auth>?){
		when(step){
			is ProcessStep.Loading  ->
				showProgressDialog()
			is ProcessStep.Error    -> {
				dismissProgressDialog()
				MessageDialog.show(
					requireActivity().supportFragmentManager,
					title = getString(R.string.alert_title_error),
					message = step.message)
			}
			is ProcessStep.Finished -> {
				dismissProgressDialog()
//				requireActivity().openActivity<HomeActivity>()
				requireActivity().finish()
			}
			else                    -> Unit
		}
	}
}