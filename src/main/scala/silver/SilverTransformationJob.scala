package com.myra.invest
package silver

import com.myra.invest.utils.Logger
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession

object SilverTransformationJob {


  def main(args: Array[String]): Unit =
    {
      val config = ConfigFactory.load()

      val spark = SparkSession.builder()
        .appName("Silver Transformation Job")
        .master("local[*]")
        .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
        .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
        .getOrCreate()

      Logger.info("Reading Bronze Dataset")

      val bronzeDf = spark.read
        .format("delta")
        .load(config.getString("myra-invest.bronze-path"))

      val validateDf = DataQualityValidator.validate(bronzeDf);

      val silverDf = StockCleaner.clean(validateDf);

      Logger.info(s"Silver Records = ${silverDf.count()}")

      silverDf.printSchema()

      silverDf.show(false)

      Logger.info("Writing Silver Dataset ")

      silverDf.write
        .format("delta")
        .mode("overwrite")
        .partitionBy("tradeDate")
        .save(config.getString("myra-invest.silver-path"))

      Logger.info("Silver Transformation Completed")

      spark.stop()

    }

}
