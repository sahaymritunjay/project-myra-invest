package com.myra.invest
package silver


import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._


object OrdersCleaner {

  def clean(df: DataFrame): DataFrame={

    df.dropDuplicates("order_id")
      .filter(col("quantity") > 0)
      .filter(col("price").isNotNull)
      .withColumn("city",initcap(trim(col("city"))))
      .withColumn("category",trim(col("category")))
      .withColumn("revenue",col("quantity") * col("price"))
      .withColumn("price_category",when(col("price") < 2000, "Budget")
        .when(col("price") > 20000, "Premium").otherwise("Mid"))
      .withColumn("order_month",date_format(col("order_date"),"MMMM"))


  }
}
