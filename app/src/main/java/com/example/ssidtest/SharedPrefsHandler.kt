package com.example.ssidtest

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsHandler {
    private var sharedPreferences: SharedPreferences?=null
    constructor(context: Context){
        sharedPreferences=context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun putString(key: String, value:String){
        var editor=sharedPreferences!!.edit()
        editor.putString(key,value)
        editor.apply()
    }
    fun getString(key: String):String?{
        return sharedPreferences!!.getString(key,null)
    }

    fun clearPreferences(){
        var editor=sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
    }

}