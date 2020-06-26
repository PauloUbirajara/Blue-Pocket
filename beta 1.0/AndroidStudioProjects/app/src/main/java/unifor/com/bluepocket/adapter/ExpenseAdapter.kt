package unifor.com.bluepocket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import unifor.com.bluepocket.R
import unifor.com.bluepocket.activity.ExpenseMain
import unifor.com.bluepocket.entity.Expense

class ExpenseAdapter(val context: Context, private val expenses: List<Expense>):RecyclerView.Adapter<ExpenseAdapter.ExpenseViewholder>(){

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewholder {
        val view: View = layoutInflater.inflate(R.layout.item_expense, parent, false)

        val expenseViewholder = ExpenseViewholder(context, expenses, view)
        return expenseViewholder
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    override fun onBindViewHolder(holder: ExpenseViewholder, position: Int) {
        holder.listName.text = expenses[position].name
        holder.setClickListener()
    }

    class ExpenseViewholder(val context: Context, val expenses:List<Expense>, val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        var listName:TextView = view.findViewById(R.id.item_textview_expensename)

        fun setClickListener() {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
//            val fragmentExpenseDetail = FragmentExpenseDetail(expenses[adapterPosition].expenseId)
//
//            if (context is ExpenseMain) {
//                fragmentExpenseDetail.addFragmentListener(context)
//
//                context.expenseId = expenses[adapterPosition].expenseId
//
//                context.supportFragmentManager.beginTransaction()
//                    .replace(R.id.expenses_main_framelayout, fragmentExpenseDetail)
//                    .addToBackStack(null)
//                    .commit()
//            }
        }
    }
}