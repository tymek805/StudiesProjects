package com.tymek805.exercise_04.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tymek805.exercise_04.database.MyAdapter
import com.tymek805.exercise_04.database.MyRepository
import com.tymek805.exercise_04.databinding.FragmentList4Binding

class List4Fragment : Fragment() {
    private lateinit var binding: FragmentList4Binding
    private lateinit var repository: MyRepository
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = MyRepository.getInstance(requireContext())
        adapter = MyAdapter(repository.getAllItems())
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
}