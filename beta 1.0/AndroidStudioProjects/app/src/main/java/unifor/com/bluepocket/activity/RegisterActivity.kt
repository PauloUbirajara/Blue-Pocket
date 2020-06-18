package unifor.com.bluepocket.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import unifor.com.bluepocket.R
import unifor.com.bluepocket.entity.User

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var usernameText: TextView
    private lateinit var emailText: TextView
    private lateinit var passwordText: TextView
    private lateinit var confirmPasswordText: TextView

    private lateinit var registerButton: Button

    private lateinit var progress: AlertDialog

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameText = findViewById(R.id.registeractivity_username_textview)
        emailText = findViewById(R.id.registeractivity_email_textview)
        passwordText = findViewById(R.id.registeractivity_password_textview)
        confirmPasswordText = findViewById(R.id.registeractivity_confirmpassword_textview)

        registerButton = findViewById(R.id.registeractivity_register_button)

        progress = AlertDialog.Builder(this)
            .setMessage("Por favor, aguarde...")
            .setTitle("Carregando")
            .setCancelable(false)
            .create()

        registerButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.registeractivity_register_button -> {

                hideKeyboard()

                val username = usernameText.text.toString()
                val email = emailText.text.toString()
                val password = passwordText.text.toString()
                val confirmPassword = confirmPasswordText.text.toString()

                // Verificar se todas as lacunas foram preenchidas corretamente //
                if(username.isEmpty()) {
                    usernameText.error = "Digite um usuário"
                    return
                }

                if(email.isEmpty()) {
                    emailText.error = "Digite um e-mail"
                    return
                }

                if(!email.contains('@')) {
                    emailText.error = "O e-mail inserido não possui um formato válido"
                    return
                }

                if(password.isEmpty()) {
                    passwordText.error = "Digite uma senha"
                    return
                }

                if(password.length < 6) {
                    passwordText.error = "A senha precisa de um mínimo de 6 letras"
                    return
                }

                if(confirmPassword.isEmpty()) {
                    confirmPasswordText.error = "Confirme sua senha"
                    return
                }

                if(password != confirmPassword) {
                    passwordText.error = "As senhas não conferem"
                    return
                }
                // Verificar se todas as lacunas foram preenchidas corretamente //

                setLoadingPopup(true)

                enableScreen(false)

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    enableScreen(true)
                    setLoadingPopup(false)

                    if(it.isSuccessful) {
                        val userRef = mDatabase.getReference("users")
                        val user = User(
                            name = username,
                            email = email,
                            userId = mAuth.currentUser!!.uid
                        )
                        userRef.child(mAuth.currentUser!!.uid).setValue(user)

                        val dialog = AlertDialog.Builder(this@RegisterActivity)
                            .setMessage("Usuário cadastrado com sucesso!")
                            .setTitle("Cadastro de usuário")
                            .setCancelable(false)
                            .setNeutralButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                                dialog.dismiss()
                                finish()
                            }).create()

                        dialog.show()
                    } else {
                        val dialog = AlertDialog.Builder(this@RegisterActivity)
                            .setMessage("${it.exception!!.message}")
                            .setTitle("Erro de cadastro")
                            .setCancelable(false)
                            .setNeutralButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                                dialog.dismiss()
                            }).create()

                        dialog.show()
                    }
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
    }

    private fun hideKeyboard() {
        val imm = applicationContext!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(null, 0)
    }

    private fun enableScreen(option: Boolean) {
        usernameText.isEnabled = option
        emailText.isEnabled = option
        passwordText.isEnabled = option
        confirmPasswordText.isEnabled = option

        registerButton.isEnabled = option
    }

    private fun setLoadingPopup(option: Boolean) {
        when(option) {
            true -> {
                progress.show()
            }

            false -> {
                progress.dismiss()
            }
        }
    }
}
