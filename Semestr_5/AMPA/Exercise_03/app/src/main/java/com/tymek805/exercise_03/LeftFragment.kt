package com.tymek805.exercise_03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tymek805.exercise_03.databinding.FragmentLeftBinding


class LeftFragment : Fragment() {
    private lateinit var binding: FragmentLeftBinding
    private val appViewModel: AppSettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeftBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.invitationEditText.addTextChangedListener { _ ->
            appViewModel.setInvitation(binding.invitationEditText.text.toString())
        }

        binding.authorEditText.addTextChangedListener { _ ->
            appViewModel.setAuthor(binding.authorEditText.text.toString())
        }

        binding.leftSwitch.setOnCheckedChangeListener { _, isChecked ->
            appViewModel.setSwitch(isChecked)
        }

        appViewModel.switch.observe(viewLifecycleOwner) { newState ->
            binding.leftSwitch.isChecked = newState
        }
    }
}