package br.unifor.cct.droidtodo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import unifor.com.bluepocket.R
import unifor.com.bluepocket.adapter.TypeAdapter
import unifor.com.bluepocket.entity.Type
import unifor.com.bluepocket.util.IFragmentListener

class FragmentTypeList(private var userId: String) : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatButton: FloatingActionButton

    private lateinit var mDatabase: FirebaseDatabase

    private var fragmentListener: IFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_type_list, container, false)

        floatButton = view.findViewById(R.id.fragment_type_list_floatingbutton_add)
        recyclerView = view.findViewById(R.id.fragment_type_recyclerview_list)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        floatButton.setOnClickListener{
            fragmentListener?.onFragmentClick(it)
        }
    }

    override fun onStart() {
        super.onStart()

        mDatabase = FirebaseDatabase.getInstance()

        val types = mutableListOf<Type>()

        val layoutManager = LinearLayoutManager(context)
        val typeAdapter = TypeAdapter(types = types, context = context!!)

        // Definir antes para poder atualizar depois //

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = typeAdapter

        val typeRef = mDatabase.getReference("types")
        val query = typeRef.orderByChild("userId")
        query.equalTo(userId).addValueEventListener(object: ValueEventListener { // Procura de valores através de uma query

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    val type = it.getValue(Type::class.java) // Converte de JSON pra classe tipo
                    types.add(type!!)
                    typeAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Debug", "Erro ao obter dados de tipo!")
                Log.e("Debug", "${databaseError.message}!")
                Log.e("Debug", "${databaseError.details}!")
            }
        })

    }

    fun addFragmentListener(listener: IFragmentListener) {
        this.fragmentListener = listener
    }
}