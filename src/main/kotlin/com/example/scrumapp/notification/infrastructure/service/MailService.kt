package com.example.scrumapp.notification.infrastructure.service

interface MailService {
    fun send(mailAddress: String, title: String, content: String)
}