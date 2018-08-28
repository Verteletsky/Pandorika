package ru.nowandroid.pandorika.screen.main

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_music.view.*
import ru.nowandroid.pandorika.dtos.MusicDto

class MusicHolder(view: View) : RecyclerView.ViewHolder(view) {
  fun bind(music: MusicDto) {
    itemView.artistName.text = music.artistName
    itemView.collectionName.text = music.collectionName
    Glide.with(itemView.context)
        .load(music.artworkUrl60)
        .into(itemView.artworkUrl)
  }
}
