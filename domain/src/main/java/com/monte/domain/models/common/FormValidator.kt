package com.monte.domain.models.common

sealed class FormValidator(var message: String? = null){
    object Continue : FormValidator(null)
    data class UserError(var errorMsg: String? = null) : FormValidator(errorMsg)
    data class PassError(var errorMsg: String? = null) : FormValidator(errorMsg)
}
