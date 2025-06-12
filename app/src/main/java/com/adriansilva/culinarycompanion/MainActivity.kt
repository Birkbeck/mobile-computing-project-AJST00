// Defining package that class belongs to
package com.adriansilva.culinarycompanion

// Importing Intent switching between activities
import android.content.Intent
// Bundle passes data and restore state during lifecycle changes
import android.os.Bundle
// View is base class for all UI components
import android.view.View
// UI component for buttons
import android.widget.Button
//allows drawing content behind system bars like the status bar
import androidx.activity.enableEdgeToEdge
// Base class for modern Android activities with backwards compatibility support
import androidx.appcompat.app.AppCompatActivity
// Provides backward compatible access to newer view features
import androidx.core.view.ViewCompat
// it allows managing system window insets like status and navigation bars
import androidx.core.view.WindowInsetsCompat

// Declaring MainActivity class serving as entry screen
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Drawing content behind system bars
        enableEdgeToEdge()

        // Inflate layout first
        setContentView(R.layout.activity_main)

        // Set system bar insets
        val root: View = findViewById(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom)
            insets
        }

        // category buttons and click listeners
        val breakfastButton = findViewById<Button>(R.id.btn_breakfast)
        breakfastButton.setOnClickListener {
            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("category", "Breakfast")
            startActivity(intent)
        }

        val brunchButton = findViewById<Button>(R.id.btn_brunch)
        brunchButton.setOnClickListener {
            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("category", "Brunch")
            startActivity(intent)
        }

        val lunchButton = findViewById<Button>(R.id.btn_lunch)
        lunchButton.setOnClickListener {
            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("category", "Lunch")
            startActivity(intent)
        }

        val dinnerButton = findViewById<Button>(R.id.btn_dinner)
        dinnerButton.setOnClickListener {
            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("category", "Dinner")
            startActivity(intent)
        }

        val dessertsButton = findViewById<Button>(R.id.btn_desserts)
        dessertsButton.setOnClickListener {
            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("category", "Desserts")
            startActivity(intent)
        }

        val otherButton = findViewById<Button>(R.id.btn_other)
        otherButton.setOnClickListener {
            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("category", "Other")
            startActivity(intent)
        }
    }
}
