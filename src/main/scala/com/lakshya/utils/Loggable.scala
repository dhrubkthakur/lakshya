package com.lakshya.utils

import org.apache.log4j.Logger

/**
 * Loggable.scala - Provide the logger support across the application
 */
trait Loggable {

  @transient private lazy val logger = Logger.getLogger(getClass.getName)

  protected def warn(msg: String): Unit = {
    logger.warn(msg)

  }
  protected def warn(msg: String, e: Throwable): Unit = {
    logger.warn(msg, e)

  }
  protected def error(msg: String, e: Throwable): Unit = {
    logger.error(msg, e)
  }

  protected def error(msg: String): Unit = {
    logger.error(msg)
  }

  protected def info(msg: String): Unit = {
    logger.info(msg)
  }

  protected def debug(msg: String): Unit = {
    logger.debug(msg)
  }

}
