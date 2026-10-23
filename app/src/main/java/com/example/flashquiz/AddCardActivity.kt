package com.example.flashquiz

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AddCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val inputQuestion = findViewById<EditText>(R.id.input_question)
        val inputAnswer = findViewById<EditText>(R.id.input_answer)
        val btnCancel = findViewById<ImageView>(R.id.btn_cancel)
        val btnSave = findViewById<ImageView>(R.id.btn_save)

        // Bouton Annuler : ferme l'activité sans rien sauvegarder[span_3](start_span)[span_3](end_span)
        btnCancel.setOnClickListener {
            finish()
        }

        // Bouton Enregistrer : récupère les textes et les renvoie à MainActivity[span_4](start_span)[span_4](end_span)
        btnSave.setOnClickListener {
            val questionString = inputQuestion.text.toString()
            val answerString = inputAnswer.text.toString()

            val data = Intent()
            data.putExtra("question_key", questionString)
            data.putExtra("answer_key", answerString)
            setResult(RESULT_OK, data)
            finish()
        }
    }
}