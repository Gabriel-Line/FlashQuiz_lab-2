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

        // Bouton Annuler : ferme l'activité sans rien sauvegarder
        btnCancel.setOnClickListener {
            finish()
        }

        // Bouton Enregistrer : sauvegarde dans Room et renvoie à MainActivity
        btnSave.setOnClickListener {
            val questionString = inputQuestion.text.toString()
            val answerString = inputAnswer.text.toString()

            // Vérification que les champs ne sont pas vides
            if (questionString.isNotBlank() && answerString.isNotBlank()) {
                // Création de l'objet Flashcard pour Room
                val card = Flashcard(question = questionString, answer = answerString)

                // Insertion dans la base de données Room (exécutée en arrière-plan)
                Thread {
                    val database = AppDatabase.getDatabase(applicationContext)
                    database.flashcardDao().insertCard(card)
                }.start()
            }

            // Renvoi des données via Intent (si utilisé par MainActivity)
            val data = Intent()
            data.putExtra("question_key", questionString)
            data.putExtra("answer_key", answerString)
            setResult(RESULT_OK, data)
            finish()
        }
    }
}