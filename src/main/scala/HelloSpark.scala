package com.myra.invest

import org.apache.spark.sql.SparkSession

object HelloSpark {

  def main(args: Array[String]): Unit = {

    val test = SparkSession.builder()
      .appName("test")
      .master("local[*]")
      .getOrCreate()

    import test.implicits._

    val stocks = Seq(
      ("TCS", 4200),
      ("Infosys", 1650),
      ("HDFC Bank", 1980),
      ("SBI", 980),
      ("BEL", 420),
      ("HAL", 5800),
      ("Tata Motors", 780),
      ("Reliance", 3100),
      ("ITC", 510),
      ("Asian Paints", 2900)
    ).toDF("company", "price")

    stocks.sort($"price".desc).show()

    test.stop()
  }
}