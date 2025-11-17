# 🚀 Market Risk Demo --- Real‑Time Risk Analytics Platform

A fully functional **market‑risk analytics platform** built with
**Spring Boot 3**,\
**Kafka Streams**, **H2 Database**, **REST APIs**, and a clean, modular
architecture.

This project demonstrates **real‑time financial data pipelines**, **risk
calculation**,\
**regulatory reporting**, and **alert generation**.

------------------------------------------------------------------------

# 📁 Project Architecture Overview

    market-risk-demo/
      ├── src/main/java/com/example/marketrisk/
      │   ├── api/                     # REST controllers
      │   ├── util/                    # Utility helpers (math/time/pricing)
      │   ├── kafka/
      │   │   ├── stream/              # Kafka Streams pipelines
      │   │   ├── processor/           # Stream processing logic
      │   │   ├── JsonSerdes/          # Custom SerDes
      │   │   ├── KafkaProducerService # Generic Kafka producer
      │   │   └── KafkaConsumerService # Consumers for *.save topics
      │   ├── model/                   # DTOs, Entities, Domain models
      │   ├── config/                  # Swagger/OpenAPI, Kafka topics, App config
      │   ├── service/                 # Business services
      │   ├── repository/              # Spring Data JPA repositories
      │   └── MarketRiskDemoApplication.java
      ├── resources/
      ├── Dockerfile
      ├── docker-compose.yml
      ├── pom.xml
      └── README.md

------------------------------------------------------------------------

# 🔄 Real‑Time Kafka Event Pipelines

## **1️⃣ Market Data Enrichment Pipeline**

    marketdata.realtime
          │
          ▼
    MarketDataEnrichStream
          │ (EnrichProcessor)
          ▼
    marketdata.enriched

### Save Listener

Kafka → `marketdata.realtime.save` → DB

------------------------------------------------------------------------

## **2️⃣ Risk Metric Calculation Pipeline**

    marketdata.enriched
          │
          ▼
    RiskMetricStream
          │ (RiskMetricCalculationProcessor)
          ▼
    risk.metrics

### Save Listener

Kafka → `risk.metrics.save` → DB

------------------------------------------------------------------------

## **3️⃣ Automated Risk Alert Pipeline**

    risk.metrics
          │
          ▼
    RiskAlertStream
          │ (RiskAlertGenerationProcessor)
          ▼
    risk.alerts

### Save Listener

Kafka → `risk.alerts` → alert engine / notifications

------------------------------------------------------------------------

# 📦 REST API Overview

## **Market Data API**

`GET /api/market-data`\
`POST /api/market-data/publish`

## **Risk Metrics API**

`GET /api/risk/metrics/{symbol}`\
`GET /api/risk/alerts`

## **Reporting API**

`GET /api/reporting/regulatory`\
`GET /api/reporting/export/tableau`

## **AI API**

`POST /api/ai/analyze`\
Runs LLM‑driven analysis on market or risk data.

------------------------------------------------------------------------

# 🧠 Kafka Streams Design

Each stage uses:

-   **Dedicated stream class**
-   **Dedicated processor**
-   **Typed KStream return values**
-   **Custom JsonSerdes**

This design allows full modularity and easy extension when adding more
pipelines like:

    marketdata.realtime → regulatory → reporting

------------------------------------------------------------------------

# 🗄 Persistence Layer

The application stores:

-   Raw market data\
-   Enriched data\
-   Risk metrics\
-   Alerts\
-   Regulatory reports

Using **Spring Data JPA + H2** (file‑based by default).

------------------------------------------------------------------------

# 🧪 Example Diagram --- Market Data → Alert

``` mermaid
flowchart LR
    A[marketdata.realtime] --> B(MarketDataEnrichStream)
    B --> C[marketdata.enriched]
    C --> D(RiskMetricStream)
    D --> E[risk.metrics]
    E --> F(RiskAlertStream)
    F --> G[risk.alerts]
    G --> H[AlertConsumer → DB/Notification]
```

------------------------------------------------------------------------

# 🚀 Running the Application

## 1️⃣ Launch Kafka via Docker Compose

    docker-compose up -d

## 2️⃣ Start Spring Boot

    mvn spring-boot:run

------------------------------------------------------------------------

# 📘 Swagger UI

Once the app starts:

**http://localhost:8080/swagger-ui/index.html**

------------------------------------------------------------------------

# 🧰 Tools & Technologies

  Component          Tech
  ------------------ ------------------------------
  Runtime            Spring Boot 3
  Messaging          Apache Kafka / Kafka Streams
  Database           H2
  Serialization      Jackson JSON + custom SerDes
  Docs               OpenAPI 3 / Swagger
  Containerization   Docker / Compose
  AI Integration     HuggingFace WebClient

------------------------------------------------------------------------

# 🧩 Extensibility

The system supports **plug‑in pipelines**.\
To add a new pipeline:

1.  Add topic definitions in `KafkaTopicsConfig`
2.  Create a stream class: `RegulatoryStream.java`
3.  Create a processor: `RegulatoryProcessor.java`
4.  Return a typed KStream
5.  Add a `.save` listener for persistence

------------------------------------------------------------------------

# 📜 License

MIT License (see LICENSE file)

------------------------------------------------------------------------

# 🙌 Contributions

PRs are welcome!\
You can contribute new processors, pipelines, dashboards, or AI analysis
modules.
