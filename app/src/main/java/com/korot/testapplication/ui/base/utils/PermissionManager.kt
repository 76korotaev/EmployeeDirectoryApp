package com.korot.testapplication.ui.base.utils

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment


class PermissionManager(val fragment: Fragment){

    companion object {
        val RequestCodeCall = 101
        val RequestCodeEmail = 102
    }

    fun callPermission(callbacks: () -> Unit){

        fragment.context?.let {context ->
            if (checkSelfPermission(context, CALL_PHONE) == PERMISSION_GRANTED) {
                callbacks.invoke()
            } else {
                fragment.requestPermissions(arrayOf(CALL_PHONE), RequestCodeCall)
            }
        }
    }

    fun emailPermission(callbacks: () -> Unit){

    }

    private fun isCallPermission(){

    }

    private fun isEmailPermission(){}

}