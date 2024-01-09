package com.monte.domain.models.common

import android.os.Bundle

sealed class StepResult{
    object FirstOpen : StepResult()
    data class RequireSession(val args: Bundle? = null) : StepResult()
    data class Continue(val args: Bundle? = null) : StepResult()
    data class DeeplinkReferred(val args: Bundle? = null) : StepResult()
}
