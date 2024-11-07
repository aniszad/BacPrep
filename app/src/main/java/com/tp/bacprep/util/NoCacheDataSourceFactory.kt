package com.tp.bacprep.util

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSource.Factory
import androidx.media3.datasource.DefaultDataSource

@UnstableApi class NoCacheDataSourceFactory(
    private val context: Context,
    private val userAgent: String
) : Factory {

    override fun createDataSource(): DataSource {
        return DefaultDataSource(
            context,
            userAgent, // Use the default DataSpec.Factory
            false
        )
    }
}