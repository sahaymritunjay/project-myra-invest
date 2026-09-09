package com.myra.invest
package utils

import org.apache.spark.sql.expressions.Window

object SparkWindowUtils {

  val stockWindow = Window.partitionBy("symbol").orderBy("tradeDate")

  val moving5 = stockWindow.rowsBetween(-4,0)

  val moving20 = stockWindow.rowsBetween(-19,0)
}
