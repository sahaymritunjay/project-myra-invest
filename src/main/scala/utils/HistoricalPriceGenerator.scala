package com.myra.invest
package utils

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object HistoricalPriceGenerator {

  def generate(df: DataFrame): DataFrame = {

    val history = (0 until 30).foldLeft(df) {(tempDf,day) =>
      tempDf.union(
        df.withColumn("tradeDate",date_sub(current_date(),day))
          .withColumn("close",round(col("close") - lit(day * 2),2))
          .withColumn("open",round(col("open") - lit(day * 2.5),2))
          .withColumn("high",round(col("high") - lit(day * 1.5),2))
          .withColumn("low",round(col("low") - lit(day * 3),2))
      )
    }
history
  }
}
