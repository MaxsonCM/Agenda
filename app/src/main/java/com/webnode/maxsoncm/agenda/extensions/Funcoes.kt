package com.webnode.maxsoncm.agenda.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.Double.parseDouble
import java.lang.RuntimeException

fun isNumeric(texto : String): Boolean {

    var numeric = true

    try {
        val num = parseDouble(texto)
    } catch (e: NumberFormatException) {
        numeric = false
    }


    return numeric
}

fun View.showKeybord() : Boolean{
    try{
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        this.requestFocus()
        imm.showSoftInput(this, 0)
        return true
    }catch (ignorar: RuntimeException){
        return false
    }
}


fun View.hideKeybord() : Boolean{
    try{
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }catch (ignorar: RuntimeException){
        return false
    }
}