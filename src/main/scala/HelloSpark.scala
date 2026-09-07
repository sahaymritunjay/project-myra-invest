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
      ("Reliance", 3100)
    ).toDF("company", "price")

    stocks.show()

    test.stop()
  }
}