package com.myra.invest
package ingestion

import config.ApiConfig

import com.myra.invest.model.HistoricalStockPrice
import com.myra.invest.parser.HistoricalAlphaVantageParser
import com.myra.invest.utils.{DateUtils, HttpClient}

import java.time.LocalDate

object AlphaVantageHistoricalIngestionJob {

  // Initial stock universe
  private val stocks = Seq(
    ("RELIANCE.BSE", "Reliance Industries"),
    ("TCS.BSE", "Tata Consultancy Services"),
    ("INFY.BSE", "Infosys"),
    ("HDFCBANK.BSE", "HDFC Bank"),
    ("ICICIBANK.BSE", "ICICI Bank")
  )

   def buildUrl(symbol: String): String =
    s"${ApiConfig.alphaVantageBaseUrl}?function=TIME_SERIES_DAILY&symbol=$symbol&outputsize=full&apikey=${ApiConfig.alphaVantageApiKey}"

  def filterLastYear(
                    prices: Seq[HistoricalStockPrice]
                    ): Seq[HistoricalStockPrice] = {
    val cutoffDate = LocalDate.parse(DateUtils.oneYearAgo())

    prices.filter(price => LocalDate.parse(price.tradeDate).isAfter(cutoffDate.minusDays(1)))
      .sortBy(_.tradeDate)
  }
    def fetchHistoricalPrices(
                             symbol: String,
                             companyName: String
                             ): Seq[HistoricalStockPrice] = {
      println(s"Fetching Histoprical Prices for $symbol")

      val response = HttpClient.get(buildUrl(symbol))
      val parsedPrices = HistoricalAlphaVantageParser.parse(response,symbol, companyName)

      val filteredPrices = filterLastYear(parsedPrices)

      println(s"Fetched ${filteredPrices.size} historical records for $symbol")

      filteredPrices

    }

  def main(args: Array[String]): Unit =
    {
    println("+==================Project Myra INGESTION")

      val historicalDataset = stocks.flatMap{

        case (symbol, company) =>
          try {
            val prices = fetchHistoricalPrices(symbol,company)

            Thread.sleep(15000)

            prices
          }catch {
            case ex: Exception =>
              println(
                s"Failed for $symbol : ${ex.getMessage}"
              )

              Seq.empty[HistoricalStockPrice]
          }
      }

      println()
      println("============== SUMMARY ==============")
      println(s"Stocks Configured      : ${stocks.size}")
      println(s"Historical Records     : ${historicalDataset.size}")

      if (historicalDataset.nonEmpty) {

        val earliest =
          historicalDataset.map(_.tradeDate).min

        val latest =
          historicalDataset.map(_.tradeDate).max

        println(s"Earliest Trade Date    : $earliest")
        println(s"Latest Trade Date      : $latest")

        println()
        println("Sample Records")

        historicalDataset.take(5).foreach(println)
      }

      println("=====================================")
    }
}
