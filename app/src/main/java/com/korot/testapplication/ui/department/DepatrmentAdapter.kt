package com.korot.testapplication.ui.department

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.korot.testapplication.R
import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.ui.department.model.DepartmentItem
import java.lang.Exception
import kotlin.math.E

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

    private val items : MutableList<DepartmentItem> = ArrayList<DepartmentItem>()

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
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class DepatrmentViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val viewName : AppCompatTextView = view.findViewById(R.id.view_list_department_name)
        private val viewId : AppCompatTextView = view.findViewById(R.id.view_list_department_id)

        fun bind(depatrment: Department){
            viewName.setText(depatrment.name)
            viewId.setText(depatrment.id.toString())
        }
    }

}