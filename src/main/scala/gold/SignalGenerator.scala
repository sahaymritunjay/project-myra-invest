package com.myra.invest
package gold

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object SignalGenerator {

  def generate(df: DataFrame): DataFrame =
    {
      df.withColumn("technicalSignal",when(col("close") > col("movingAvg20") && col("dailyReturnPct") > 1,"BUY")
      .when(col("close") > col("movingAvg5"), "HOLD").otherwise("WATCH"))
    }
}
