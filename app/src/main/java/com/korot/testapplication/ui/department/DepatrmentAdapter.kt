package com.korot.testapplication.ui.department

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.korot.testapplication.R
import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.domain.model.Employee
import com.korot.testapplication.ui.department.model.DepartmentItem

class DepatrmentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var employeeClickListener : ((Employee) -> Unit)? = null

    fun setEmployeeClickListener(listener: (Employee) -> Unit){
        employeeClickListener = listener
    }

    fun getItems(): List<DepartmentItem>{
        return items
    }

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
        val parentDep = items.firstOrNull { it.department?.id == idParent }
        if (parentDep!= null){
            val index = items.indexOf(parentDep)
            items.addAll(index+1, childs)
            notifyItemRangeInserted(index+1, childs.size)
            items.removeAt(index)
            items.add(index, parentDep.copy(isOpen = true))
            notifyItemChanged(index)
        }
    }

    fun openDepartment(depatrment: Department, hierarchyLavel: Int){
        if (!depatrment.department.isNullOrEmpty()){
            addItemsInParent(depatrment.department.map { DepartmentItem(department = it, parentId = depatrment.id, hierarchyLavel = hierarchyLavel) }, depatrment.id)
        } else {
            addItemsInParent(depatrment.employees.map { DepartmentItem(employee = it, parentId = depatrment.id, hierarchyLavel = hierarchyLavel) }, depatrment.id)
        }
    }

    fun closeDepartment(depatrment: Department){
        val parentDep = items.firstOrNull { it.department?.id == depatrment.id }
        parentDep?.let { notNullDep ->
            val index = items.indexOf(notNullDep)
            items.removeAt(index)
            items.add(index, notNullDep.copy(isOpen = false))
            notifyItemChanged(index)
        }


        val allChildsId = getAllChild(depatrment.id)
        val listItemsRemoved = ArrayList<DepartmentItem>()

        items.forEach{departmentItem ->
            if (departmentItem.department?.id in allChildsId || departmentItem.employee?.id in allChildsId){
                listItemsRemoved.add(departmentItem)
            }
        }
        listItemsRemoved.forEach {
            val index = items.indexOf(it)
            items.remove(it)
            notifyItemRemoved(index)
        }

    }

    private fun getAllChild(idParent: Int):List<Int>{
        val allChilds = ArrayList<Int>()
        val childs = items.filter { it.parentId == idParent }
        allChilds.addAll(childs.map { it.department?.id ?: it.employee?.id ?: -1 })
        childs.forEach {
            it.department?.id?.let { allChilds.addAll(getAllChild(it)) }
        }
        return allChilds
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
            is DepatrmentViewHolder -> holder.bind(item.department, item.isOpen, item.hierarchyLavel)
            is EmployeeViewHolder -> holder.bind(item.employee)
        }
    }

    inner class DepatrmentViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val viewName : AppCompatTextView = view.findViewById(R.id.view_list_department_name)
        private val viewId : AppCompatTextView = view.findViewById(R.id.view_list_department_id)
        private val viewIcon: AppCompatImageView = view.findViewById(R.id.view_list_department_icon)
        private val viewRoot: CardView = view.findViewById(R.id.view_list_department_view)


        fun bind(depatrment: Department?, isOpen: Boolean, hierarchyLavel: Int){
            viewName.setText(depatrment?.name)
            viewId.setText(depatrment?.id.toString())
            if(isOpen){
                viewIcon.setImageResource(R.drawable.ic_up_24px)
            } else {
                viewIcon.setImageResource(R.drawable.ic_down_24px)
            }
            val layoutParams =  LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(16*hierarchyLavel, 4, 0, 4)

            viewRoot.layoutParams = layoutParams
            viewRoot.requestLayout()

            itemView.setOnClickListener {
                depatrment?.let { notNullDepartment ->
                    if (isOpen){
                        closeDepartment(depatrment)
                    } else {
                        openDepartment(notNullDepartment, hierarchyLavel+1)
                    }
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
            itemView.setOnClickListener {
                employee?.let {
                    employeeClickListener?.invoke(it)
                }
            }
        }

        fun loadImage(url: String){

        }
    }

}