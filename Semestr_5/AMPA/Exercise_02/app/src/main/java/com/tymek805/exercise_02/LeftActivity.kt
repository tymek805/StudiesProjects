package com.tymek805.exercise_02

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import com.tymek805.exercise_02.databinding.ActivityLeftBinding


class LeftActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeftBinding
    private var isBold: Boolean = false
    private var isItalic: Boolean = false

    private var textSize: Int = 0
    private var textType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        registerForContextMenu(binding.textViewSize)
        registerForContextMenu(binding.textViewType)

        val backButton: Button = binding.backButton
        backButton.setOnClickListener { _ ->
            onBackPressed()
        }

        getCustomPreferences(this)
    }

    private fun setCustomPreferences(context: Context) {
        val data: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putInt("textSize", textSize)
        editor.putInt("textType", textType)
        editor.apply()
    }

    private fun getCustomPreferences(context: Context) {
        val data: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        textSize = data.getInt("textSize", 0)
        textType = data.getInt("textType", 0)

        when (textSize) {
            20 -> binding.textViewSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            22 -> binding.textViewSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22F)
            24 -> binding.textViewSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
        }
        when (textType) {
            1 -> {
                isBold = !isBold
                applyTypeface(binding.textViewType)
            }
            2 -> {
                isItalic = !isItalic
                applyTypeface(binding.textViewType)
            }
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getCustomPreferences(this)
        menuInflater.inflate(R.menu.modification_menu, menu)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        getCustomPreferences(this)
        when (v) {
            binding.textViewSize -> {
                menu!!.setHeaderTitle("Font Size Menu")
                menuInflater.inflate(R.menu.cmenu_fontsize, menu)
            }
            binding.textViewType -> {
                menu!!.setHeaderTitle("Font Type Menu")
                menuInflater.inflate(R.menu.cmenu_fonttype, menu)
                menu.findItem(R.id.type_option_1).setChecked(isBold)
                menu.findItem(R.id.type_option_2).setChecked(isItalic)
            }
            else -> menuInflater.inflate(R.menu.menu_main, menu)
        }
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.size_option_1 -> {
                textSize = 20
                binding.textViewSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
            R.id.size_option_2 -> {
                textSize = 22
                binding.textViewSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22F)
            }
            R.id.size_option_3 -> {
                textSize = 24
                binding.textViewSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
            }
            R.id.type_option_1 -> {
                textType = 1
                isBold = !isBold
                applyTypeface(binding.textViewType)
            }
            R.id.type_option_2 -> {
                textType = 2
                isItalic = !isItalic
                applyTypeface(binding.textViewType)
            }
        }
        setCustomPreferences(this)
        return super.onContextItemSelected(item)
    }

    private fun applyTypeface(textView: TextView) {
        when {
            isBold && isItalic -> textView.setTypeface(null, Typeface.BOLD_ITALIC)
            isBold -> textView.setTypeface(null, Typeface.BOLD)
            isItalic -> textView.setTypeface(null, Typeface.ITALIC)
            else -> textView.setTypeface(null, Typeface.NORMAL)
        }
    }

    override fun onResume() {
        getCustomPreferences(this)
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setCustomPreferences(this)
        return when (item.itemId) {
            R.id.set_20_for_all -> {
                binding.textViewSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                binding.textViewType.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                true
            }
            R.id.set_22_for_all -> {
                binding.textViewSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22F)
                binding.textViewType.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22F)
                true
            }
            R.id.set_24_for_all -> {
                binding.textViewSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
                binding.textViewType.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
                true
            }
            R.id.set_bold_for_all -> {
                isBold = !isBold
                applyTypeface(binding.textViewType)
                applyTypeface(binding.textViewSize)
                true
            }
            R.id.set_italic_for_all ->  {
                isItalic = !isItalic
                applyTypeface(binding.textViewType)
                applyTypeface(binding.textViewSize)
                true
            }
            R.id.reset_option -> {
                binding.textViewSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                binding.textViewType.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                isItalic = false
                isBold = false
                applyTypeface(binding.textViewType)
                applyTypeface(binding.textViewSize)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}