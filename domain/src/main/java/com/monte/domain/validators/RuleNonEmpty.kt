package com.monte.domain.validators

import com.wajahatkarim3.easyvalidation.core.rules.BaseRule

class RuleNonEmpty(private var error: String = ""): BaseRule {

    override fun validate(text: String) : Boolean = text.isNotEmpty()

    override fun getErrorMessage(): String = error
    override fun setError(msg: String) { error = msg }

}