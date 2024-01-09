package com.monte.domain.validators

import com.wajahatkarim3.easyvalidation.core.rules.*

class FieldValidator(private val text: String)
{
    /*
     * Boolean to determine whether all the validations have passed successfully!
     * If any validation check is failed, then the value to
     * false and result is returned to developer
     */
    private var isValid = true

    /*
     The error message to be sent in the error callback
     */
    private var errorMessage: String = ""

    /*
     * In case of validation error or failure, this callback is invoked
     */
    var errorCallback: ((message: String) -> Unit)? = null

    /*
     * In case of validation success, this callback is invoked
     */
    var successCallback: (() -> Unit)? = null

    /*
     * The rules list to check for validation
     */
    var rulesList = ArrayList<BaseRule>()

    /*
     * Performs the validation check and returns true or false.
     * Also invokes success and error callbacks if non null.
     */
    fun check() : Boolean
    {
        for (rule in rulesList)
        {
            if (!rule.validate(text))
            {
                setError(rule.getErrorMessage())
                break
            }
        }

        // Invoking callbacks
        if (isValid)
            successCallback?.invoke()
        else
            errorCallback?.invoke(errorMessage)

        return isValid
    }

    fun setError(message: String)
    {
        isValid = false
        errorMessage = message
    }

    fun getError() = errorMessage

    fun addRule(rule: BaseRule): FieldValidator
    {
        rulesList.add(rule)
        return this
    }

    fun addErrorCallback(callback: (message: String) -> Unit): FieldValidator
    {
        errorCallback = callback
        return this
    }

    fun addSuccessCallback(callback: () -> Unit): FieldValidator
    {
        successCallback = callback
        return this
    }

    // Rules
    fun nonEmpty(): FieldValidator
    {
        addRule(NonEmptyRule())
        return this
    }

    fun minLength(length: Int): FieldValidator
    {
        addRule(MinLengthRule(length))
        return this
    }

    fun maxLength(length: Int): FieldValidator
    {
        addRule(MaxLengthRule(length))
        return this
    }

    fun validEmail(): FieldValidator
    {
        addRule(EmailRule())
        return this
    }

    fun validNumber(): FieldValidator
    {
        addRule(ValidNumberRule())
        return this
    }

    fun greaterThan(number: Number): FieldValidator
    {
        addRule(GreaterThanRule(number))
        return this
    }

    fun greaterThanOrEqual(number: Number): FieldValidator
    {
        addRule(GreaterThanOrEqualRule(number))
        return this
    }

    fun lessThan(number: Number): FieldValidator
    {
        addRule(LessThanRule(number))
        return this
    }

    fun lessThanOrEqual(number: Number): FieldValidator
    {
        addRule(LessThanOrEqualRule(number))
        return this
    }

    fun numberEqualTo(number: Number): FieldValidator
    {
        addRule(NumberEqualToRule(number))
        return this
    }

    fun allLowerCase(): FieldValidator
    {
        addRule(AllLowerCaseRule())
        return this
    }

    fun allUpperCase(): FieldValidator
    {
        addRule(AllUpercCaseRule())
        return this
    }

    fun atleastOneUpperCase(): FieldValidator
    {
        addRule(AtLeastOneUpercCaseRule())
        return this
    }

    fun atleastOneLowerCase(): FieldValidator
    {
        addRule(AtLeastOneLowerCaseRule())
        return this
    }

    fun atleastOneNumber(): FieldValidator
    {
        addRule(AtLeastOneNumberCaseRule())
        return this
    }

    fun noNumbers(): FieldValidator
    {
        addRule(NoNumbersRule())
        return this
    }

    fun onlyNumbers(): FieldValidator
    {
        addRule(OnlyNumbersRule())
        return this
    }

    fun startWithNumber(): FieldValidator
    {
        addRule(StartsWithNumberRule())
        return this
    }

    fun startWithNonNumber(): FieldValidator
    {
        addRule(StartsWithNoNumberRule())
        return this
    }

    fun noSpecialCharacters(): FieldValidator
    {
        addRule(NoSpecialCharacterRule())
        return this
    }

    fun atleastOneSpecialCharacters(): FieldValidator
    {
        addRule(AtleastOneSpecialCharacterRule())
        return this
    }

    fun textEqualTo(target: String): FieldValidator
    {
        addRule(TextEqualToRule(target))
        return this
    }

    fun textNotEqualTo(target: String): FieldValidator
    {
        addRule(TextNotEqualToRule(target))
        return this
    }

    fun startsWith(target: String): FieldValidator
    {
        addRule(StartsWithRule(target))
        return this
    }

    fun endsWith(target: String): FieldValidator
    {
        addRule(EndsWithRule(target))
        return this
    }

    fun contains(target: String): FieldValidator
    {
        addRule(ContainsRule(target))
        return this
    }

    fun notContains(target: String): FieldValidator
    {
        addRule(NotContainsRule(target))
        return this
    }

    fun creditCardNumber(): FieldValidator
    {
        addRule(MinLengthRule(16))
        addRule(MaxLengthRule(16))
        addRule(CreditCardRule())
        return this
    }

    fun creditCardNumberWithSpaces(): FieldValidator
    {
        addRule(MinLengthRule(19))
        addRule(MaxLengthRule(19))
        addRule(CreditCardWithSpacesRule())
        return this
    }

    fun creditCardNumberWithDashes(): FieldValidator
    {
        addRule(MinLengthRule(19))
        addRule(MaxLengthRule(19))
        addRule(CreditCardWithDashesRule())
        return this
    }

    fun validUrl(): FieldValidator
    {
        addRule(ValidUrlRule())
        return this
    }

    fun regex(pattern: String): FieldValidator
    {
        addRule(RegexRule(pattern))
        return this
    }
}