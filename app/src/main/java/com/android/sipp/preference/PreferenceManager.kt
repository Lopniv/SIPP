package com.android.sipp.preference

import android.content.Context
import com.android.sipp.model.Users

class PreferenceManager(context: Context) {
    private val preferences = context.getSharedPreferences("User_Pref", Context.MODE_PRIVATE)

    fun saveUserData(user: Users){
        setUserId(Constants.KEY_USER_ID, user.id)
        setFirstName(Constants.KEY_FIRST_NAME, user.firstName)
        setLastName(Constants.KEY_LAST_NAME, user.lastName)
        setEmail(Constants.KEY_EMAIL, user.email)
        setPhone(Constants.KEY_PHONE, user.phone)
        setType(Constants.KEY_TYPE, user.type)
    }

    fun setUserId(key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

    fun getUserId(key: String): String?{
        return preferences.getString(key, null)
    }

    fun setToken(key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

    fun getToken(key: String): String?{
        return preferences.getString(key, null)
    }

    fun setFirstName(key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

    fun getFirstName(key: String): String?{
        return preferences.getString(key, null)
    }

    fun setLastName(key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

    fun getLastName(key: String): String?{
        return preferences.getString(key, null)
    }

    fun setEmail(key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

    fun getEmail(key: String): String?{
        return preferences.getString(key, null)
    }

    fun setPhone(key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

    fun getPhone(key: String): String?{
        return preferences.getString(key, null)
    }

    fun setType(key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

    fun getType(key: String): String?{
        return preferences.getString(key, null)
    }

    fun setAddress(key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

    fun getAddress(key: String): String?{
        return preferences.getString(key, null)
    }

    fun clearUserCredentials() {
        preferences.edit().clear().apply()
    }
}