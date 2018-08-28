package ru.nowandroid.pandorika.screen.main

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ru.nowandroid.pandorika.R
import ru.nowandroid.pandorika.dtos.MusicDto
import ru.nowandroid.pandorika.utils.inflate

class MusicAdapter : RecyclerView.Adapter<MusicHolder>() {

  private var data = mutableListOf<MusicDto>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder =
      MusicHolder(parent.inflate(R.layout.item_music))

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: MusicHolder, position: Int) {
    holder.bind(data[position])
  }

  fun set(data: List<MusicDto>) {
    this.data.clear()
    this.data.addAll(data)
    notifyDataSetChanged()
  }
}