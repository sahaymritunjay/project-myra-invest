package com.myra.invest
package gold

import com.myra.invest.utils.{HistoricalPriceGenerator, Logger}
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession

object GoldTransformationJob {


  def main(args: Array[String]): Unit =
    {
      val config = ConfigFactory.load()

      val spark = SparkSession.builder().appName("Gold Transformation")
        .master("local[*]")
        .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
        .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
        .getOrCreate()

      Logger.info("Reading Silver Table")

      val silverDf = spark.read
        .format("delta").load(config.getString("myra-invest.silver-path"))

      val historicalDf = HistoricalPriceGenerator.generate(silverDf)

      Logger.info(s"Historical Rows = ${historicalDf.count()}")

      val technicalDf = TechnicalIndicatorCalculator.calculate(historicalDf)

      val goldDf = SignalGenerator.generate(technicalDf)

      goldDf.show(false)

      Logger.info("Writing Gold Dataset")

      goldDf.write.format("delta")
        .mode("overwrite")
        .partitionBy("tradeDate")
        .save(config.getString("myra-invest.gold-path"))

      Logger.info("Gold Layer Completed")
    }
}
