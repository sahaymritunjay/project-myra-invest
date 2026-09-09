package com.myra.invest
package gold

import com.myra.invest.utils.SparkWindowUtils
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object TechnicalIndicatorCalculator {

  def calculate(df: DataFrame): DataFrame = {

  df.withColumn("dailyReturnPct",round(((col("close") - col("open")) /col("open")) * 100, 2))
    .withColumn("movingAvg5", round(avg("close").over(SparkWindowUtils.moving5),2))
    .withColumn("movingAvg20",round(avg("close").over(SparkWindowUtils.moving20),2))
    .withColumn("volatilty5",round(stddev("close").over(SparkWindowUtils.moving5),2))


  }
}
