package com.example.androidcourseshpp.data.source.local.userdata

interface DatabaseValidityProvider {
    fun isDataValid(): Boolean
    fun setDataValidity(isDataValid: Boolean)
}