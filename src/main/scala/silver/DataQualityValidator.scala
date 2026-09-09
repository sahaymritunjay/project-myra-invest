package com.myra.invest
package silver

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object DataQualityValidator {

  def validate(df: DataFrame): DataFrame = {

    val cleanedDf = df.filter(col("close").isNotNull)
      .filter(col("volume") > 0)
      .filter(col("symbol").isNotNull)

    cleanedDf
  }
}
