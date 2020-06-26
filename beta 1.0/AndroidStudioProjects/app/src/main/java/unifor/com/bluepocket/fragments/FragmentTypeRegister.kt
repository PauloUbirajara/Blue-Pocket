package unifor.com.bluepocket.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import unifor.com.bluepocket.R
import unifor.com.bluepocket.entity.Type

class FragmentTypeRegister(private var userId: String) : Fragment() {
    private lateinit var registerName: EditText
    private lateinit var registerType: Spinner
    private lateinit var registerDate: EditText
    private lateinit var registerValue: EditText
    private lateinit var registerAdd: Button
    private lateinit var registerBack: Button

    private lateinit var mDatabase: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_type_register, container, false)

        registerName = view.findViewById(R.id.typeregister_typename_textview)
        registerType = view.findViewById(R.id.typeregister_typetype_spinner)
        registerDate = view.findViewById(R.id.typeregister_typedate_textview)
        registerValue = view.findViewById(R.id.typeregister_typevalue_textview)

        registerAdd = view.findViewById(R.id.typeregister_typecreate_button)
        registerBack = view.findViewById(R.id.typeregister_typeback_button)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        registerAdd.setOnClickListener {
            if (registerName.text.isEmpty()) {
                registerName.error = "Este campo não pode ser nulo"
            }

            // Verificar se usuario possui uma tabela de tipos, e se não houver, criar a tabela com os tipos predefinidos
            // Quando for pegar a tabela de tipos, inserir antes o 'Selecione o tipo da despesa', juntar o vetor vindo do Firebase e na última posição adicionar o 'Criar novo tipo' no vetor //

            if (registerDate.text.isEmpty()) {
                registerDate.error = "Este campo não pode ser nulo"
            }

            if (registerValue.text.isEmpty()) {
                registerValue.error = "Este campo não pode ser nulo"
            }

            val name = registerName.text.toString()

            val typeRef =
                mDatabase.getReference("types") // criar pasta categories no database

            val typeId = typeRef.push().key // criar chave aleatória

            val type = Type(name = name, userId = userId, typeId = typeId!!)

            typeRef.child(typeId).setValue(type).addOnCompleteListener {

                if (it.isSuccessful) {
                    val dialog = AlertDialog.Builder(context!!)
                        .setTitle("Criação de Tipo")
                        .setMessage("Tipo criada com sucesso!")
                        .setCancelable(false)
                        .setNeutralButton(
                            "Ok"
                        ) { dialog, id ->
                            dialog.dismiss()
                            activity!!.supportFragmentManager.popBackStack()
                        }.create()

                    dialog.show()
                } else {
                    val dialog = AlertDialog.Builder(context!!)
                        .setTitle("Erro ao criar tipo")
                        .setMessage("${it.exception!!.message}!")
                        .setCancelable(false)
                        .setNeutralButton(
                            "Ok"
                        ) { dialog, id ->
                            dialog.dismiss()
                            activity!!.supportFragmentManager.popBackStack()

                        }.create()

                    dialog.show()
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        mDatabase = FirebaseDatabase.getInstance()
    }
}
