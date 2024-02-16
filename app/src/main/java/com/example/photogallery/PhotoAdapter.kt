package com.example.photogallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.db.PhotoDetail
import com.squareup.picasso.Picasso

class PhotoAdapter : ListAdapter<PhotoDetail, PhotoAdapter.PhotoViewHolder>(PhotoDetailDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_photo_detail, parent, false)
        return PhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoDetail = getItem(position)
        holder.bind(photoDetail)
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewPhoto)
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)

        fun bind(photoDetail: PhotoDetail) {
            titleTextView.text = photoDetail.title
            Picasso.get().load(photoDetail.url).into(imageView)
        }
    }
}

class PhotoDetailDiffCallback : DiffUtil.ItemCallback<PhotoDetail>() {
    override fun areItemsTheSame(oldItem: PhotoDetail, newItem: PhotoDetail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoDetail, newItem: PhotoDetail): Boolean {
        return oldItem == newItem
    }
}