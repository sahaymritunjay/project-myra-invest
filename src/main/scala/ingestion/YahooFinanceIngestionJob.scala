package com.myra.invest
package ingestion

import utils.HttpClient

import org.apache.spark.sql.SparkSession

object YahooFinanceIngestionJob {

  def main(args: Array[String]): Unit ={
    val spark = SparkSession.builder()
      .appName("Yahoo Finance Ingestion")
      .master("local[*]")
      .getOrCreate()

    spark.stop()
  }

  private def yahooUrl(symbol: String): String =s"https://query1.finance.yahoo.com/v8/finance/chart/$symbol?interval=1d&range=1d"

  val json: String = HttpClient.get(yahooUrl("RELIANCE.NS"))
  println(json.take(300))
}
