package ru.nowandroid.pandorika.screen.main

import android.content.Context
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject
import ru.nowandroid.pandorika.R
import ru.nowandroid.pandorika.service.ApiService
import ru.nowandroid.pandorika.utils.fadeIn
import ru.nowandroid.pandorika.utils.fadeOut
import ru.nowandroid.pandorika.utils.isConnectedToInternet

class MainActivity :
    AppCompatActivity(), SearchView.OnQueryTextListener {

  private val jobs = mutableListOf<Job>()
  private val service: ApiService by inject()
  private val adapter: MusicAdapter by lazy { MusicAdapter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    list.setHasFixedSize(true)
    list.layoutManager = LinearLayoutManager(this)
    list.adapter = adapter

    showStub(resources.getString(R.string.open_first_app))
  }

  override fun onQueryTextSubmit(str: String): Boolean {
    if (isConnectedToInternet()) {
      if (str.trim().isNotEmpty()) {
        startSearchMusic(str)
      }
    } else {
      Toast.makeText(this@MainActivity,
                     resources.getText(R.string.internet_connection_error),
                     Toast.LENGTH_LONG).show()
    }
    return true
  }

  private fun startSearchMusic(str: String) {
    fadeIn()
    jobs += launch(UI) {
      try {
        val response = service.retrofit.getResultSearch(str).await()
        if (response.resultCount > 0) {
          adapter.set(response.results)
          hideStub()
          hideKeyboard()
        } else {
          showStub(resources.getText(R.string.we_not_found))
        }
        fadeOut()
      } catch (e: Exception) {
        Log.e(this@MainActivity.localClassName, e.message)
        fadeOut()
      }
    }
  }

  private fun hideKeyboard() {
    (this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
        (this.currentFocus ?: this.emptyView).windowToken, 0)
  }

  private fun hideStub() {
    emptyView.visibility = View.GONE
  }

  private fun showStub(text: CharSequence) {
    emptyView.visibility = View.VISIBLE
    emptyView.text = text
  }

  override fun onQueryTextChange(str: String): Boolean {
    return true
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.search_menu, menu)

    val searchItem = menu.findItem(R.id.search)
    val searchView: SearchView = MenuItemCompat.getActionView(searchItem) as SearchView
    searchView.setOnQueryTextListener(this)
    return true
  }

  override fun onDestroy() {
    jobs.forEach {
      it.cancel()
    }
    super.onDestroy()
  }
}
