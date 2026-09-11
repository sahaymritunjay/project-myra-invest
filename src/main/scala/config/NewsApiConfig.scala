package com.myra.invest
package config

object NewsApiConfig {
  val apiKey: String ="utdKcM0vaRC44eAkTLF3t7GGadz28gCCz0KXYtQU"

  val baseUrl: String = "https://api.marketaux.com/v1/news/all"

  val language: String = "en"

  val countries: String = "in"

  val pageSize: Int = 20;

  // Initial stock universe for Project Myra Version 1
  val stockUniverse: Seq[(String, String)] = Seq(
    ("RELIANCE.BSE", "Reliance Industries"),
    ("TCS.BSE", "Tata Consultancy Services"),
    ("INFY.BSE", "Infosys"),
    ("HDFCBANK.BSE", "HDFC Bank"),
    ("ICICIBANK.BSE", "ICICI Bank")
  )
}
