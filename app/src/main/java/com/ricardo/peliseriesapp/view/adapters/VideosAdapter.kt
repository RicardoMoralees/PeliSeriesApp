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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.ricardo.peliseriesapp.R
import com.ricardo.peliseriesapp.model.Video

class VideosAdapter(var videos: List<Video>, val context: Context):
    RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    val api_key = "AIzaSyBOx_RxoT1d7XHh8gpweALjnqkn0o30Izk"

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
        val url: String = "https://image.tmdb.org/t/p/original" + ""

        holder.videoPlayer.initialize(object :YouTubePlayerListener{
            override fun onApiChange(youTubePlayer: YouTubePlayer) {
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                Log.e("Youtube Error", error.toString())
            }

            override fun onPlaybackQualityChange(
                youTubePlayer: YouTubePlayer,
                playbackQuality: PlayerConstants.PlaybackQuality
            ) {
            }

            override fun onPlaybackRateChange(
                youTubePlayer: YouTubePlayer,
                playbackRate: PlayerConstants.PlaybackRate
            ) {
            }

            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(video.id, 0f);
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
            }

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                Log.w("Youtube id", videoId)
            }

            override fun onVideoLoadedFraction(
                youTubePlayer: YouTubePlayer,
                loadedFraction: Float
            ) {
            }

        },true)
    }

    override fun getItemCount(): Int = videos.size
}