package com.example.scrumapp.notification.inmemoryinfrastructure.service

import com.example.scrumapp.notification.infrastructure.service.MailService
import org.slf4j.LoggerFactory

class NopMailService : MailService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun send(mailAddress: String, title: String, content: String) {
        logger.info("mailAddress:${mailAddress},title:${title},content:${content}")
    }
}