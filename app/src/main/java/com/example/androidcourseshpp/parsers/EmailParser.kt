package com.example.androidcourseshpp.parsers

object EmailParser {

    fun parseEMail(eMail: String): String {
        val parsedName = StringBuilder()

        val parsedEmail: MutableList<String> = eMail.split('@').toMutableList()
        val name = parsedEmail[0].filter { it.isLetter() || it == '.' }.split('.')

        parsedName.append(name[0])
        if (name.size > 1) {
            parsedName.append(" ").append(name[1])
        }

        parsedName.capitalize()

        return parsedName.toString()
    }

    private fun StringBuilder.capitalize() {
        this[0] = this[0].uppercaseChar()
        this[this.indexOf(' ') + 1] =
            this[this.indexOf(' ') + 1].uppercaseChar()
    }
}