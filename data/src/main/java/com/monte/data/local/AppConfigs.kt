package com.monte.data.local

import com.skydoves.preferenceroom.*

@EncryptEntity("9B484B8FD53BC6AF")
@PreferenceEntity("AppConfigs")
open class AppConfigs {

    @KeyName("activeSession")
    @JvmField val activeSession: Boolean = false

    @KeyName("authToken")
    @JvmField val authToken: String = ""

}