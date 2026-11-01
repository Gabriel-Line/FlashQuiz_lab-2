package com.example.flashquiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var cardQuestion: TextView
    private lateinit var cardAnswer: TextView
    private lateinit var db: AppDatabase
    private var flashcardList: List<Flashcard> = listOf()
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardQuestion = findViewById(R.id.card_question)
        cardAnswer = findViewById(R.id.card_answer)
        val addCardButton = findViewById<ImageView>(R.id.btn_add_card)
        val btnNext = findViewById<ImageView>(R.id.btn_next) // Icône flèche '>'

        // Initialisation de la base de données Room
        db = AppDatabase.getDatabase(applicationContext)

        // Charger les cartes depuis la base de données au démarrage
        loadFlashcardsFromDatabase()

        // Lanceur pour récupérer le résultat de AddCardActivity
        val addCardLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // Recharger les cartes et aller sur la dernière ajoutée
                loadFlashcardsFromDatabase()
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

        // Bouton "+" pour ajouter une carte
        addCardButton.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            addCardLauncher.launch(intent)
        }

        // Icône '>' pour passer à la carte/question suivante dans le deck[span_2](start_span)[span_2](end_span)
        btnNext.setOnClickListener {
            if (flashcardList.isNotEmpty()) {
                currentIndex = (currentIndex + 1) % flashcardList.size
                displayCard(currentIndex)
            }
        }
    }

    // Charger toutes les cartes de la base de données en arrière-plan
    private fun loadFlashcardsFromDatabase() {
        Thread {
            flashcardList = db.flashcardDao().getAllCards()

            runOnUiThread {
                if (flashcardList.isNotEmpty()) {
                    currentIndex = flashcardList.size - 1 // Affiche la plus récente par défaut
                    displayCard(currentIndex)
                } else {
                    cardQuestion.text = "Aucune carte disponible. Cliquez sur + pour en ajouter !"
                    cardAnswer.text = ""
                }
            }
        }.start()
    }

    // Afficher une carte spécifique selon son index
    private fun displayCard(index: Int) {
        val card = flashcardList[index]
        cardQuestion.text = card.question
        cardAnswer.text = card.answer

        // S'assurer que la question est visible et la réponse cachée au changement de carte
        cardQuestion.visibility = View.VISIBLE
        cardAnswer.visibility = View.INVISIBLE
    }
}