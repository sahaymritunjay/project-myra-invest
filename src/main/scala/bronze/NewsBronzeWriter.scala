package com.myra.invest
package bronze

import com.myra.invest.model.StockNews
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object NewsBronzeWriter {

  val bronzeTable = "workspace.myra_invest_bronze_stock_news"

  def createDataFrame(spark: SparkSession, news: Seq[StockNews]): DataFrame ={

    import spark.implicits._

    news.toDF().withColumn("publishedAt",to_timestamp(col("publishedAt")))
      .withColumn("ingestionTimestamp",to_timestamp(col("ingestionTimestamp")))
      .withColumn("ingestionDate",to_date(col("ingestionTimestamp")))
  }


  def write(spark: SparkSession, news: Seq[StockNews]): Unit ={

    import spark.implicits._
    val newsDF = createDataFrame(spark, news)

    println(s"Writing ${newsDF.count()} news records to Bronze Delta.")

    newsDF.write
      .mode(SaveMode.Append)
      .format("delta")
      .saveAsTable(bronzeTable)

    println("Bronze News table updated successfully")
  }
}
