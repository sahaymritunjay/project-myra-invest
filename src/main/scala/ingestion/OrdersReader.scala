package com.myra.invest
package ingestion

import com.myra.invest.model.OrderSchema
import org.apache.spark.sql.{DataFrame, SparkSession}

object OrdersReader {

  def readOrders(spark: SparkSession): DataFrame = {

    spark.read
      .option("header","true")
      .schema(OrderSchema.schema)
      .csv("datasets/orders.csv")

  }

}
