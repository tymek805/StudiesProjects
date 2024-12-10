package com.tymek805.exercise_02

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tymek805.exercise_02.databinding.ActivityRightBinding
import org.w3c.dom.Text

class RightActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRightBinding
    private var actionMode: ActionMode? = null

    private val actionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.cmenu_bg_color, menu)
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            val colorColor: TextView = binding.colorColorTextView
            when (item?.itemId) {
                R.id.bg_color_1 -> {
                    colorColor.setTextColor(Color.WHITE)
                    colorColor.setBackgroundColor((ContextCompat.getColor(applicationContext, R.color.menu_item_option_1)))
                }
                R.id.bg_color_2 -> {
                    colorColor.setTextColor(Color.WHITE)
                    colorColor.setBackgroundColor((ContextCompat.getColor(applicationContext, R.color.menu_item_option_2)))
                }
                R.id.bg_color_3 -> {
                    colorColor.setTextColor(Color.BLACK)
                    colorColor.setBackgroundColor((ContextCompat.getColor(applicationContext, R.color.menu_item_option_3)))
                }
            }
            mode?.finish()
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val optionArray = arrayOf("Good", "Moderate", "Bad")
        val checkedItem = intArrayOf(-1)

        val backButton: Button = binding.backButton
        backButton.setOnClickListener { _ ->
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Go Back Dialog\nGive your opinion:")
                .setSingleChoiceItems(optionArray, checkedItem[0]) { dialog, which ->
                    checkedItem[0] = which
                }
                .setPositiveButton("Accept") { _, _ ->
                    println(checkedItem[0])
                    when (checkedItem[0]) {
                        0 -> {Toast.makeText(this,"Good opinion submitted", Toast.LENGTH_SHORT).show()}
                        1 -> {Toast.makeText(this,"Moderate opinion submitted", Toast.LENGTH_SHORT).show()}
                        2 -> {Toast.makeText(this,"Bad opinion submitted", Toast.LENGTH_SHORT).show()}
                    }
                    onBackPressedDispatcher.onBackPressed()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    Toast.makeText(this,"Return canceled", Toast.LENGTH_SHORT).show()
                    dialog.cancel()
                }
                .setIcon(R.drawable.ic_home)
            builder.create().show()
        }

        val colorColor: TextView = binding.colorColorTextView
        colorColor.setOnLongClickListener { _ ->
            if (actionMode == null) {
                actionMode = startSupportActionMode(actionModeCallback)
                true
            } else false
        }

        val datePicker: TextView = binding.datePicker
        datePicker.setOnClickListener { _ ->
            val initialDate = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                    val date = Calendar.getInstance()
                    date.set(Calendar.YEAR, year)
                    date.set(Calendar.MONTH, month)
                    date.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    datePicker.text = dayOfMonth.toString() + "-" + (month + 1) + "-" + year
                },
                initialDate.get(Calendar.YEAR), initialDate.get(Calendar.MONTH), initialDate.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        val fab: FloatingActionButton = binding.fab
        fab.setOnClickListener {_ ->
            colorColor.setTextColor(Color.BLACK)
            colorColor.setBackgroundColor(Color.parseColor("#D3D3D3"))
        }
    }
}