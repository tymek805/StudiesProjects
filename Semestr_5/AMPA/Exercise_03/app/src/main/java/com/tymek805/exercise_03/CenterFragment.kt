package com.tymek805.exercise_03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tymek805.exercise_03.databinding.FragmentCenterBinding

class CenterFragment : Fragment() {
    private lateinit var binding: FragmentCenterBinding
    private val appViewModel: AppSettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCenterBinding.inflate(layoutInflater)
        binding.invitationTextView.text = appViewModel.invitation.value
        binding.authorTextView.text = appViewModel.author.value
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changeButton.setOnClickListener { _ ->
            parentFragmentManager.setFragmentResult(
                "msg_to_right",
                bundleOf("key1" to "Button clicked!")
            )

            appViewModel.setInvitation("Invitation changed")
            appViewModel.setAuthor("Author's name changed")
        }

        appViewModel.invitation.observe(viewLifecycleOwner) { newMessage ->
            binding.invitationTextView.text = newMessage
        }

        appViewModel.author.observe(viewLifecycleOwner) { newMessage ->
            binding.authorTextView.text = newMessage
        }

        val centerSwitch: Switch = binding.centerSwitch
        appViewModel.switch.observe(viewLifecycleOwner) { newState ->
            centerSwitch.isChecked = newState
            binding.imageView.visibility = if (newState) View.INVISIBLE else View.VISIBLE
        }

        centerSwitch.setOnCheckedChangeListener { _, isChecked ->
            appViewModel.setSwitch(isChecked)
            binding.imageView.visibility = if (isChecked) View.INVISIBLE else View.VISIBLE
        }
    }
}