package com.dicoding.bangkitapi.ui.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.bangkitapi.R
import com.dicoding.bangkitapi.database.FavoriteEvent
import com.dicoding.bangkitapi.databinding.ActivityFavoriteDetailBinding

class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteDetailBinding
    private lateinit var favoriteEvent: FavoriteEvent
    private val favoriteViewModel: FavoriteViewModel by viewModels { ViewModelFactory.getInstance(application) }
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari Intent
        val eventId = intent.getStringExtra(EXTRA_EVENT_ID)

        // Observe data acara favorit
        eventId?.let {
            favoriteViewModel.getFavoriteEventById(it).observe(this) { event ->
                if (event != null) {
                    this.favoriteEvent = event
                    isFavorite = true // Set isFavorite ke true jika event ada di database
                    displayEventDetails(event)
                } else {
                    // Event tidak ditemukan, bisa handle sesuai kebutuhan
                }
            }
        }

        binding.fabFavorite.setOnClickListener {
            toggleFavorite()
        }

        binding.btnRegist.setOnClickListener {
            openRegistrationUrl()
        }
    }

    private fun displayEventDetails(event: FavoriteEvent) {
        // Set nama dan pemilik acara
        binding.tvEventName.text = event.name
        binding.tvOwnerName.text = event.ownerName
        binding.tvEventTime.text = event.beginTime

        // Menghitung kuota tersisa
        val remainingQuota = event.quota?.minus(event.registrant ?: 0) ?: event.quota ?: 0
        binding.tvEventQuota.text = "Sisa Kuota: $remainingQuota"

        // Set deskripsi acara
        binding.tvEventDescription.text = HtmlCompat.fromHtml(
            event.description ?: "No Description Available",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        // Muat gambar menggunakan Glide
        Glide.with(this)
            .load(event.mediaCover)
            .placeholder(R.drawable.ic_launcher_background) // Ganti dengan placeholder yang sesuai
            .into(binding.imgEventLogo)

        // Set icon berdasarkan status favorit
        binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
    }


    private fun toggleFavorite() {
        if (isFavorite) {
            // Hapus dari favorit
            favoriteViewModel.deleteFavoriteEvent(favoriteEvent)
            isFavorite = false
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24) // Ganti icon untuk tidak favorit
        } else {
            // Tambah ke favorit
            favoriteViewModel.insertFavoriteEvent(favoriteEvent)
            isFavorite = true
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24) // Ganti icon untuk favorit
        }
    }

    private fun openRegistrationUrl() {
        val url = favoriteEvent.link // Pastikan `link` ada di FavoriteEvent
        if (!url.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } else {
            // Tampilkan pesan jika link tidak ada
            Toast.makeText(this, "Link tidak tersedia", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }
}
