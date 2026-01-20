# Pricing API – Gherkin Scenarios

```gherkin
Feature: Query applicable product price for a brand at a given date and time
  In order to show the correct final price (PVP) to customers
  As a client of the pricing API
  I want to obtain the applicable tariff and price for a product and brand at a specific application date

  Background:
    Given the following prices exist in the system:
      | brandId | productId | priceList | startDate           | endDate             | priority | price | currency |
      | 1       | 35455     | 1         | 2020-06-14T00:00:00 | 2020-12-31T23:59:59 | 0        | 35.50 | EUR      |
      | 1       | 35455     | 2         | 2020-06-14T15:00:00 | 2020-06-14T18:30:00 | 1        | 25.45 | EUR      |
      | 1       | 35455     | 3         | 2020-06-15T00:00:00 | 2020-06-15T11:00:00 | 1        | 30.50 | EUR      |
      | 1       | 35455     | 4         | 2020-06-15T16:00:00 | 2020-12-31T23:59:59 | 1        | 38.95 | EUR      |

  # Required scenarios from the statement

  Scenario: Test 1 - Request at 2020-06-14 10:00 for product 35455 and brand 1
    Given the application date is "2020-06-14T10:00:00"
    And the product id is 35455
    And the brand id is 1
    When I GET "/prices/applicable" with query parameters:
      | applicationDate        | productId | brandId |
      | 2020-06-14T10:00:00    | 35455     | 1       |
    Then the response status should be 200
    And the response JSON should contain:
      | field       | value                |
      | productId   | 35455                |
      | brandId     | 1                    |
      | priceList   | 1                    |
      | startDate   | 2020-06-14T00:00:00  |
      | endDate     | 2020-12-31T23:59:59  |
      | price       | 35.50                |
      | currency    | EUR                  |
    And the field "found" should be true

  Scenario: Test 2 - Request at 2020-06-14 16:00 for product 35455 and brand 1 (overlapping tariff with higher priority)
    Given the application date is "2020-06-14T16:00:00"
    And the product id is 35455
    And the brand id is 1
    When I GET "/prices/applicable" with query parameters:
      | applicationDate        | productId | brandId |
      | 2020-06-14T16:00:00    | 35455     | 1       |
    Then the response status should be 200
    And the response JSON should contain:
      | field       | value                |
      | productId   | 35455                |
      | brandId     | 1                    |
      | priceList   | 2                    |
      | startDate   | 2020-06-14T15:00:00  |
      | endDate     | 2020-06-14T18:30:00  |
      | price       | 25.45                |
      | currency    | EUR                  |
    And the field "found" should be true

  Scenario: Test 3 - Request at 2020-06-14 21:00 for product 35455 and brand 1
    Given the application date is "2020-06-14T21:00:00"
    And the product id is 35455
    And the brand id is 1
    When I GET "/prices/applicable" with query parameters:
      | applicationDate        | productId | brandId |
      | 2020-06-14T21:00:00    | 35455     | 1       |
    Then the response status should be 200
    And the response JSON should contain:
      | field       | value                |
      | productId   | 35455                |
      | brandId     | 1                    |
      | priceList   | 1                    |
      | startDate   | 2020-06-14T00:00:00  |
      | endDate     | 2020-12-31T23:59:59  |
      | price       | 35.50                |
      | currency    | EUR                  |
    And the field "found" should be true

  Scenario: Test 4 - Request at 2020-06-15 10:00 for product 35455 and brand 1
    Given the application date is "2020-06-15T10:00:00"
    And the product id is 35455
    And the brand id is 1
    When I GET "/prices/applicable" with query parameters:
      | applicationDate        | productId | brandId |
      | 2020-06-15T10:00:00    | 35455     | 1       |
    Then the response status should be 200
    And the response JSON should contain:
      | field       | value                |
      | productId   | 35455                |
      | brandId     | 1                    |
      | priceList   | 3                    |
      | startDate   | 2020-06-15T00:00:00  |
      | endDate     | 2020-06-15T11:00:00  |
      | price       | 30.50                |
      | currency    | EUR                  |
    And the field "found" should be true

  Scenario: Test 5 - Request at 2020-06-16 21:00 for product 35455 and brand 1
    Given the application date is "2020-06-16T21:00:00"
    And the product id is 35455
    And the brand id is 1
    When I GET "/prices/applicable" with query parameters:
      | applicationDate        | productId | brandId |
      | 2020-06-16T21:00:00    | 35455     | 1       |
    Then the response status should be 200
    And the response JSON should contain:
      | field       | value                |
      | productId   | 35455                |
      | brandId     | 1                    |
      | priceList   | 4                    |
      | startDate   | 2020-06-15T16:00:00  |
      | endDate     | 2020-12-31T23:59:59  |
      | price       | 38.95                |
      | currency    | EUR                  |
    And the field "found" should be true

  # Priority resolution and boundary conditions

  Scenario: Overlapping tariffs - highest priority is selected
    Given the application date is "2020-06-14T16:00:00"
    And the product id is 35455
    And the brand id is 1
    And more than one price row matches the given application date
    When I GET "/prices/applicable" with query parameters:
      | applicationDate        | productId | brandId |
      | 2020-06-14T16:00:00    | 35455     | 1       |
    Then the response status should be 200
    And the selected price should be the one with the greatest "priority" value among the matches

  Scenario: Application date equals startDate - price is applicable (inclusive lower bound)
    Given the application date is "2020-06-15T00:00:00"
    And the product id is 35455
    And the brand id is 1
    When I GET "/prices/applicable" with query parameters:
      | applicationDate        | productId | brandId |
      | 2020-06-15T00:00:00    | 35455     | 1       |
    Then the response status should be 200
    And the response JSON should contain:
      | field       | value                |
      | priceList   | 3                    |
      | startDate   | 2020-06-15T00:00:00  |
    And the field "found" should be true

  Scenario: Application date equals endDate - price is applicable (inclusive upper bound)
    Given the application date is "2020-06-14T18:30:00"
    And the product id is 35455
    And the brand id is 1
    When I GET "/prices/applicable" with query parameters:
      | applicationDate        | productId | brandId |
      | 2020-06-14T18:30:00    | 35455     | 1       |
    Then the response status should be 200
    And the response JSON should contain:
      | field       | value                |
      | priceList   | 2                    |
      | endDate     | 2020-06-14T18:30:00  |
    And the field "found" should be true

  # Validation and error handling

  Scenario: Invalid date format returns 400 Bad Request
    Given the application date is "14-06-2020 10:00"  # invalid format
    And the product id is 35455
    And the brand id is 1
    When I GET "/prices/applicable" with query parameters:
      | applicationDate     | productId | brandId |
      | 14-06-2020 10:00    | 35455     | 1       |
    Then the response status should be 400

  Scenario: Missing required parameters returns 400 Bad Request
    Given only the application date is provided
    When I GET "/prices/applicable" with query parameters:
      | applicationDate        |
      | 2020-06-14T10:00:00    |
    Then the response status should be 400

  # No price found behavior

  Scenario: No price found for given brand/product/date returns 200 with found=false
    Given the application date is "2020-01-01T10:00:00"
    And the product id is 35455
    And the brand id is 1
    When I GET "/prices/applicable" with query parameters:
      | applicationDate        | productId | brandId |
      | 2020-01-01T10:00:00    | 35455     | 1       |
    Then the response status should be 200
    And the field "found" should be false
    And the response JSON should not contain a "priceList" field
```

