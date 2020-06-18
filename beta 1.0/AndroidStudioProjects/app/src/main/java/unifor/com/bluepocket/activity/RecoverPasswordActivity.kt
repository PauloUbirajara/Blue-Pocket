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
import unifor.com.bluepocket.R

class RecoverPasswordActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var recoverEmailText: TextView
    private lateinit var recoverButton: Button

    private lateinit var progress: AlertDialog

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        recoverEmailText = findViewById(R.id.recoveractivity_email_textview)
        recoverButton = findViewById(R.id.recoverpassword_recover_button)

        progress = AlertDialog.Builder(this)
            .setMessage("Por favor, aguarde...")
            .setTitle("Carregando")
            .setCancelable(false)
            .create()

        recoverButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.recoverpassword_recover_button -> {

                hideKeyboard()

                val recoverEmail = recoverEmailText.text.toString()

                // Verificar se a lacuna está correta //
                if(recoverEmail.isEmpty()) {
                    recoverEmailText.error = "Digite seu e-mail"
                    return
                }

                if(!recoverEmail.contains('@')) {
                    recoverEmailText.error = "O e-mail inserido não possui um formato válido"
                    return
                }
                // Verificar se a lacuna está correta //

                enableScreen(false)

                setLoadingPopup(true)

                mAuth.sendPasswordResetEmail(recoverEmail).addOnCompleteListener {
                    enableScreen(true)
                    setLoadingPopup(false)

                    if(it.isSuccessful) {

                        val dialog = AlertDialog.Builder(this)
                            .setMessage("Uma mensagem de confirmação foi enviado para o seu e-mail!")
                            .setTitle("Recuperação de senha")
                            .setCancelable(false)
                            .setNeutralButton(
                                "Ok",
                                DialogInterface.OnClickListener { dialog, id ->
                                    dialog.dismiss()
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }).create()
                        dialog.show()

                    } else {

                        val dialog = AlertDialog.Builder(this)
                            .setMessage("${it.exception!!.message}!")
                            .setTitle("Erro de recuperação de senha")
                            .setCancelable(false)
                            .setNeutralButton(
                                "Ok",
                                DialogInterface.OnClickListener { dialog, id ->
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
    }

    private fun hideKeyboard() {
        val imm = applicationContext!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(null, 0)
    }

    private fun enableScreen(option: Boolean) {
        recoverEmailText.isEnabled = option
        recoverButton.isEnabled = option
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
