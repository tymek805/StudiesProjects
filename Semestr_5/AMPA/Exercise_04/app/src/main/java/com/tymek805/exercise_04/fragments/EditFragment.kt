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
import com.tymek805.exercise_04.database.MyItem
import com.tymek805.exercise_04.database.TransportType
import com.tymek805.exercise_04.databinding.FragmentEditBinding

class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private val viewModel: ListViewModel by activityViewModels { ListViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(layoutInflater)

        val cityEditText = binding.cityEditText
        val timeEditText = binding.timeEditText
        val radioGroup: RadioGroup = binding.radioGroup
        val ratingBar = binding.ratingBar
        val checkbox = binding.checkbox

        if (viewModel.selectedItem.value == null) {
            val newItem = MyItem()
            viewModel.addItem(newItem)
            viewModel.selectItem(newItem)
        }

        viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
            item?.let {
                cityEditText.setText(it.destination)
                timeEditText.setText(it.time.toString())
                ratingBar.rating = it.rating
                checkbox.isChecked = it.checked
                when (it.transportType) {
                    TransportType.AIRPLANE -> radioGroup.check(R.id.airplaneBtn)
                    TransportType.BUS -> radioGroup.check(R.id.busBtn)
                    TransportType.TRAIN -> radioGroup.check(R.id.trainBtn)
                    null -> TODO()
                }
            }
        }

        binding.saveButton.setOnClickListener {
            val selectedItem: MyItem? = viewModel.selectedItem.value
            selectedItem?.let {
                selectedItem.destination = cityEditText.text.toString()
                selectedItem.time = timeEditText.text.toString().toInt()
                selectedItem.subText = "Czas połączenia: ${selectedItem.time} min"
                selectedItem.transportType = when (radioGroup.checkedRadioButtonId) {
                    R.id.airplaneBtn -> TransportType.AIRPLANE
                    R.id.trainBtn -> TransportType.TRAIN
                    R.id.busBtn -> TransportType.BUS
                    else -> TransportType.BUS
                }
                selectedItem.rating = ratingBar.rating
                selectedItem.checked = checkbox.isChecked

                viewModel.updateItem(selectedItem)
                findNavController().navigate(R.id.listFragment)
            }
        }

        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}