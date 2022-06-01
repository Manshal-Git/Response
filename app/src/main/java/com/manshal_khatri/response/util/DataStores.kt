package com.manshal_khatri.response.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.manshal_khatri.response.util.DataStores.preferenceDataStoreScores
import kotlinx.coroutines.flow.first

object DataStores {
    val Context.preferenceDataStoreScores : DataStore<Preferences> by preferencesDataStore("scores")
    val Context.preferenceDataStoreAuth : DataStore<Preferences> by preferencesDataStore("auth")

}