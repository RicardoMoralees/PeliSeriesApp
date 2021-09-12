package com.ricardo.peliseriesapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ricardo.peliseriesapp.databinding.FragmentPeliculasBinding
import com.ricardo.peliseriesapp.model.Pelicula
import com.ricardo.peliseriesapp.view.adapters.PeliculasAdapter
import com.ricardo.peliseriesapp.view.activities.DetailActivity
import com.ricardo.peliseriesapp.viewmodel.PeliculaViewModel

class PeliculasFragment : Fragment() {

    private lateinit var peliculaViewModel: PeliculaViewModel
    private var _binding: FragmentPeliculasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        peliculaViewModel = ViewModelProvider.NewInstanceFactory().create(PeliculaViewModel::class.java)

        _binding = FragmentPeliculasBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerPeliculas :RecyclerView = binding.recyclerPeliculas
        recyclerPeliculas.layoutManager = LinearLayoutManager(context)
        val peliculasAdapter = PeliculasAdapter(listOf(), view.context, peliculasListener)
        recyclerPeliculas.adapter = peliculasAdapter
        peliculaViewModel.getNowPLayingPeliculas().observe(viewLifecycleOwner, Observer {
            peliculasAdapter.peliculas = it
            peliculasAdapter.notifyDataSetChanged()
        })
    }

    val peliculasListener = object: PeliculasAdapter.PeliculasClickListener {
        override fun onPeliculaCLicked(pelicula: Pelicula) {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_ID, pelicula.id)
            }
            startActivity(intent)
            //Toast.makeText(context,"Pelicula " + pelicula.title + " clicked!",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}