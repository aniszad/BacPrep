package com.tp.bacprep

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.tp.bacprep.util.SharedPref
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class MyApp : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(updateBaseContextLocale(base))
    }

    private fun updateBaseContextLocale(context: Context): Context {
        val languageCode = SharedPref(context).getMyLanguage() ?: ""
        val locale = Locale(languageCode, getCountryCode(languageCode))
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    fun updateLocale(context: Context, languageCode: String) {
        val sharedPref = SharedPref(context)
        sharedPref.setMyLanguage(languageCode)
        val locale = Locale(languageCode, getCountryCode(languageCode))
        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }


    fun resetLocaleToSystemDefault(context: Context) {
        // Get the system's default locale
        val defaultLocale = Resources.getSystem().configuration.locale

        // Set it as the default Locale
        Locale.setDefault(defaultLocale)

        // Update the configuration
        val config = Configuration()
        config.locale = defaultLocale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    private fun getCountryCode(languageCode: String): String {
        return when (languageCode) {
            "ar" -> "DZ" // Arabic in Algeria
            "fr" -> "FR" // French in France
            "en" -> "US" // English in the United States
            else -> "FR" // Default to French in France
        }
    }
}
