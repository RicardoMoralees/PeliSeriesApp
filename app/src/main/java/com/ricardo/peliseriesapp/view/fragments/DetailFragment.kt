package com.ricardo.peliseriesapp.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ricardo.peliseriesapp.R
import com.ricardo.peliseriesapp.databinding.DetailFragmentBinding
import com.ricardo.peliseriesapp.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    private var idPelicula: Int = 0

    companion object {
        fun newInstance(id: Int): DetailFragment {
            val fragment = DetailFragment()
            fragment.idPelicula = id
            return fragment
        }
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewModel.getDetallePelicula(idPelicula).observe(viewLifecycleOwner,{ pelicula ->
            binding.tvDetalleTituloPelicula.text = pelicula.title
            val url: String = "https://image.tmdb.org/t/p/original" + pelicula.poster_path
            Glide.with(requireContext()).load(url)
                .placeholder(R.drawable.ic_baseline_local_movies_24)
                .error(R.drawable.ic_image_not_found_24)
                .into(binding.ivDetallePortadaPelicula)
            binding.tvDetalleDescripcionPelicula.text = pelicula.overview
            binding.tvDetalleDescripcionPelicula.movementMethod = ScrollingMovementMethod()
            binding.tvDetallePaginaPelicula.text = pelicula.homepage
            binding.tvDetallePaginaPelicula.movementMethod = LinkMovementMethod()
            binding.tvDetalleFechaPelicula.text = pelicula.release_date

            var generos: String = ""
            pelicula.genres.forEach { genre ->  generos += genre.name.plus(", ")}
            binding.tvDetalleGenerosPelicula.text = generos

            binding.tvDetallePopularidadPelicula.text = pelicula.popularity.toString()
            binding.tvDetalleVotosPelicula.text = pelicula.vote_average.toString()
        })
    }
}