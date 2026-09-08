package com.myra.invest
package model

import org.apache.spark.sql.types._

object OrderSchema {

  val schema: StructType= StructType(List(
    StructField("order_id",IntegerType),
    StructField("customer_id", StringType),
    StructField("product",StringType),
    StructField("category", StringType),
    StructField("city", StringType),
    StructField("quantity",IntegerType),
    StructField("price",IntegerType),
    StructField("order_date", DateType)

  ))

}