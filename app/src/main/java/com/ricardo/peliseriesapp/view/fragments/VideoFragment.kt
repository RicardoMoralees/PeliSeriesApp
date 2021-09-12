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
import com.ricardo.peliseriesapp.view.adapters.VideosAdapter
import com.ricardo.peliseriesapp.viewmodel.VideoViewModel

class VideoFragment : Fragment() {

    private var idVideo: Int = 0

    companion object {
        fun newInstance(id: Int): VideoFragment {
            val fragment = VideoFragment()
            fragment.idVideo = id
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
        viewModel.getVideos(idVideo).observe(viewLifecycleOwner, {
            videosAdapter.videos = it
            videosAdapter.notifyDataSetChanged()
        })
    }
}