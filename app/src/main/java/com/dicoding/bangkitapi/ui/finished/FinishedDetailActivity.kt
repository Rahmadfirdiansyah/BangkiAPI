package com.dicoding.bangkitapi.ui.finished

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.bangkitapi.R
import com.dicoding.bangkitapi.data.response.ListEventsItem
import com.dicoding.bangkitapi.database.FavoriteEvent
import com.dicoding.bangkitapi.ui.favorite.insert.FavoriteEventAddUpdateViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FinishedDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: FavoriteEventAddUpdateViewModel
    private var isFavorite: Boolean = false // Status favorit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_detail)

        // Ambil data event dari intent
        val eventItem: ListEventsItem? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EVENT_DATA", ListEventsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("EVENT_DATA")
        }

        // Binding layout
        val eventImage = findViewById<ImageView>(R.id.img_event_logo)
        val eventName = findViewById<TextView>(R.id.tv_event_name)
        val eventOwner = findViewById<TextView>(R.id.tv_owner_name)
        val eventTime = findViewById<TextView>(R.id.tv_event_time)
        val eventQuota = findViewById<TextView>(R.id.tv_event_quota)
        val eventDescription = findViewById<TextView>(R.id.tv_event_description)
        val btnRegist = findViewById<Button>(R.id.btn_regist)

        // Jika eventItem tidak null, tampilkan datanya
        eventItem?.let {
            eventName.text = it.name
            eventOwner.text = it.ownerName
            eventTime.text = it.beginTime
            val remainingQuota = it.quota?.minus(it.registrants ?: 0)
            eventQuota.text = "Sisa Kuota $remainingQuota"
            eventDescription.text = HtmlCompat.fromHtml(
                it.description ?: "No Description Available",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

            // button register
            btnRegist.setOnClickListener {
                val url = eventItem?.link // Mengakses properti link dari eventItem
                if (!url.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                } else {
                    // Jika link tidak ada, tampilkan pesan atau handling lain
                    btnRegist.text = "Link Not Available"
                }
            }

            // Tampilkan gambar menggunakan Glide
            Glide.with(this)
                .load(it.mediaCover)
                .placeholder(R.drawable.ic_launcher_background)
                .into(eventImage)

            // Inisialisasi ViewModel
            viewModel = ViewModelProvider(this).get(FavoriteEventAddUpdateViewModel::class.java)

            // FloatingActionButton Favorit
            val fabFavorite: FloatingActionButton = findViewById(R.id.fab_favorite)
            fabFavorite.setOnClickListener {
                eventItem?.let { event ->
                    if (isFavorite) {
                        // Hapus dari favorit
                        deleteFromFavorites(event)
                        fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                        isFavorite = false
                    } else {
                        // Tambahkan ke favorit
                        saveToFavorites(event)
                        fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                        isFavorite = true
                    }
                }
            }
        } ?: run {
            eventName.text = "Event Not Found"
        }
    }

    private fun saveToFavorites(eventItem: ListEventsItem) {
        val favoriteEvent = FavoriteEvent(
            id = eventItem.id?.toString() ?: "",
            name = eventItem.name ?: "Unknown Event",
            ownerName = eventItem.ownerName ?: "Unknown Owner",
            quota = eventItem.quota ?: 0,
            registrant = eventItem.registrants ?: 0,
            beginTime = eventItem.beginTime ?: "Unknown Time",
            description = eventItem.description ?: "No Description Available",
            mediaCover = eventItem.mediaCover,
            link = eventItem.link ?: ""
        )
        viewModel.insert(favoriteEvent)
        Toast.makeText(this, "Event telah ditambahkan ke favorit", Toast.LENGTH_SHORT).show()
    }

    private fun deleteFromFavorites(eventItem: ListEventsItem) {
        viewModel.delete(FavoriteEvent(
            id = eventItem.id?.toString() ?: "",
            name = eventItem.name ?: "Unknown Event",
            ownerName = eventItem.ownerName ?: "Unknown Owner",
            quota = eventItem.quota ?: 0,
            registrant = eventItem.registrants ?: 0,
            beginTime = eventItem.beginTime ?: "Unknown Time",
            description = eventItem.description ?: "No Description Available",
            mediaCover = eventItem.mediaCover,
            link = eventItem.link ?: ""
        ))
        Toast.makeText(this, "Event telah dihapus dari favorit", Toast.LENGTH_SHORT).show()
    }
}
