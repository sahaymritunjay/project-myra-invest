package com.myra.invest
package config

object NewsApiConfigTest extends App {

  println("===== NEWS CONFIGURATION =====")

  println(s"Base URL : ${NewsApiConfig.baseUrl}")
  println(s"Language : ${NewsApiConfig.language}")
  println(s"Country  : ${NewsApiConfig.countries}")
  println(s"Page Size: ${NewsApiConfig.pageSize}")

  println()
  println("Tracked Companies")
  println("-----------------")

  NewsApiConfig.stockUniverse.foreach {
    case (symbol, companyName) =>
      println(s"$symbol -> $companyName")
  }
}