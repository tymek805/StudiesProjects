package com.tymek805.exercise_01

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider
import com.tymek805.exercise_01.databinding.Activity2Binding

class Activity2 : AppCompatActivity() {
    private lateinit var binding : Activity2Binding

    lateinit var img1: ImageView

    val swListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        if (isChecked)
            img1.visibility = View.VISIBLE
        else
            img1.visibility = View.INVISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        img1 = binding.img1
        val sw: Switch = binding.switch1
        sw.isChecked = true
        sw.setOnCheckedChangeListener(swListener)

        val slider: Slider = binding.slider!!
        
        slider.addOnChangeListener { _, value, _ ->
            val intent = Intent(this, Activity3::class.java)
            intent.putExtra("sliderValue", value)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val toast: Toast = Toast.makeText(
            this,
            "I am back",
            Toast.LENGTH_LONG)
        toast.show()
        super.onBackPressed()
    }
}