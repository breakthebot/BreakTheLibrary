package org.breakthebot.breakthelibrary

import java.util.logging.Logger
import kotlin.math.log

object BreakTheLibrary {

    const val LOGGING_NAME: String = "BreakTheLibrary"



    val logger: Logger = Logger.getLogger(LOGGING_NAME)
    init {
        logger.info("Init")
    }

}