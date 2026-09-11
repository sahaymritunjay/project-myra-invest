package com.myra.invest
package news

object NewsSentimentAnalyzer {

  sealed trait Sentiment

  case object Positive extends Sentiment
  case object Neutral extends  Sentiment
  case object Negative extends Sentiment

  def analyze(headline: String, summary: String): Sentiment = {

    Neutral
  }
}
