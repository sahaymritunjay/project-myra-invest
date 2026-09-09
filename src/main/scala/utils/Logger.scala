package com.myra.invest
package utils

import java.time.LocalDateTime

object Logger {

  def info(message: String): Unit ={
    println(s"[INFO] ${LocalDateTime.now()} : $message")
  }

  def warn(message: String): Unit ={
    println(s"[WARN] ${LocalDateTime.now()} : $message")
  }

  def error(message: String): Unit ={
    println(s"[ERROR] ${LocalDateTime.now()} : $message")
  }

}
