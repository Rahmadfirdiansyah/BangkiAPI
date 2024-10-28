import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bangkitapi.R
import com.dicoding.bangkitapi.database.FavoriteEvent
import com.dicoding.bangkitapi.databinding.ItemFavoriteEventBinding

class FavoriteEventAdapter(private val onClick: (FavoriteEvent) -> Unit) :
    ListAdapter<FavoriteEvent, FavoriteEventAdapter.FavoriteEventViewHolder>(FavoriteEventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        val binding = ItemFavoriteEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteEventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteEventViewHolder(private val binding: ItemFavoriteEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEvent: FavoriteEvent) {
            with(binding) {
                tvFavoriteEventName.text = favoriteEvent.name
                Glide.with(itemView.context)
                    .load(favoriteEvent.mediaCover)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivFavoriteEventCover)

                // Tindakan saat item diklik
                itemView.setOnClickListener {
                    onClick(favoriteEvent) // Panggil fungsi onClick saat item diklik
                }
            }
        }
    }

    class FavoriteEventDiffCallback : DiffUtil.ItemCallback<FavoriteEvent>() {
        override fun areItemsTheSame(oldItem: FavoriteEvent, newItem: FavoriteEvent): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteEvent, newItem: FavoriteEvent): Boolean {
            return oldItem == newItem
        }
    }
}

