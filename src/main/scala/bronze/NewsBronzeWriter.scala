package com.myra.invest
package bronze

import com.myra.invest.model.StockNews
import org.apache.spark.sql.{DataFrame, SparkSession}

object NewsBronzeWriter {


  def write(spark: SparkSession, news: Seq[StockNews]): DataFrame ={
    import spark.implicits._
    val newsDf = news.toDF()

    newsDf
  }
}
