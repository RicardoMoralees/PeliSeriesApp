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
import com.ricardo.peliseriesapp.databinding.FragmentPeliculasBinding
import com.ricardo.peliseriesapp.model.Pelicula
import com.ricardo.peliseriesapp.utils.Constants
import com.ricardo.peliseriesapp.utils.Constants.Companion.EXTRA_ID
import com.ricardo.peliseriesapp.utils.Constants.Companion.EXTRA_TIPO
import com.ricardo.peliseriesapp.utils.Constants.Companion.EXTRA_TITULO
import com.ricardo.peliseriesapp.view.activities.DetailActivity
import com.ricardo.peliseriesapp.view.adapters.PeliculasAdapter
import com.ricardo.peliseriesapp.viewmodel.PeliculaViewModel

class PeliculasFragment : Fragment() {

    private lateinit var peliculaViewModel: PeliculaViewModel
    private var _binding: FragmentPeliculasBinding? = null
    private lateinit var peliculasAdapter: PeliculasAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        peliculaViewModel =
            ViewModelProvider.NewInstanceFactory().create(PeliculaViewModel::class.java)

        _binding = FragmentPeliculasBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinnerTitle = binding.textPelicula
        peliculasAdapter = PeliculasAdapter(listOf(), view.context, peliculasListener)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.peliculas_categorias,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTitle.adapter = adapter
        }
        spinnerTitle.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.progressPeliculas.visibility = View.VISIBLE
                binding.recyclerPeliculas.visibility = View.GONE
                binding.containerSinResultados.visibility = View.GONE
                if (p2 == Constants.SERIES_POPULARES) {
                    if (checkInternetConnection())
                        callPeliculasPopulares()
                    else
                        showInternetAlert()
                } else {
                    if (checkInternetConnection())
                        callPeliculasActuales()
                    else
                        showInternetAlert()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                if (checkInternetConnection())
                    callPeliculasActuales()
                else
                    showInternetAlert()
            }

        }
        val recyclerPeliculas: RecyclerView = binding.recyclerPeliculas
        recyclerPeliculas.layoutManager = LinearLayoutManager(context)
        recyclerPeliculas.adapter = peliculasAdapter
    }

    private fun callPeliculasActuales() {
        peliculaViewModel.getNowPLayingPeliculas().observe(viewLifecycleOwner, Observer {
            binding.progressPeliculas.visibility = View.GONE
            if (it.isEmpty())
                binding.containerSinResultados.visibility = View.VISIBLE
            else {
                binding.recyclerPeliculas.visibility = View.VISIBLE
                peliculasAdapter.peliculas = it
                peliculasAdapter.notifyDataSetChanged()
                binding.containerSinResultados.visibility = View.GONE
            }
        })
    }

    private fun callPeliculasPopulares() {
        peliculaViewModel.getPeliculasPopulares().observe(viewLifecycleOwner, Observer {
            binding.progressPeliculas.visibility = View.GONE
            if (it.isEmpty())
                binding.containerSinResultados.visibility = View.VISIBLE
            else {
                binding.recyclerPeliculas.visibility = View.VISIBLE
                peliculasAdapter.peliculas = it
                peliculasAdapter.notifyDataSetChanged()
                binding.containerSinResultados.visibility = View.GONE
            }
        })
    }

    val peliculasListener = object : PeliculasAdapter.PeliculasClickListener {
        override fun onPeliculaCLicked(pelicula: Pelicula) {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_ID, pelicula.id)
                putExtra(EXTRA_TITULO, pelicula.title)
                putExtra(EXTRA_TIPO, Constants.TIPO_PELICULA)
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