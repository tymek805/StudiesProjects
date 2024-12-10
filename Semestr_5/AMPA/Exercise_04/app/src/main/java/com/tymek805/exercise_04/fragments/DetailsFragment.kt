package com.tymek805.exercise_04.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tymek805.exercise_04.R
import com.tymek805.exercise_04.database.TransportType
import com.tymek805.exercise_04.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: ListViewModel by activityViewModels { ListViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
            item?.let {
                binding.cityEditText.text = it.destination
                binding.timeTextEdit.text = it.time.toString()
                binding.ratingBar.rating = it.rating
                binding.transportType.text = it.transportType.toString()
                binding.checkbox.isChecked = it.checked

                binding.lrowTv1.text = item.destination
                binding.lrowTv2.text = item.subText
                binding.checkbox.isChecked = item.checked
                binding.lrowCheckBox.isChecked = item.checked
                val img = binding.lrowImage
                when (item.transportType) {
                    TransportType.AIRPLANE -> img.setImageResource(R.drawable.ic_airplane)
                    TransportType.TRAIN -> img.setImageResource(R.drawable.ic_train)
                    TransportType.BUS -> img.setImageResource(R.drawable.ic_bus)
                    null -> img.setImageResource(R.drawable.ic_null)
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.modifyButton.setOnClickListener {
            findNavController().navigate(R.id.editFragment)
        }

        return binding.root
    }
}