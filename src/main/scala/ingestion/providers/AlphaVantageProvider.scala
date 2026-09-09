package com.myra.invest
package ingestion.providers

import config.ApiConfig
import model.StockPrice
import utils.HttpClient

import io.circe.parser._

import java.time.LocalDate

class AlphaVantageProvider extends Provider {

  override def fetch(symbol: String): StockPrice = {

    val url = s"${ApiConfig.alphaVantageBaseUrl}?function=GLOBAL_QUOTE&symbol=$symbol&apikey=${ApiConfig.alphaVantageApiKey}"

    val response = HttpClient.get(url)

    val json = parse(response).getOrElse(throw new RuntimeException("Invalid JSON"))

    val quote = json.hcursor.downField("Global Quote")

    StockPrice(
      symbol = quote.get[String]("01. symbol").getOrElse(symbol),
      companyName = symbol,
      tradeDate = LocalDate.now().toString,
      open = quote.get[String]("02. open").getOrElse("0").toDouble,
      high = quote.get[String]("03. high").getOrElse("0").toDouble,
      low = quote.get[String]("04. low").getOrElse("0").toDouble,
      close = quote.get[String]("05. price").getOrElse("0").toDouble,
      volume = quote.get[String]("06. volume").getOrElse("0").toLong,
      currency = "USD",
      source = "AlphaVantage"
    )


  }
}
