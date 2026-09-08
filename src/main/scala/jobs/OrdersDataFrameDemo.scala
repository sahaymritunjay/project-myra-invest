package com.myra.invest
package jobs

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object OrdersDataFrameDemo {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Orders DataFrame Demo")
      .master("local[*]")
      .getOrCreate()

    import org.apache.spark.sql.types._

    val orderSchema = StructType(List(
      StructField("order_id", IntegerType),
      StructField("customer_id", StringType),
      StructField("product", StringType),
      StructField("category", StringType),
      StructField("city", StringType),
      StructField("quantity", IntegerType),
      StructField("price", IntegerType),
      StructField("order_date", DateType)
    ))

    val orders = spark.read
      .option("header","true")
      .schema(orderSchema)
    //  .option("inferSchema","true")
      .csv("datasets/orders.csv")

    val electronics = orders.filter(col("category") === "Electronics")

    val revenue = orders.withColumn(
      "revenue",
      col("quantity") * col("price")
    )


    electronics.show()

   // electronics.count()

   // revenue.collect()

    orders.show()



//    orders.printSchema()
//    orders.count()
//    orders.columns.foreach(println)

    spark.stop()
  }
}
