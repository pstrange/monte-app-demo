package com.monte.domain.usecases

import com.monte.domain.models.common.FormValidator
import com.monte.domain.models.common.Outcome
import com.monte.domain.utils.asOutcome
import com.monte.domain.validators.FieldListValidator
import com.monte.domain.validators.FieldValidator
import com.monte.domain.validators.RuleNonEmpty

class LoginValidatorUseCase(
	private val nonEmpty: RuleNonEmpty
) {

    operator fun invoke(identifier: String, password: String) : Outcome<FormValidator> {
        return FieldListValidator(
            listOf(
                Pair(
	                FormValidator.UserError(),
	                FieldValidator(identifier)
                        .addRule(nonEmpty)),
                Pair(
	                FormValidator.PassError(),
	                FieldValidator(password)
		                .addRule(nonEmpty))
            )
        ).check().asOutcome()
    }

}