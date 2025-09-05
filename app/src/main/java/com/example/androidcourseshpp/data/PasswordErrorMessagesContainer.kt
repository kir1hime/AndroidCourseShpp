package com.example.androidcourseshpp.data

import android.content.Context
import com.example.androidcourseshpp.R

class PasswordErrorMessagesContainer(private val context: Context) {

     fun getMessages() : List<String> = with(context) {
        listOf(
            getString(R.string.less_8_symbols_pswd_error, MIN_NUM_OF_CHARS_IN_PASSWORD),
            getString(R.string.capital_letter_error),
            getString(R.string.lowercase_letter_error),
            getString(R.string.special_symbol_error),
            getString(R.string.numbers_error),
        )
    }
}