package iut.mobile.tp1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val tvResultat: TextView = findViewById(R.id.tvResultat)
        val btnPrecedent: Button = findViewById(R.id.btnPrecedent)

        val recupTexte = intent.getStringExtra(MainActivity.EXTRA_TEXT)

        if (recupTexte != null) {
            tvResultat.text = recupTexte
        }

        btnPrecedent.setOnClickListener {
            finish()
        }

    }
}