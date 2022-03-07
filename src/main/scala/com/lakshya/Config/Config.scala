package com.lakshya.Config

import com.lakshya.utils.Loggable
import com.typesafe.config._

trait Config extends Loggable{

  lazy val conf = ConfigFactory
    .load("application.conf")
    .getConfig("com.lakshya")

  // http configuration
  lazy val httpConf = conf.getConfig("http")
  lazy val connectionTimeout = httpConf.getInt("connectionTimeout")
  lazy val connectionRequestTimeout =
    httpConf.getInt("connectionRequestTimeout")
  lazy val socketTimeout = httpConf.getInt("socketTimeout")

}
