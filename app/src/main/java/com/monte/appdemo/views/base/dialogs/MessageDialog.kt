package com.monte.appdemo.views.base.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.FragmentManager
import com.monte.appdemo.R
import com.monte.appdemo.databinding.DialogSimpleMessageBinding
import com.monte.appdemo.views.base.fragmens.BaseBottomSheetDialogFragment

object MessageDialog {

    private var messageDialog: SimpleMessageDialog? = null

    fun show(
        fragmentManager: FragmentManager,
        title: String? = null,
        message: String? = null,
        cancelLbl: String? = null,
        acceptLbl: String? = null,
        showCancel: Boolean = false,
        onCancelEvent: ((dialog: Dialog) -> Unit)? = null,
        onAcceptEvent: ((dialog: Dialog) -> Unit)? = null,
        onDismissEvent: ((dialog: DialogInterface) -> Unit)? = null
    ) {
        dismiss()
        try {
            messageDialog = SimpleMessageDialog.newInstance(
                title,
                message,
                cancelLbl,
                acceptLbl,
                showCancel,
                onCancelEvent,
                onAcceptEvent,
                onDismissEvent
            ).also {
                it.show(fragmentManager, "progress_dialog")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun dismiss() {
        messageDialog?.let {
            if(messageDialog?.isVisible == true)
                messageDialog?.dismissAllowingStateLoss()
            messageDialog = null
        }
    }

}

class SimpleMessageDialog : BaseBottomSheetDialogFragment<DialogSimpleMessageBinding>(R.layout.dialog_simple_message) {

    companion object {
        private const val TITLE = "title"
        private const val MESSAGE = "message"
        private const val CANCEL_LBL = "cancel_lbl"
        private const val ACCEPT_LBL = "accept_lbl"
        private const val SHOW_CANCEL = "show_cancel"
        fun newInstance(
            title: String? = null,
            message: String? = null,
            cancelLbl: String? = null,
            acceptLbl: String? = null,
            showCancel: Boolean = false,
            onCancelEvent: ((dialog: Dialog) -> Unit)? = null,
            onAcceptEvent: ((dialog: Dialog) -> Unit)? = null,
            onDismissEvent: ((dialog: DialogInterface) -> Unit)? = null) =
            SimpleMessageDialog().apply {
                this.onCancelEvent = onCancelEvent
                this.onAcceptEvent = onAcceptEvent
                this.onDismissEvent = onDismissEvent
                if (message != null)
                    arguments = Bundle().apply {
                        putString(TITLE, title)
                        putString(MESSAGE, message)
                        putString(CANCEL_LBL, cancelLbl)
                        putString(ACCEPT_LBL, acceptLbl)
                        putBoolean(SHOW_CANCEL, showCancel)
                    }
            }
    }

    var onCancelEvent: ((dialog: Dialog) -> Unit)? = null
    var onAcceptEvent: ((dialog: Dialog) -> Unit)? = null
    var onDismissEvent: ((dialog: DialogInterface) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun setupView() {
        binding.title = arguments?.getString(TITLE)
        binding.message = arguments?.getString(MESSAGE)
        binding.cancelLabel = arguments?.getString(CANCEL_LBL)
        binding.acceptLabel = arguments?.getString(ACCEPT_LBL)
        binding.showCancel = arguments?.getBoolean(SHOW_CANCEL)

        binding.buttonCancel.setOnClickListener {
            onCancelEvent?.invoke(requireDialog())
            dismiss() }
        binding.buttonAccept.setOnClickListener {
            onAcceptEvent?.invoke(requireDialog()) ?: run { dismiss() }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDismissEvent?.invoke(dialog)
        super.onDismiss(dialog)
    }
}
