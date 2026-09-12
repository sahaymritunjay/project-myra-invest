package com.myra.invest
package news

import config.NewsApiConfig

import com.myra.invest.bronze.NewsBronzeWriter
import com.myra.invest.model.StockNews
import com.myra.invest.parser.NewsApiParser
import com.myra.invest.utils.HttpClient
import org.apache.spark.sql.SparkSession

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object NewsIngestionJob {

  def buildUrl(): String =
    {
      val companyQuery =
        Seq(
          "Reliance Industries",
          "Tata Consultancy Services",
          "Infosys",
          "HDFC Bank",
          "ICICI Bank"
        ).mkString(" OR ")

      val encodedQuery =
        URLEncoder.encode(companyQuery, StandardCharsets.UTF_8.toString)


      s"${NewsApiConfig.baseUrl}" +
        s"?api_token=${NewsApiConfig.apiKey}" +
        s"&search=$encodedQuery" +
        s"&language=${NewsApiConfig.language}" +
        s"&countries=${NewsApiConfig.countries}" +
        s"&limit=${NewsApiConfig.pageSize}"
    }

  def fetchNews(): Seq[StockNews] = {

    println("Downloading today's financial news...")

    val jsonResponse =
      HttpClient.get(buildUrl())

    val articles =
      NewsApiParser.parse(jsonResponse)

    println(s"Downloaded ${articles.size} Project Myra news articles.")

    articles
  }

  def printArticles(news: Seq[StockNews]): Unit = {

    println()
    println("============= SAMPLE NEWS =============")

    news.take(5).foreach { article =>

      println("--------------------------------------")
      println(s"Company   : ${article.companyName}")
      println(s"Headline  : ${article.headline}")
      println(s"Publisher : ${article.publisher}")
      println(s"Published : ${article.publishedAt}")
    }

    println("--------------------------------------")
    println(s"Displayed ${math.min(news.size, 5)} articles.")
  }

  def main(args: Array[String]): Unit = {

    println("==============================================")
    println("PROJECT MYRA - LIVE NEWS INGESTION")
    println("==============================================")

    val spark = SparkSession.builder()
      .appName("Bronze News")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val news = fetchNews()

    printArticles(news)

    NewsBronzeWriter.write(spark, news)

    println()
    println("Sprint 11 Day 3 completed successfully.")
  }

}
