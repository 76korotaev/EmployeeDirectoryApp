package com.korot.testapplication.ui.department

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.korot.testapplication.R
import com.korot.testapplication.ui.base.BaseFragment
import com.korot.testapplication.ui.base.BaseActivity
import com.korot.testapplication.ui.department.model.DepartmentItem
import com.korot.testapplication.ui.employee.EmployeeFragment

class DepartmentFragment(fragmentProvider: BaseActivity) :
    BaseFragment(fragmentProvider) {

    private lateinit var viewList: RecyclerView
    private lateinit var viewLogin: AppCompatTextView
    private lateinit var viewLogout: AppCompatImageView

    private val model = DepartmentViewModel(loader)
    private var adapter: DepatrmentAdapter = DepatrmentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.observer.observe(this, Observer {
            viewList.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            viewList.adapter = adapter
            adapter.setItems(listOf(DepartmentItem(department = it)))
            adapter.setEmployeeClickListener { employee ->
                val items = adapter.getItems()
                val department =
                    items.firstOrNull { it.department?.id == employee.departmentId }?.department
                loader.addFragment(
                    EmployeeFragment(
                        loader,
                        employee,
                        department?.name ?: "Неизвестный департамент"
                    )
                )
            }
        })

        model.loginObserver.observe(this, Observer {
            viewLogin.setText(it)
        })
        model.loadEmployee()
        model.getLogin()


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot = inflater.inflate(R.layout.fragment_department, container, false)
        viewList = viewRoot.findViewById(R.id.view_department_list)
        viewLogin = viewRoot.findViewById(R.id.view_appbar_login)
        viewLogout = viewRoot.findViewById(R.id.view_appbar_logout)
        viewLogout.setOnClickListener {
            loader.logout()
        }
        return viewRoot
    }
}