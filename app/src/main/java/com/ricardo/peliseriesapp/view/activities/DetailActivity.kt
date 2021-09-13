package com.ricardo.peliseriesapp.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ricardo.peliseriesapp.R
import com.ricardo.peliseriesapp.databinding.DetailActivityBinding
import com.ricardo.peliseriesapp.utils.Constants.Companion.EXTRA_ID
import com.ricardo.peliseriesapp.utils.Constants.Companion.EXTRA_TIPO
import com.ricardo.peliseriesapp.utils.Constants.Companion.EXTRA_TITULO
import com.ricardo.peliseriesapp.utils.Constants.Companion.TIPO_PELICULA
import com.ricardo.peliseriesapp.view.fragments.DetailPeliculaFragment
import com.ricardo.peliseriesapp.view.fragments.DetailSerieFragment
import com.ricardo.peliseriesapp.view.fragments.VideoFragment

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: DetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: Int = intent.getIntExtra(EXTRA_ID,0)
        val titulo: String = intent.getStringExtra(EXTRA_TITULO)!!
        val tipo: String = intent.getStringExtra(EXTRA_TIPO)!!
        title = titulo

        val detailFragment: Fragment = if (tipo.equals(TIPO_PELICULA))
            DetailPeliculaFragment.newInstance(id)
        else
            DetailSerieFragment.newInstance(id)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_fragment_detail, detailFragment)
                .replace(R.id.container_fragment_video,VideoFragment.newInstance(id, tipo))
                .commitNow()
        }
    }
}