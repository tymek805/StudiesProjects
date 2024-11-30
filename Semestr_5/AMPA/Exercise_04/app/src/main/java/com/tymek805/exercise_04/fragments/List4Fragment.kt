package com.tymek805.exercise_04.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tymek805.exercise_04.database.MyItem
import com.tymek805.exercise_04.database.MyListAdapter
import com.tymek805.exercise_04.database.MyRepository
import com.tymek805.exercise_04.databinding.FragmentList4Binding

class List4Fragment : Fragment() {
    private lateinit var binding: FragmentList4Binding
    val viewModel: ListViewModel by activityViewModels { ListViewModel.Factory }

    private lateinit var repository: MyRepository
    private lateinit var adapter: MyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = MyRepository.getInstance(requireContext())
        adapter = MyListAdapter(onItemAction)
        adapter.submitList(viewModel.getAllItems())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentList4Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recView = binding.myRecyclerView
        recView.layoutManager = LinearLayoutManager(requireContext())
        recView.adapter = adapter
    }

    val onItemAction : (item: MyItem, action:Int)->Unit = { item, action ->
        when (action) {
            1 -> {
                Toast.makeText(requireContext(),"You clicked: " + item.textMain,
                    Toast.LENGTH_SHORT).show()
            }
            2 -> {
                viewModel.deleteItem(item)
                adapter.submitList(viewModel.getAllItems())
            }
        }
    }
}