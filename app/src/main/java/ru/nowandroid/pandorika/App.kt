package ru.nowandroid.pandorika

import android.app.Application
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.applicationContext
import ru.nowandroid.pandorika.service.ApiService

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin(listOf(mainModule))
  }
}

val mainModule = applicationContext {
  bean { ApiService() }
}