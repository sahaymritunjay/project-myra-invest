package com.myra.invest
package jobs

import config.Nifty50Config
import ingestion.providers.AlphaVantageProvider

import org.apache.spark.sql.SparkSession

object StockIngestionJob {

  def main(args : Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Stock Ingestion Nifty50")
      .master("local[*]")
      .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
      .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
      .getOrCreate()

    import spark.implicits._
    val provider = new AlphaVantageProvider()

    val stockPrices = Nifty50Config.topStocks.map(provider.fetch)

    val stockDf = stockPrices.toDF()
    stockDf.show(false)

    stockDf.write.format("delta")
      .mode("append")
      .save("datasets/bronze/daily_stock_snapshot")

    spark.stop()

  }
}
