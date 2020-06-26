package unifor.com.bluepocket.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import unifor.com.bluepocket.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var emailText: TextView
    private lateinit var passwordText: TextView

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var forgotPasswordButton: TextView

    private lateinit var progress: AlertDialog

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailText = findViewById(R.id.loginactivity_email_textview)
        passwordText = findViewById(R.id.loginactivity_password_textview)

        loginButton = findViewById(R.id.loginactivity_login_button)
        registerButton = findViewById(R.id.loginactivity_register_button)
        forgotPasswordButton = findViewById(R.id.loginactivity_forgotpassword_textview)

        progress = AlertDialog.Builder(this)
            .setMessage("Por favor, aguarde...")
            .setTitle("Carregando")
            .setCancelable(false)
            .create()

        loginButton.setOnClickListener(this)
        registerButton.setOnClickListener(this)
        forgotPasswordButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.loginactivity_login_button -> {

                hideKeyboard()

                val email = emailText.text.toString()
                val password = passwordText.text.toString()

                // Verificar se lacunas estão preenchidas //
                if(email.isEmpty()) {
                    emailText.error = "Digite seu e-mail"
                    return
                }

                if(!email.contains('@')) {
                    emailText.error = "O e-mail inserido não possui um formato válido"
                    return
                }

                if(password.isEmpty()) {
                    passwordText.error = "Digite sua senha"
                    return
                }
                // Verificar se lacunas estão preenchidas //

                enableScreen(false)

                setLoadingPopup(true)

                // Verificar com o Firebase //
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    enableScreen(true)
                    setLoadingPopup(false)

                    if(it.isSuccessful) {
                        // Alternar tela se tiver inserido as informacoes corretas //
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        // Alternar tela se tiver inserido as informacoes corretas //

                        // Limpar lacunas de informações //
                        emailText.text = ""
                        passwordText.text = ""
                        // Limpar lacunas de informações //
                    } else {
                        val dialog = AlertDialog.Builder(this)
                            .setMessage("${it.exception!!.message}!")
                            .setTitle("Erro de login")
                            .setCancelable(false)
                            .setNeutralButton(
                                "Ok",
                                DialogInterface.OnClickListener { dialog, id ->
                                    dialog.dismiss()
                                }).create()
                        dialog.show()
                    }
                }
                // Verificar com o Firebase //
            }

            R.id.loginactivity_register_button -> {
                val it = Intent(this, RegisterActivity::class.java)
                startActivity(it)
            }

            R.id.loginactivity_forgotpassword_textview -> {
                val it = Intent(this, RecoverPasswordActivity::class.java)
                startActivity(it)
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
        emailText.isEnabled = option
        passwordText.isEnabled = option

        loginButton.isEnabled = option
        registerButton.isEnabled = option
        forgotPasswordButton.isEnabled = option
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
