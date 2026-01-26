package iut.mobile.tp1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TEXT = "text_to_display"
    }

    private var texteValide: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val premierBouton: Button = findViewById(R.id.bouton1)

        val monTextView: TextView = findViewById(R.id.monTextView)
        val monEditText: EditText = findViewById(R.id.monEditText)

        premierBouton.setOnClickListener {
            val texteSaisi = monEditText.text.toString()
            val layoutPrincipal : ConstraintLayout = findViewById(R.id.main)

            if (texteSaisi == "afficher nouveau textView") {
                val deuxiemeTextView = TextView(this)

                deuxiemeTextView.text = texteSaisi

                layoutPrincipal.addView(deuxiemeTextView)

            } else {
                monTextView.text = texteSaisi
            }
        }

        val boutonValider: Button = findViewById(R.id.bouton1)
        val boutonNext: Button = findViewById(R.id.btnNext)

        boutonValider.setOnClickListener {
            texteValide = monEditText.text.toString()
            monTextView.text = texteValide
        }

        boutonNext.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)

            intent.putExtra(EXTRA_TEXT, texteValide)

            startActivity(intent)
        }
    }
}   