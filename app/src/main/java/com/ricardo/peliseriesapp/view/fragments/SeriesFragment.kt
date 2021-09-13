package com.ricardo.peliseriesapp.view.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ricardo.peliseriesapp.R
import com.ricardo.peliseriesapp.databinding.FragmentSeriesBinding
import com.ricardo.peliseriesapp.model.Serie
import com.ricardo.peliseriesapp.utils.Constants.Companion.EXTRA_ID
import com.ricardo.peliseriesapp.utils.Constants.Companion.EXTRA_TIPO
import com.ricardo.peliseriesapp.utils.Constants.Companion.EXTRA_TITULO
import com.ricardo.peliseriesapp.utils.Constants.Companion.SERIES_POPULARES
import com.ricardo.peliseriesapp.utils.Constants.Companion.TIPO_SERIE
import com.ricardo.peliseriesapp.view.activities.DetailActivity
import com.ricardo.peliseriesapp.view.adapters.SeriesAdapter
import com.ricardo.peliseriesapp.viewmodel.SeriesViewModel

class SeriesFragment : Fragment() {

    private lateinit var seriesViewModel: SeriesViewModel
    private var _binding: FragmentSeriesBinding? = null
    lateinit var seriesAdapter: SeriesAdapter

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
        val spinnerTitle = binding.textSeries
        seriesAdapter = SeriesAdapter(listOf(), view.context, seriesListener)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.series_categorias,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTitle.adapter = adapter
        }
        spinnerTitle.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.progressSeries.visibility = View.VISIBLE
                binding.recyclerSeries.visibility = View.GONE
                binding.containerSinResultados.visibility = View.GONE
                if (p2 == SERIES_POPULARES) {
                    if (checkInternetConnection())
                        callSeriesPopulares()
                    else
                        showInternetAlert()
                } else {
                    if (checkInternetConnection())
                        callSeriesActuales()
                    else
                        showInternetAlert()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                if (checkInternetConnection())
                    callSeriesActuales()
                else
                    showInternetAlert()
            }

        }
        val recyclerSeries: RecyclerView = binding.recyclerSeries
        recyclerSeries.layoutManager = LinearLayoutManager(context)
        recyclerSeries.adapter = seriesAdapter
    }

    private fun callSeriesActuales() {
        seriesViewModel.getNowPLayingSeries().observe(viewLifecycleOwner, Observer {
            binding.progressSeries.visibility = View.GONE
            if (it.isEmpty())
                binding.containerSinResultados.visibility = View.VISIBLE
            else {
                binding.recyclerSeries.visibility = View.VISIBLE
                seriesAdapter.series = it
                seriesAdapter.notifyDataSetChanged()
                binding.containerSinResultados.visibility = View.GONE
            }
        })
    }

    private fun callSeriesPopulares() {
        seriesViewModel.getSeriesPopulares().observe(viewLifecycleOwner, Observer {
            binding.progressSeries.visibility = View.GONE
            if (it.isEmpty())
                binding.containerSinResultados.visibility = View.VISIBLE
            else {
                binding.recyclerSeries.visibility = View.VISIBLE
                seriesAdapter.series = it
                seriesAdapter.notifyDataSetChanged()
                binding.containerSinResultados.visibility = View.GONE
            }
        })
    }

    val seriesListener = object : SeriesAdapter.SeriesClickListener {
        override fun onSerieCLicked(serie: Serie) {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_ID, serie.id)
                putExtra(EXTRA_TITULO, serie.name)
                putExtra(EXTRA_TIPO, TIPO_SERIE)
            }
            startActivity(intent)
        }
    }

    fun checkInternetConnection(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun showInternetAlert() {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.aceptar,
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    })
                    .setCancelable(false)
                    .setTitle(getString(R.string.sin_internet))
                    .setMessage(getString(R.string.internet_message))
            }
            builder.create()
        }
        alertDialog!!.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}