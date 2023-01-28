package com.example.openweatherapp.logs

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class HTTPLogger: HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Timber.tag("OkHttp").d(message)
    }
}