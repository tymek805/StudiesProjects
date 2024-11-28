package com.tymek805.exercise_01

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tymek805.exercise_01.databinding.Activity2Binding
import com.tymek805.exercise_01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    val myListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.button4 -> {
                val myIntent = Intent(this, Activity3::class.java)
                startActivity(myIntent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button1: Button = binding.button1
        button1.setOnClickListener { _ ->
            val myIntent = Intent(this, Activity2::class.java)
            startActivity(myIntent)
        }

        val button2: Button = binding.button2
        button2.setOnClickListener(this)

        val button4: Button = binding.button4
        button4.setOnClickListener(myListener)
    }

    override fun onClick(view: View?) {
        val myIntent = Intent(this, Activity3::class.java)
        startActivity(myIntent)
    }

    fun run2(view: View) {
        val myIntent = Intent(this, Activity2::class.java)
        startActivity(myIntent)
    }
}