package unifor.com.bluepocket.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import unifor.com.bluepocket.R

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var recipeButton: Button
    private lateinit var expenseButton: Button
    private lateinit var typeButton: Button
    private lateinit var chartButton: Button
    private lateinit var mensalDataButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recipeButton = findViewById(R.id.menu_recipe_button)
        expenseButton = findViewById(R.id.menu_expense_button)
        typeButton = findViewById(R.id.menu_type_button)
        chartButton = findViewById(R.id.menu_chart_button)
        mensalDataButton = findViewById(R.id.menu_mensal_data_button)

        recipeButton.setOnClickListener(this)
        expenseButton.setOnClickListener(this)
        typeButton.setOnClickListener(this)
        chartButton.setOnClickListener(this)
        mensalDataButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.menu_recipe_button -> {
                val it = Intent(this, RecipeMain::class.java)
                startActivity(it)
            }

            R.id.menu_expense_button -> {
                val it = Intent(this, ExpenseMain::class.java)
                startActivity(it)
            }

            R.id.menu_type_button -> {
                val it = Intent(this, TypeMain::class.java)
                startActivity(it)
            }

            R.id.menu_chart_button -> {
                val it = Intent(this, ChartMain::class.java)
                startActivity(it)
            }

            R.id.menu_mensal_data_button -> {
                val it = Intent(this, MensalDataMain::class.java)
                startActivity(it)
            }
        }
    }
}
