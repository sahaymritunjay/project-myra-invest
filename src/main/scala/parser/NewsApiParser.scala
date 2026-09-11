package com.myra.invest
package parser

import model.StockNews

import com.myra.invest.config.CompanySymbolMapper
import org.json4s.jackson.JsonMethods
import org.json4s._

object NewsApiParser {
  implicit val formats: Formats = DefaultFormats

  def parse(json: String): Seq[StockNews] = {

    val parsedJson = JsonMethods.parse(json)

    val articles = (parsedJson \ "data").children

    articles.flatMap { article =>

      val headline =
        (article \ "title").extractOpt[String].getOrElse("")

      val summary =
        (article \ "description").extractOpt[String].getOrElse("")

      val publisher =
        (article \ "source").extractOpt[String].getOrElse("Unknown")

      val publishedAt =
        (article \ "published_at").extractOpt[String].getOrElse("")

      val url =
        (article \ "url").extractOpt[String].getOrElse("")

      val searchableText = headline + " " + summary

      CompanySymbolMapper.lookupCompany(searchableText).map {
        case (symbol, companyName) =>

          StockNews(
            symbol = symbol,
            companyName = companyName,
            headline = headline,
            summary = summary,
            publisher = publisher,
            publishedAt = publishedAt,
            url = url,
            source = "MarketAux",
            ingestionTimestamp = java.time.Instant.now().toString
          )
      }
    }

  }
}
