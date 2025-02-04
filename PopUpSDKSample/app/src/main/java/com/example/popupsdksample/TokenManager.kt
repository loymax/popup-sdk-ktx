package com.example.popupsdksample

import android.content.Context
import android.content.SharedPreferences

object TokenManager {

    private const val PREF_NAME = "auth_prefs"
    private const val TOKEN_KEY = "auth_token"

    private const val FCM_PREF_NAME = "firebase_prefs"
    private const val FCM_TOKEN_KEY = "firebase_token"

    private lateinit var authPrefs: SharedPreferences
    private lateinit var fcmPrefs: SharedPreferences

    /**
     * Инициализация TokenManager (вызывается в Application)
     */
    fun init(context: Context) {
        authPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        fcmPrefs = context.getSharedPreferences(FCM_PREF_NAME, Context.MODE_PRIVATE)
    }

    // Методы для обычного токена

    /**
     * Сохранение токена
     */
    fun saveToken(token: String) {
        authPrefs.edit().putString(TOKEN_KEY, token).apply()
    }

    /**
     * Получение токена
     */
    fun getToken(): String? {
        return authPrefs.getString(TOKEN_KEY, null)
    }

    /**
     * Очистка токена
     */
    fun clearToken() {
        authPrefs.edit().remove(TOKEN_KEY).apply()
    }

    /**
     * Проверка, есть ли сохраненный токен
     */
    fun hasToken(): Boolean {
        return getToken() != null
    }

    // Методы для Firebase-токена

    /**
     * Сохранение Firebase-токена
     */
    fun saveFirebaseToken(fcmToken: String) {
        fcmPrefs.edit().putString(FCM_TOKEN_KEY, fcmToken).apply()
    }

    /**
     * Получение Firebase-токена
     */
    fun getFirebaseToken(): String? {
        return fcmPrefs.getString(FCM_TOKEN_KEY, null)
    }

    /**
     * Очистка Firebase-токена
     */
    fun clearFirebaseToken() {
        fcmPrefs.edit().remove(FCM_TOKEN_KEY).apply()
    }

    /**
     * Проверка, есть ли сохраненный Firebase-токен
     */
    fun hasFirebaseToken(): Boolean {
        return getFirebaseToken() != null
    }
}