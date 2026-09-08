package com.myra.invest
package model

case class StockPrice (
                      symbol: String,
                      companyName: String,
                      tradeDate: String,
                      open: Double,
                      high: Double,
                      low: Double,
                      close: Double,
                      volume: Long,
                      currency: String,
                      source: String
                      )
