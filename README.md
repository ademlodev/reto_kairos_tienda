# Reto Kairos Tienda — Pricing API (Spring Boot)

This repository contains a Spring Boot service for **querying the applicable final price (PVP)** for a product of a
given brand at a specific application date/time.

The challenge is based on a simplified e-commerce pricing table (`PRICES`) with time ranges and a **priority rule** to
resolve overlaps.

## Problem Statement (Summary)

In an e-commerce database we have a `PRICES` table that stores the final price (PVP) and the price list (tariff) that
applies to a product of a brand between two dates.

Relevant fields (sample data in the PDF):

- **BRAND_ID**: brand/chain identifier (e.g., `1 = ZARA`)
- **START_DATE / END_DATE**: date-time range where the tariff applies
- **PRICE_LIST**: price tariff identifier
- **PRODUCT_ID**: product identifier
- **PRIORITY**: disambiguation rule when multiple tariffs overlap
    - If two tariffs match the same date range, the one with **higher `PRIORITY`** (higher numeric value) must be
      applied.
- **PRICE**: final sale price
- **CURR**: currency ISO code (e.g., `EUR`)

## Expected Service Behavior

Expose a REST endpoint that:

- **Inputs** (query parameters):
    - application date/time
    - product identifier
    - brand identifier
- **Outputs** (response payload):
    - product identifier
    - brand identifier
    - applicable tariff (`PRICE_LIST`)
    - application start/end date
    - applicable final price (`PRICE`)

### Business Rule (Selection)

For a given `(brandId, productId, applicationDateTime)`:

1. Find all price rows where `START_DATE <= applicationDateTime <= END_DATE`
2. If there are multiple matches, return the one with the **highest `PRIORITY`**

## Data Source

The service must use an **in-memory database** (e.g. **H2**) and initialize it with the **sample data from the statement
**.

## API Contract (Suggested)

The statement does not enforce a specific URL, but a typical contract for this challenge is:

- **Method**: `GET`
- **Path**: `/prices/applicable`
- **Query parameters**:
    - `applicationDate` (ISO-8601, e.g. `2020-06-14T10:00:00`)
    - `productId` (e.g. `35455`)
    - `brandId` (e.g. `1`)

Example:

```bash
curl "http://localhost:8080/api/v1/prices/applicable?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

Expected response fields:

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

## Required Tests (from the statement)

Create endpoint tests validating these requests (using the sample dataset):

1. **2020-06-14 10:00** — product `35455`, brand `1` (ZARA)
2. **2020-06-14 16:00** — product `35455`, brand `1` (ZARA)
3. **2020-06-14 21:00** — product `35455`, brand `1` (ZARA)
4. **2020-06-15 10:00** — product `35455`, brand `1` (ZARA)
5. **2020-06-16 21:00** — product `35455`, brand `1` (ZARA)

## Tech Stack (current repo)

- **Java**: 17
- **Spring Boot**: 4.0.1
- **Build tool**: Maven Wrapper (`./mvnw`)
- **Packaging**: WAR (`<packaging>war</packaging>` in `pom.xml`)

## Prerequisites

- **Java 17** installed (JDK)
- No Maven installation required (project includes Maven Wrapper)

## Run Locally

From the project root:

```bash
./mvnw spring-boot:run
```

The application will start on the default port `8080` unless configured otherwise.

## Run Tests

```bash
./mvnw test
```

## Build Artifact

```bash
./mvnw clean package
```

This produces a WAR under `target/`.

## API Contract & Documentation

- **API-first contract**: the HTTP contract for the pricing service is defined in
  `src/main/resources/openapi/pricing-api.yaml`. This file is the **source of truth**
  for the `/api/v1/prices/applicable` endpoint.
- **OpenAPI / Swagger UI**:
    - OpenAPI JSON: `http://localhost:8080/v3/api-docs`
    - Swagger UI: `http://localhost:8080/swagger-ui.html`

When implementing the controller logic (e.g. `PricesController`), keep the
request parameters, response fields and error handling aligned with
`pricing-api.yaml`.

## Evaluation Criteria (from the statement)

- Service design and implementation quality
- Code quality
- Correct results validated by the tests

