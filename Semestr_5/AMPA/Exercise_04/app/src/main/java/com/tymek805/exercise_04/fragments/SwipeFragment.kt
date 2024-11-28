package com.tymek805.exercise_04.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tymek805.exercise_04.R
import com.tymek805.exercise_04.databinding.FragmentSwipeBinding

class SwipeFragment : Fragment() {
    private lateinit var binding: FragmentSwipeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSwipeBinding.inflate(layoutInflater)
        return binding.root
    }
}