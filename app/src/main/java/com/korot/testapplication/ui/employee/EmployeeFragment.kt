package com.korot.testapplication.ui.employee

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.korot.testapplication.R
import com.korot.testapplication.domain.model.Employee
import com.korot.testapplication.ui.base.BaseFragment
import com.korot.testapplication.ui.base.BaseActivity
import com.korot.testapplication.ui.base.utils.NativeFunctionHelper
import com.korot.testapplication.ui.base.utils.PermissionManager
import com.squareup.picasso.Picasso

class EmployeeFragment(
    baseActivity: BaseActivity, val employee: Employee, val departmentName: String
) : BaseFragment(baseActivity) {

    lateinit var viewDepartment: AppCompatTextView
    lateinit var viewFIO: AppCompatTextView
    lateinit var viewPhone: TextInputEditText
    lateinit var viewEmail: TextInputEditText
    lateinit var viewTitle: AppCompatTextView
    lateinit var viewId: AppCompatTextView
    lateinit var viewIcon: AppCompatImageView
    lateinit var viewBack: AppCompatImageView
    private val model = EmployeeViewModel(baseActivity)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot = inflater.inflate(R.layout.fragment_employee, container, false)
        viewDepartment = viewRoot.findViewById(R.id.view_appbar_department)
        viewEmail = viewRoot.findViewById(R.id.view_employee_email)
        viewPhone = viewRoot.findViewById(R.id.view_employee_phone)
        viewFIO = viewRoot.findViewById(R.id.view_employee_name)
        viewTitle = viewRoot.findViewById(R.id.view_employee_title)
        viewIcon = viewRoot.findViewById(R.id.view_employee_icon)
        viewId = viewRoot.findViewById(R.id.view_employee_id)
        viewBack = viewRoot.findViewById(R.id.view_appbar_back)

        viewBack.setOnClickListener {
            loader.back()
        }

        viewPhone.setOnClickListener {
            callPhone()
        }

        viewEmail.setOnClickListener {
            if (!employee.email.isNullOrEmpty() && context != null) {
                NativeFunctionHelper.sendEmail(employee.email, context!!)
            }
        }

        model.photoObserver.observe(viewLifecycleOwner, Observer {
            Picasso.get()
                .load(it)
                .resize(200, 200)
                .into(viewIcon)
        })

        model.loadPhoto(employee.id)

        bind()
        return viewRoot
    }

    private fun bind() {

        viewDepartment.setText(departmentName)
        viewId.setText(employee.id.toString())
        viewTitle.setText(employee.title)
        viewFIO.setText(employee.name)
        viewPhone.setText(employee.phone)
        viewEmail.setText(employee.email)

    }

    private fun callPhone(){
        if (!employee.phone.isNullOrEmpty() && context != null) {
            NativeFunctionHelper.callPhone(employee.phone, this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PermissionManager.RequestCodeCall){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                callPhone()
            }
        }
    }


}