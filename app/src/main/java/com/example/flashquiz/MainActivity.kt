package com.example.flashquiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cardQuestion = findViewById<TextView>(R.id.card_question)
        val cardAnswer = findViewById<TextView>(R.id.card_answer)
        val addCardButton = findViewById<ImageView>(R.id.btn_add_card)

        // Définir le lanceur pour récupérer le résultat de AddCardActivity
        val addCardLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val questionString = data.getStringExtra("question_key")
                    val answerString = data.getStringExtra("answer_key")

                    // Mettre à jour le texte de la question et de la réponse
                    cardQuestion.text = questionString
                    cardAnswer.text = answerString
                }
            }
        }

        // Cliquer sur la question révèle la réponse
        cardQuestion.setOnClickListener {
            cardQuestion.visibility = View.INVISIBLE
            cardAnswer.visibility = View.VISIBLE
        }

        // Cliquer sur la réponse réaffiche la question
        cardAnswer.setOnClickListener {
            cardAnswer.visibility = View.INVISIBLE
            cardQuestion.visibility = View.VISIBLE
        }

        // Cliquer sur le bouton "+" lance AddCardActivity en attendant un résultat
        addCardButton.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            addCardLauncher.launch(intent)
        }
    }
}