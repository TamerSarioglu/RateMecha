package com.tamersarioglu.ratemecha.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val TOKEN_KEY = stringPreferencesKey("auth_token")
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val USERNAME_KEY = stringPreferencesKey("username")
        val EMAIL_KEY = stringPreferencesKey("email")
        val FULL_NAME_KEY = stringPreferencesKey("full_name")
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveUserInfo(
        userId: String,
        username: String,
        email: String,
        fullName: String
    ) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
            preferences[USERNAME_KEY] = username
            preferences[EMAIL_KEY] = email
            preferences[FULL_NAME_KEY] = fullName
        }
    }

    val token: Flow<String?> = dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    val userId: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_ID_KEY]
    }

    val userInfo: Flow<UserInfo?> = dataStore.data.map { preferences ->
        val userId = preferences[USER_ID_KEY]
        val username = preferences[USERNAME_KEY]
        val email = preferences[EMAIL_KEY]

        if (userId != null && username != null && email != null) {
            UserInfo(
                id = userId,
                username = username,
                email = email,
                fullName = preferences[FULL_NAME_KEY]
            )
        } else {
            null
        }
    }

    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    data class UserInfo(
        val id: String,
        val username: String,
        val email: String,
        val fullName: String?
    )
}
