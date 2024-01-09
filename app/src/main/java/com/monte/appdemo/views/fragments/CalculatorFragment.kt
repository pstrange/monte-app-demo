package com.monte.appdemo.views.fragments

import android.view.View
import android.view.View.OnClickListener
import com.monte.appdemo.R
import com.monte.appdemo.databinding.FragmentCalculatorBinding
import com.monte.appdemo.viewmodel.common.CalculatorViewModel
import com.monte.appdemo.views.base.dialogs.MessageDialog
import com.monte.appdemo.views.base.fragmens.BaseFragment
import com.monte.appdemo.views.base.utils.ProcessStep
import com.monte.appdemo.views.base.utils.showListPopUp
import com.monte.domain.models.common.FormValidator
import com.monte.domain.models.response.CalcResult
import com.monte.domain.models.response.Price
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalculatorFragment : BaseFragment<FragmentCalculatorBinding>(R.layout.fragment_calculator), OnClickListener {

	private val viewModel: CalculatorViewModel by viewModel()

	override fun setupViews() {
		binding.inputType.error = viewModel.typeErrorData
		binding.inputWeight.error = viewModel.weightErrorData
		binding.lifecycleOwner = this

		binding.inputType.type.setOnClickListener {
			viewModel.pricesData?.let { data ->
				requireActivity().showListPopUp(
					binding.inputType.type, data.map { it.name }.toTypedArray()
				) { _, _, index, _ ->
					binding.inputType.type.tag = data[index].identifier
					binding.inputType.type.setText(data[index].name)
				}
			}
		}

		binding.buttonContinue.setOnClickListener(this)
	}

	override fun setupObservers() {
		viewModel.validFormData.observe(this, ::validationObserver)
		viewModel.catalogueData.observe(this, ::cataloguesObserver)
		viewModel.calculateData.observe(this, ::calculatorObserver)

		viewModel.getCatalogue()
	}

	override fun onClick(view: View) {
		when(view.id){
			R.id.button_continue -> {
				viewModel.validateForm(
					binding.inputType.type.text.toString(),
					binding.inputWeight.weight.text.toString())
			}
			else -> Unit
		}
	}

	private fun validationObserver(result: FormValidator?){
		when(result){
			is FormValidator.Continue -> {
				viewModel.calculate(
					binding.inputType.type.tag.toString(),
					binding.inputWeight.weight.text.toString())
			}
			else -> Unit
		}
	}

	private fun cataloguesObserver(step: ProcessStep<List<Price>>?){
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
				viewModel.pricesData = step.data?.toList()
			}
			else -> Unit
		}
	}

	private fun calculatorObserver(step: ProcessStep<CalcResult>?){
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
				MessageDialog.show(
					requireActivity().supportFragmentManager,
					title = getString(R.string.alert_title_success),
					message = "$${step.data?.total}")
			}
			else -> Unit
		}
	}
}