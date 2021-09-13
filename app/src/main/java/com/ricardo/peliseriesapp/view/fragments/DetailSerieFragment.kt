package com.ricardo.peliseriesapp.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ricardo.peliseriesapp.R
import com.ricardo.peliseriesapp.databinding.DetailSerieFragmentBinding
import com.ricardo.peliseriesapp.viewmodel.DetailViewModel

class DetailSerieFragment : Fragment() {

    private var idSerie: Int = 0

    companion object {
        fun newInstance(id: Int): DetailSerieFragment {
            val fragment = DetailSerieFragment()
            fragment.idSerie = id
            return fragment
        }
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DetailSerieFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailSerieFragmentBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewModel.getDetalleSerie(idSerie).observe(viewLifecycleOwner,{ serie ->
            val url: String = "https://image.tmdb.org/t/p/original" + serie.poster_path
            Glide.with(requireContext()).load(url)
                .placeholder(R.drawable.ic_baseline_local_movies_24)
                .error(R.drawable.ic_image_not_found_24)
                .into(binding.ivDetallePortadaSerie)
            binding.tvDetalleDescripcionSerie.text = serie.overview
            binding.tvDetalleDescripcionSerie.movementMethod = ScrollingMovementMethod()
            binding.tvDetallePaginaSerie.text = serie.homepage
            binding.tvDetallePaginaSerie.movementMethod = LinkMovementMethod()
            binding.tvDetalleFechaSerie.text = serie.first_air_date

            var generos: String = ""
            serie.genres.forEach { genre ->  generos += genre.name.plus(", ")}
            binding.tvDetalleGenerosSerie.text = generos

            binding.tvDetallePopularidadSerie.text = serie.popularity.toString()
            binding.ratingDetalleVotosSerie.rating = (serie.vote_average / 2f)
            binding.tvDetalleEpisodiosSerie.text = serie.number_of_episodes.toString()
            binding.tvDetalleTemporadaSerie.text = serie.number_of_seasons.toString()
        })
    }
}