package unifor.com.bluepocket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import unifor.com.bluepocket.R
import unifor.com.bluepocket.activity.TypeMain
import unifor.com.bluepocket.entity.Type

class TypeAdapter(val context: Context, private val types: List<Type>):RecyclerView.Adapter<TypeAdapter.TypeViewholder>(){

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewholder {
        val view: View = layoutInflater.inflate(R.layout.item_type, parent, false)

        val typeViewholder = TypeViewholder(context, types, view)
        return typeViewholder
    }

    override fun getItemCount(): Int {
        return types.size
    }

    override fun onBindViewHolder(holder: TypeViewholder, position: Int) {
        holder.listName.text = types[position].name
        holder.setClickListener()
    }

    class TypeViewholder(val context: Context, val types:List<Type>, val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        var listName:TextView = view.findViewById(R.id.item_textview_typename)

        fun setClickListener() {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
//            val fragmentTypeDetail = FragmentTypeDetail(types[adapterPosition].typeId)
//
//            if (context is TypeMain) {
//                fragmentTypeDetail.addFragmentListener(context)
//
//                context.typeId = types[adapterPosition].typeId
//
//                context.supportFragmentManager.beginTransaction()
//                    .replace(R.id.types_main_framelayout, fragmentTypeDetail)
//                    .addToBackStack(null)
//                    .commit()
//            }
        }
    }
}