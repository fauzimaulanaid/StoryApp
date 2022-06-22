package com.fauzimaulana.storyapp.view.preference

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.fauzimaulana.storyapp.R
import java.util.*

class PreferenceFragment: PreferenceFragmentCompat() {

    private lateinit var languagePreference: Preference
    private lateinit var LANGUAGE: String

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummary()
        languagePreference.setOnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }
    }

    private fun init() {
        LANGUAGE = resources.getString(R.string.key_language)
        languagePreference = findPreference<Preference>(LANGUAGE) as Preference
    }

    private fun setSummary() {
        val languageSelected = Locale.getDefault().toString()
        if (languageSelected == "en_US") {
            languagePreference.summary = resources.getString(R.string.english)
        } else {
            languagePreference.summary = resources.getString(R.string.bahasa)
        }
    }
}