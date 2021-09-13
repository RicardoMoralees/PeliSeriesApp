package com.ricardo.peliseriesapp.view.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.ricardo.peliseriesapp.R
import com.ricardo.peliseriesapp.model.Video

class VideosAdapter(var videos: List<Video>, val context: Context):
    RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    class VideoViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitulo: AppCompatTextView = view.findViewById(R.id.tv_titulo_video)
        val videoPlayer: YouTubePlayerView = view.findViewById(R.id.youtube_player_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video: Video = videos.get(position)

        holder.tvTitulo.text = video.name
        holder.videoPlayer.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(video.id, 0f)
                super.onReady(youTubePlayer)
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                Toast.makeText(context, "videoId: " + video.id + " " + error.toString(), Toast.LENGTH_SHORT).show()
                super.onError(youTubePlayer, error)
            }
        })
    }

    override fun getItemCount(): Int = videos.size
}