package com.korot.testapplication.ui.base.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

object NativeFunctionHelper {

    fun callPhone(phone: String, fragment: Fragment) {
        fragment.context?.let {
            PermissionManager(fragment).callPermission {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:$phone"))
                val chooser = Intent.createChooser(intent, "Вызов абонента $phone")
                it.startActivity(chooser)
            }
        }

    }

    fun sendEmail(email: String, context: Context) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:$email"))
        val chooser = Intent.createChooser(intent, "Выберите прилоение для отправки почты")
        context.startActivity(chooser)
    }

}