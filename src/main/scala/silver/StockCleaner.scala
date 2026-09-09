package com.myra.invest
package silver

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object StockCleaner {

  def clean(df: DataFrame): DataFrame= {

    df.dropDuplicates("symbol","tradeDate")
      .withColumn("tradeDate", to_date(col("tradeDate")))
      .withColumn("ingestionTimestamp",current_timestamp())
      .withColumn("priceChange",round(col("close") - col("open"),2))
      .withColumn("priceChangePct", round(((col("close") - col("open")) / col("open")) *100, 2))

  }
}
