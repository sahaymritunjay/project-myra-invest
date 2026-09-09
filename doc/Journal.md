Why did Spark infer price as Integer?

Why is order_date still a String?

What is a DataFrame internally?

What is a DataFrame?

Why is an explicit schema preferred in production?

What is the difference between a Transformation and an Action?

Explain Lazy Evaluation in your own words (3–5 lines).

Why did we separate OrdersReader and OrdersCleaner?

Why is SparkSession passed into OrdersReader?

What makes this ETL job more production-ready than HelloSpark?

Why did we write Parquet instead of CSV?

## Sprint 5 — Live Market Ingestion Engine

**Date:** 8 September 2026

### Objective

Build the first live stock market ingestion service for Project Myra Invest.

### What I Built

* Created reusable configuration layer (`ApiConfig`).
* Created reusable HTTP client.
* Connected to Alpha Vantage REST API.
* Parsed live JSON response using Circe.
* Converted business object into Spark DataFrame.
* Wrote first Delta Lake dataset locally.

### Engineering Learnings

* Keep API logic separate from business logic.
* Domain models simplify Spark transformations.
* Delta Lake is the preferred storage format for Bronze ingestion.

### Bugs Faced

* Yahoo Finance returned HTTP 429.
* Switched to Alpha Vantage as a documented API provider.
* Added timeout and HTTP status handling in the client.


## Sprint 6 — Nifty50 Ingestion Framework

**Objective**

Design a reusable ingestion framework capable of supporting multiple market providers.

### What I Built

* Provider trait.
* AlphaVantageProvider implementation.
* Nifty50 configuration object.
* StockIngestionJob fetching multiple stocks.
* Historical Bronze snapshot dataset.

### Architecture Decision

Business logic depends on an interface (`Provider`), not a concrete API implementation.


Sprint 7 Journal

## Sprint 7 — Silver Layer Engineering

**Objective**

Transform Bronze stock market data into clean analytical data.

### What I Built

* Environment configuration using Typesafe Config.
* Structured logging framework.
* Data quality validator.
* Stock cleaning and enrichment logic.
* Partitioned Silver Delta dataset.

### Engineering Learnings

* Bronze stores raw truth.
* Silver enforces business quality rules.
* Partitioning improves query performance.
* Logging is essential for production Spark jobs.


What did I learn technically?

What production concept did I learn?

What interview question can I now answer confidently?
