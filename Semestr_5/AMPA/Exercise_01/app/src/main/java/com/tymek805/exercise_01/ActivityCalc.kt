package com.tymek805.exercise_01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tymek805.exercise_01.databinding.Activity3Binding
import com.tymek805.exercise_01.databinding.ActivityCalcBinding

class ActivityCalc : AppCompatActivity() {
    private lateinit var binding : ActivityCalcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        var val1: Int = bundle?.getInt("val1",1) ?: 0
        var val2: Int = bundle?.getInt("val2",1) ?: 0

        val btnAdd: Button = binding.btAdd
        val btnSub: Button = binding.btSub
        val btnMul: Button = binding.btMul

        btnAdd.setOnClickListener { _ ->
            intent.putExtra("result",val1 + val2)
            setResult(RESULT_OK, intent)
            finish()
        }

        btnSub.setOnClickListener { _ ->
            intent.putExtra("result",val1 - val2)
            setResult(RESULT_OK, intent)
            finish()
        }

        btnMul.setOnClickListener { _ ->
            intent.putExtra("result",val1 * val2)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}