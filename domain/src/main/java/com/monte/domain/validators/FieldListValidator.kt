package com.monte.domain.validators

import com.monte.domain.models.common.FormValidator

class FieldListValidator(private val validators : List<Pair<FormValidator, FieldValidator>>) {
    fun check() : FormValidator {
        validators.forEach{
            if(!it.second.check()){
                it.first.message = it.second.getError()
                return it.first
            }
        }
        return FormValidator.Continue
    }
}