package com.tymek805.exercise_04.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tymek805.exercise_04.R
import com.tymek805.exercise_04.database.MyItem
import com.tymek805.exercise_04.database.MyListAdapter
import com.tymek805.exercise_04.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by activityViewModels { ListViewModel.Factory }

    private lateinit var adapter: MyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MyListAdapter(onItemAction)
        adapter.submitList(viewModel.getAllItems())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.editFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recView = binding.myRecyclerView
        recView.layoutManager = LinearLayoutManager(requireContext())
        recView.adapter = adapter
    }

    private val onItemAction : (item: MyItem, action:Int)->Unit = { item, action ->
        when (action) {
            1 -> {
                viewModel.selectItem(item)
                findNavController().navigate(R.id.detailsFragment)
            }
            2 -> {
                viewModel.deleteItem(item)
                adapter.submitList(viewModel.getAllItems())
            }
        }
    }
}