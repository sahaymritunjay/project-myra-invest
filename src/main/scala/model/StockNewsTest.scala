package com.myra.invest
package model

object StockNewsTest extends  App {

  val sampleNews = StockNews(

    symbol = "INFY.BSE",
    companyName ="Infosys",
    headline = "Infosys wins major AI transformation contract",
    summary = "Infosys announced a multi-year AI partnership with a global retailer.",
    publisher = "Economic Times",
    publishedAt = "2026-09-11T08:00:00Z",
    url = "https://example.com/news/infosys-ai",
    source = "MarketAux",
    ingestionTimestamp = java.time.Instant.now().toString

  )

  println(sampleNews)
}
