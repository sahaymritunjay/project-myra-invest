package com.myra.invest
package jobs

import ingestion.OrdersReader
import silver.OrdersCleaner

import org.apache.spark.sql.SparkSession

object OrdersETLJob {

  def main(args: Array[String]): Unit={

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Orders ETL Job")
      .getOrCreate()

    val rawOrders = OrdersReader.readOrders(spark)

    val cleanOrders = OrdersCleaner.clean(rawOrders)
    cleanOrders.show(false)

    cleanOrders.write.mode("overwrite").parquet("output/orders_clean")

    val parquetDF = spark.read.parquet("output/orders_clean")
    parquetDF.show(false)

    spark.stop()
  }
}
