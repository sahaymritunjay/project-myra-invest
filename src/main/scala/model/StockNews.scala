package com.myra.invest
package model

case class StockNews
(
symbol:String,
companyName: String,
headline: String,
summary: String,
publisher: String,
publishedAt: String,
url: String,
source: String,
ingestionTimestamp: String
)
