package com.korot.testapplication.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.korot.testapplication.R
import com.korot.testapplication.ui.MainActivity
import com.korot.testapplication.ui.base.BaseFragment
import com.korot.testapplication.ui.base.ConsumerFragmentProvider
import com.korot.testapplication.ui.base.LoaderInterface
import com.korot.testapplication.ui.department.DepartmentFragment

class AuthFragment(loader: ConsumerFragmentProvider): BaseFragment(loader) {

    lateinit var viewLogin: TextInputEditText
    lateinit var viewPassword : TextInputEditText
    lateinit var viewOk : AppCompatButton
    val model = AuthViewModel(loader)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot = inflater.inflate(R.layout.fragment_auth,container,false)
        viewLogin = viewRoot.findViewById(R.id.view_auth_login)
        viewPassword = viewRoot.findViewById(R.id.view_auth_password)
        viewOk = viewRoot.findViewById(R.id.view_auth_ok)

        viewOk.setOnClickListener {
            model.login(viewLogin.text.toString(), viewPassword.text.toString())
        }
        return viewRoot
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.observer.observe(this, Observer {
            when{
                it -> loader.getProvider().endAuth()
            }
        })
    }



}