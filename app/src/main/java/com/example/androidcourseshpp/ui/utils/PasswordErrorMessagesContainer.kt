package com.example.androidcourseshpp.ui.utils

import com.example.androidcourseshpp.R

object PasswordErrorMessagesContainer {

    fun getMessageResourceIds(): List<Int> {
        return listOf(
            R.string.less_8_symbols_pswd_error,
            R.string.capital_letter_error,
            R.string.lowercase_letter_error,
            R.string.special_symbol_error,
            R.string.numbers_error
        )
    }
}