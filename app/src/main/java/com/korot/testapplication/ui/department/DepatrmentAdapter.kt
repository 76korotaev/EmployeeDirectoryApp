package com.korot.testapplication.ui.department

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.korot.testapplication.R
import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.domain.model.Employee
import com.korot.testapplication.ui.department.model.DepartmentItem
import java.lang.Exception

class DepatrmentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class DepartmentItemType(val id:Int,@LayoutRes val layoutId: Int){
        DEPARTMENT(layoutId = R.layout.list_departmenr, id = 0),
        EMPLOYEE(layoutId = R.layout.list_employee, id = 1);

        companion object{
            fun valuewOf(id: Int): DepartmentItemType{
                return when(id){
                    0 -> DEPARTMENT
                    1 -> EMPLOYEE
                    else -> throw Exception("Not found id")
                }
            }
        }
    }

    private val items : MutableList<DepartmentItem> = ArrayList()

    fun addItemsInParent(childs: List<DepartmentItem>, idParent: Int){
        val parentDep = this.items.firstOrNull { it.department?.id == idParent }
        if (parentDep!= null){
            val index = items.indexOf(parentDep) + 1
            items.addAll(index, childs)
            notifyItemRangeInserted(index, childs.size)
        }
    }

    fun openDepartment(depatrment: Department){
        if (!depatrment.department.isNullOrEmpty()){
            addItemsInParent(depatrment.department.map { DepartmentItem(department = it) }, depatrment.id)
        } else {
            addItemsInParent(depatrment.employees.map { DepartmentItem(employee = it) }, depatrment.id)
        }
    }

    fun setItems(items: List<DepartmentItem>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return when{
            item.department != null -> DepartmentItemType.DEPARTMENT.id
            item.employee != null -> DepartmentItemType.EMPLOYEE.id
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val type = DepartmentItemType.valuewOf(viewType)
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(type.layoutId, parent, false)
        return when(type){
            DepartmentItemType.DEPARTMENT -> DepatrmentViewHolder(view)
            DepartmentItemType.EMPLOYEE -> EmployeeViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when(holder){
            is DepatrmentViewHolder -> holder.bind(item.department)
            is EmployeeViewHolder -> holder.bind(item.employee)
        }
    }

    inner class DepatrmentViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val viewName : AppCompatTextView = view.findViewById(R.id.view_list_department_name)
        private val viewId : AppCompatTextView = view.findViewById(R.id.view_list_department_id)

        fun bind(depatrment: Department?){
            viewName.setText(depatrment?.name)
            viewId.setText(depatrment?.id.toString())
            itemView.setOnClickListener {
                depatrment?.let { notNullDepartment ->
                    openDepartment(notNullDepartment)
                }
            }
        }
    }

    inner class EmployeeViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val viewName : AppCompatTextView = view.findViewById(R.id.view_list_employee_name)
        private val viewId : AppCompatTextView = view.findViewById(R.id.view_list_employee_id)
        private val viewIcon : AppCompatImageView = view.findViewById(R.id.view_list_employee_icon)

        fun bind(employee: Employee?){
            viewName.setText(employee?.name)
            viewId.setText(employee?.id.toString())

        }

        fun loadImage(url: String){

        }
    }

}