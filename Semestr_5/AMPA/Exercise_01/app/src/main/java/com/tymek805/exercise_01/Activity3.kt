package com.tymek805.exercise_01

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.tymek805.exercise_01.databinding.Activity3Binding
import java.util.Locale



class Activity3 : AppCompatActivity(), View.OnLongClickListener {
    private lateinit var binding : Activity3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = Activity3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        var valueSlider: Float = extras?.getFloat("sliderValue",0.5f) ?: 0.0f
        val sliderValueText: TextView = binding.sliderValueText;
        val str = "Slider value: $valueSlider"
        sliderValueText.text = str;

        val activity3: View = binding.activity3
        activity3.setOnLongClickListener(this)

        val et1: EditText = binding.et1

        val buttonDial: Button = binding.buttonDial
        buttonDial.setOnClickListener { _ ->
            runDial(et1.text.toString())
        }

        val buttonCalc: Button = binding.buttonCalc

        var val1 = binding.val1
        var val2 = binding.val2

        var aBundle = Bundle()
        buttonCalc.setOnClickListener {
            aBundle.putInt("val1",val1.text.toString().toInt())
            aBundle.putInt("val2",val2.text.toString().toInt())
            val intent = Intent(this, ActivityCalc::class.java)
            intent.putExtras(aBundle)
            startCalcForResult.launch(intent)
        }

        val buttonSearch: Button = binding.buttonSearch;
        val searchText: EditText = binding.mapSearch;
        buttonSearch.setOnClickListener { _ ->
            searchMap(searchText.text.toString())
        }
    }

    private val startCalcForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                val d = intent?.getIntExtra("result",999)
                if (d != null) {
                    val tvResult: TextView = binding.result
                    tvResult.text = d.toString()
                }
            }
        }

    private fun runDial(phoneNum: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNum")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun searchMap(location: String) {
        val uri = java.lang.String.format(Locale.ENGLISH, "geo:0,0?q=$location")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        val toast: Toast = Toast.makeText(
            this,
            "Activity 3 is started",
            Toast.LENGTH_LONG)
        toast.show()
    }

    override fun onBackPressed() {
        val toast: Toast = Toast.makeText(
            this,
            "I am back",
            Toast.LENGTH_LONG)
        toast.show()
        super.onBackPressed()
    }

    override fun onLongClick(view: View?): Boolean {
        onBackPressed()
        return true
    }

    fun finishActivity3(view: View) {
        onBackPressed()
    }
}