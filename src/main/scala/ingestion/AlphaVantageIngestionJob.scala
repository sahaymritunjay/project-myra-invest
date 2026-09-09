package com.myra.invest
package ingestion

import config.{ApiConfig, TableNames}
import model.StockPrice
import utils.HttpClient

import io.circe.Json
import io.circe.parser._
import org.apache.spark.sql.SparkSession

object AlphaVantageIngestionJob {

  private def buildUrl(symbol: String): String =
    s"${ApiConfig.alphaVantageBaseUrl}?function=GLOBAL_QUOTE&symbol=$symbol&apikey=${ApiConfig.alphaVantageApiKey}"

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("Ingestion").master("local[*]")
      .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
      .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
      .getOrCreate()

    import spark.implicits._
    val symbol = "IBM"
    val response = HttpClient.get(buildUrl(symbol))

    val json: Json = parse(response).getOrElse(throw  new RuntimeException("Invalid JSON Recieved"))
    val quote = json.hcursor.downField("Global Quote")
    val stockPrice = StockPrice(
      symbol = quote.get[String]("01. symbol").getOrElse(""),
      companyName = "IBM",
      tradeDate = java.time.LocalDate.now().toString,
      open = quote.get[String]("02. open").getOrElse("0").toDouble,
      high = quote.get[String]("03. high").getOrElse("0").toDouble,
      low = quote.get[String]("04. low").getOrElse("0").toDouble,
      close = quote.get[String]("05. price").getOrElse("0").toDouble,
      volume = quote.get[String]("06. volume").getOrElse("0").toLong,
      currency = "USD",
      source = "AlphaVantage"
    )

    val stockDf = Seq(stockPrice).toDF()
    stockDf.printSchema()
    stockDf.show(false)

    stockDf.write
      .format("delta")
      .mode("overwrite")
      .saveAsTable(TableNames.bronzeStockTable)
  }
}
