package com.ricardo.peliseriesapp.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ricardo.peliseriesapp.databinding.VideoFragmentBinding
import com.ricardo.peliseriesapp.utils.Constants
import com.ricardo.peliseriesapp.view.adapters.VideosAdapter
import com.ricardo.peliseriesapp.viewmodel.VideoViewModel

class VideoFragment : Fragment() {

    private var idVideo: Int = 0
    private var tipoVideo: String = ""

    companion object {
        fun newInstance(id: Int, tipo: String): VideoFragment {
            val fragment = VideoFragment()
            fragment.idVideo = id
            fragment.tipoVideo = tipo
            return fragment
        }
    }

    private lateinit var viewModel: VideoViewModel
    private lateinit var binding: VideoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = VideoFragmentBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(VideoViewModel::class.java)

        val recyclerVideos = binding.recyclerVideos
        val manager: RecyclerView.LayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        recyclerVideos.layoutManager = manager
        val videosAdapter = VideosAdapter(listOf(), requireContext())
        recyclerVideos.adapter = videosAdapter
        if (tipoVideo.equals(Constants.TIPO_PELICULA)) {
            viewModel.getPeliculaVideos(idVideo).observe(viewLifecycleOwner, {
                if (it.isEmpty())
                    binding.containerSinResultados.visibility = View.VISIBLE
                else {
                    binding.recyclerVideos.visibility = View.VISIBLE
                    videosAdapter.videos = it
                    binding.containerSinResultados.visibility = View.GONE
                }
            })
        } else {
            viewModel.getSerieVideos(idVideo).observe(viewLifecycleOwner, {
                if (it.isEmpty())
                    binding.containerSinResultados.visibility = View.VISIBLE
                else {
                    binding.recyclerVideos.visibility = View.VISIBLE
                    videosAdapter.videos = it
                    binding.containerSinResultados.visibility = View.GONE
                }
            })
        }

    }
}