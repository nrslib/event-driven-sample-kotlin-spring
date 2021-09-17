package com.example.scrumapp.notification.domain.models.watcher

import com.example.scrumapp.lib.domainsupport.valueobjects.StringValueObject

class MailAddress(value: String) : StringValueObject(value) {
    init {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+"
        val regex = Regex(emailPattern)
        if (!regex.matches(value)) {
            throw InvalidFormatException()
        }
    }
}