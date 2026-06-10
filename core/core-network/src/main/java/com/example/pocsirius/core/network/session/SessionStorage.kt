package com.example.pocsirius.core.network.session

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionStorage @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private var isFirstAttempt = true

    private val prefs: SharedPreferences by lazy { createPrefs() }

    private fun createPrefs(): SharedPreferences =
        runCatching {
            EncryptedSharedPreferences.create(
                context,
                PREFS_FILE_NAME,
                MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
            )
        }.getOrElse {
            if (isFirstAttempt) {
                isFirstAttempt = false
                context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
                    .edit { clear() }
                createPrefs()
            } else throw it
        }

    fun saveToken(token: String) = prefs.edit { putString(KEY_TOKEN, token) }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun clearSession() = prefs.edit { clear() }

    fun hasSession(): Boolean = getToken() != null

    companion object {
        private const val PREFS_FILE_NAME = "sirius_supervisor_session"
        private const val KEY_TOKEN = "auth_token"
    }
}
