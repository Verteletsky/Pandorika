package ru.nowandroid.pandorika.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nowandroid.pandorika.R

val Activity.progress: View
  get() = this.findViewById<View>(R.id.progress_view)

fun Activity.fadeIn() {
  progress.visibility = View.VISIBLE
}

fun Activity.fadeOut() {
  progress.visibility = View.GONE
}

fun ViewGroup.inflate(layout: Int): View =
    LayoutInflater.from(context).inflate(layout, this, false)

fun Activity.isConnectedToInternet(): Boolean {
  val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

  val activeNetwork = cm.activeNetworkInfo

  return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}