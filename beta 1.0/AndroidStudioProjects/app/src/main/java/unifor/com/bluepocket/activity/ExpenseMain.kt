package unifor.com.bluepocket.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.unifor.cct.droidtodo.fragments.FragmentExpenseList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import unifor.com.bluepocket.R
import unifor.com.bluepocket.util.IFragmentListener

class ExpenseMain : AppCompatActivity(), IFragmentListener {
    // Adicionar o botao pra cada categoria lateral
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase
    var userId: String = ""
    var expenseId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()

        userId = mAuth.currentUser!!.uid

        val fragmentExpenseList = FragmentExpenseList(userId) // Mostrar todas as categorias pelo id do usuário logado no momento
        fragmentExpenseList.addFragmentListener(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.expenses_main_framelayout, fragmentExpenseList)
            .addToBackStack(null)
            .commit()
    }

    override fun onFragmentClick(view: View) {
        userId = mAuth.currentUser!!.uid

        when (view.id) {
//            R.id.fragment_expense_list_floatingbutton_add -> {
////                val fragmentExpenseRegister = FragmentExpenseRegister(userId) // Registro de uma nova categoria
//
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.expenses_main_framelayout, fragmentExpenseRegister)
//                    .addToBackStack(null)
//                    .commit()
//            }
        }
    }
}