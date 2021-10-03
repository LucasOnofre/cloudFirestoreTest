package com.onoffrice.android.cloudfirestoretest

import java.io.Serializable

data class UserInfo(
    var name: String? = null,
    var age: String? = null
) : Serializable
