package com.myra.invest.parser

import com.myra.invest.model.HistoricalStockPrice
import org.json4s._
import org.json4s.jackson.JsonMethods
import org.json4s.jackson.JsonMethods._

object HistoricalAlphaVantageParser {

  implicit val formats: Formats = DefaultFormats

  def parse(
             json: String,
             symbol: String,
             companyName: String
           ): Seq[HistoricalStockPrice] = {

    val parsedJson = JsonMethods.parse(json)

    val timeSeries =
      parsedJson \ "Time Series (Daily)"

    timeSeries match {

      case JObject(days) =>

        days.map {

          case (tradeDate, JObject(values)) =>

            val valueMap = values.toMap

            HistoricalStockPrice(
              symbol = symbol,
              companyName = companyName,
              tradeDate = tradeDate,
              open = valueMap("1. open").extract[String].toDouble,
              high = valueMap("2. high").extract[String].toDouble,
              low = valueMap("3. low").extract[String].toDouble,
              close = valueMap("4. close").extract[String].toDouble,
              volume = valueMap("5. volume").extract[String].toLong,
              currency = "INR",
              source = "AlphaVantage",
              ingestionTimestamp = java.time.Instant.now().toString
            )

          case _ =>
            throw new RuntimeException("Unexpected JSON format.")
        }

      case _ =>
        throw new RuntimeException(
          "Time Series (Daily) section not found in AlphaVantage response."
        )
    }
  }
}