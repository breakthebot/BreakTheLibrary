package org.breakthebot.breakthelibrary

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object BreakTheLibrary {

    const val LOGGING_NAME: String = "BreakTheLibrary"

    val logger: Logger = LoggerFactory.getLogger(LOGGING_NAME)

    init {
        logger.info("Init")
    }

}