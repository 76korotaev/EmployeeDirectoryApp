package com.korot.testapplication.network.apimodel

import com.google.gson.annotations.SerializedName


data class LoginResponse (
    @SerializedName("Message")
    val message: String,

    @SerializedName("Success")
    val isSuccess: Boolean
)