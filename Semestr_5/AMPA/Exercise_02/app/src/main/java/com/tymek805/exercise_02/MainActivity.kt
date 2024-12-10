package com.tymek805.exercise_02

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tymek805.exercise_02.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        backButton = binding.backButton
        backButton.setOnClickListener { _ ->
            finish()
        }

        val botNavView: BottomNavigationView = binding.bottomNavigationView
        botNavView.selectedItemId = R.id.bottom_nav_finish
        botNavView.setOnItemSelectedListener { item ->
            when (item.itemId)  {
                R.id.bottom_nav_left -> {
                    startActivity(Intent(this, LeftActivity::class.java))
                }
                R.id.bottom_nav_finish -> finish()
                R.id.bottom_nav_right -> startActivity(Intent(this, RightActivity::class.java))
            }
            true
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_1 -> {
                backButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.menu_item_option_1))
                true
            }
            R.id.option_2 -> {
                backButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.menu_item_option_2))
                true
            }
            R.id.option_3 -> {
                backButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.menu_item_option_3))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}