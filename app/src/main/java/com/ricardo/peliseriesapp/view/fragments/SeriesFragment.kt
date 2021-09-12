package com.ricardo.peliseriesapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ricardo.peliseriesapp.databinding.FragmentSeriesBinding
import com.ricardo.peliseriesapp.model.Serie
import com.ricardo.peliseriesapp.view.adapters.SeriesAdapter
import com.ricardo.peliseriesapp.viewmodel.SeriesViewModel

class SeriesFragment : Fragment() {

    private lateinit var seriesViewModel: SeriesViewModel
    private var _binding: FragmentSeriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        seriesViewModel =
                ViewModelProvider.NewInstanceFactory().create(SeriesViewModel::class.java)

        _binding = FragmentSeriesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val seriesAdapter: SeriesAdapter
        val recyclerSeries : RecyclerView = binding.recyclerSeries
        recyclerSeries.layoutManager = LinearLayoutManager(context)
        seriesAdapter = SeriesAdapter(listOf(), view.context, seriesListener)
        recyclerSeries.adapter = seriesAdapter
        seriesViewModel.getNowPLayingSeries().observe(viewLifecycleOwner, Observer {
            seriesAdapter.series = it
            seriesAdapter.notifyDataSetChanged()
        })
    }

    val seriesListener = object: SeriesAdapter.SeriesClickListener {
        override fun onSerieCLicked(serie: Serie) {
            Toast.makeText(context,"Serie " + serie.name + " clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}