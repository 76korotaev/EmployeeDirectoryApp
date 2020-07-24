package com.korot.testapplication.ui.department

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.korot.testapplication.R

class DepartmentFragment: Fragment() {

    lateinit var viewList: RecyclerView
    private val model = DepartmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.observer.observe(this, Observer {
            //todo тут в адаптер будем записывать
        })
        model.loadEmployee()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot = inflater.inflate(R.layout.fragment_auth,container)
        viewList = viewRoot.findViewById(R.id.view_department_list)
        return viewRoot
    }
}