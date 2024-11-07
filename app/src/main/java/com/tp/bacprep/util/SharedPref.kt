package com.tp.bacprep.util

import android.content.Context
import android.content.SharedPreferences


class SharedPref(context : Context) : android.app.Application() {
    private val obSharedPref : SharedPreferences = context.getSharedPreferences(Constants.OB_SHARED_PREF, MODE_PRIVATE)
    private val userInfo : SharedPreferences = context.getSharedPreferences(Constants.USER_INFO_SHARED_PREF, MODE_PRIVATE)
    private val userIdSharedPref : SharedPreferences = context.getSharedPreferences(Constants.USER_ID_SHARED_PREF, MODE_PRIVATE)
    private val branchSharedPref : SharedPreferences = context.getSharedPreferences(Constants.USER_BRANCH_INFO, MODE_PRIVATE)
    private val languageSharedPref : SharedPreferences = context.getSharedPreferences(Constants.USER_LANGUAGE_INFO, MODE_PRIVATE)
    private val darkMode : SharedPreferences = context.getSharedPreferences(Constants.THEME_MODE, MODE_PRIVATE)

     fun setObVisited(isVisited: Boolean){
         val editor = obSharedPref.edit()
         editor.apply{
            putBoolean(Constants.IS_VISITED, isVisited)
            apply()
        }
    }

    fun getObVisited(): Boolean {
        return obSharedPref.getBoolean(Constants.IS_VISITED, false)
    }
     fun setThemeMode(isDarkMode: Boolean){
         val editor = darkMode.edit()
         editor.apply{
            putBoolean(Constants.THEME_MODE, isDarkMode)
            apply()
        }
    }

    fun getThemeMode(): Boolean {
        return darkMode.getBoolean(Constants.THEME_MODE, false)
    }

    fun saveUserName(userId : String, userName : String){
        val editor = userInfo.edit()
        editor.apply {
            putString(userId, userName)
            apply()
        }
    }
    fun getUserName(userId : String) : String?{
        return userInfo.getString(userId, "")
    }
    fun saveUserId(userId : String){
        val editor = userIdSharedPref.edit()
        editor.apply {
            putString(Constants.USER_ID, userId)
            apply()
        }
    }
    fun getUserId() : String?{
        return userIdSharedPref.getString(Constants.USER_ID, "")
    }

    fun setMyBranch(branch : String){
        val editor = branchSharedPref.edit()
        editor.apply{
            putString(Constants.CURRENT_USER_BRANCH, branch)
            apply()
        }
    }
    fun getMyBranch(): String? {
        return branchSharedPref.getString(Constants.CURRENT_USER_BRANCH, "")
    }

    fun setMyLanguage(languageCode: String) {
        val editor = languageSharedPref.edit()
        editor.apply{
            putString(Constants.USER_LANGUAGE_INFO, languageCode)
            apply()
        }
    }

    fun getMyLanguage(): String? {
        return languageSharedPref.getString(Constants.USER_LANGUAGE_INFO, "")
    }
}