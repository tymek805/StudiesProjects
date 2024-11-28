package com.tymek805.exercise_03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.tymek805.exercise_03.databinding.FragmentRightBinding

class RightFragment : Fragment() {
    private lateinit var binding: FragmentRightBinding
    private val appViewModel: AppSettingsViewModel by activityViewModels()
    private val rightViewModel: RightViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRightBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener("msg_to_right", viewLifecycleOwner) { _, bundle ->
            val result = bundle.getString("key1")
            appViewModel.setMsg(result.toString())
        }

        appViewModel.msg.observe(viewLifecycleOwner) { newMessage ->
            binding.messageFromCenter.text = newMessage
        }

        rightViewModel.rating.observe((viewLifecycleOwner)) { newValue ->
            binding.ratingBar.rating = newValue
        }

        val ratingBar: RatingBar = binding.ratingBar
        ratingBar.setOnClickListener {_ ->
            rightViewModel.setRating(ratingBar.rating)
        }
    }
}