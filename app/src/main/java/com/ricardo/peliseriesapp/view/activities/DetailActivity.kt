package com.ricardo.peliseriesapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ricardo.peliseriesapp.R
import com.ricardo.peliseriesapp.databinding.DetailActivityBinding
import com.ricardo.peliseriesapp.view.fragments.DetailFragment
import com.ricardo.peliseriesapp.view.fragments.VideoFragment

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: DetailActivityBinding
     companion object {
        val EXTRA_ID = "EXTRA_ID"
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: Int = intent.getIntExtra(EXTRA_ID,0)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_fragment_detail, DetailFragment.newInstance(id))
                .replace(R.id.container_fragment_video,VideoFragment.newInstance(id))
                .commitNow()
        }
    }
}