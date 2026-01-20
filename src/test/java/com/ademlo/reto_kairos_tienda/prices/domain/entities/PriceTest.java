package com.ademlo.reto_kairos_tienda.prices.domain.entities;

import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.DateTimeRange;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.Money;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.PriceListId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriceTest {

	@Test
	void appliesTo_delegatesToDateTimeRangeInclusively() {
		Price price = new Price(
				new BrandId(1L),
				new ProductId(35455L),
				new PriceListId(1),
				new DateTimeRange(
						LocalDateTime.parse("2020-06-14T00:00:00"),
						LocalDateTime.parse("2020-06-14T23:59:59")
				),
				0,
				new Money(new BigDecimal("35.50"), "EUR")
		);

		assertTrue(price.appliesTo(LocalDateTime.parse("2020-06-14T00:00:00")));
		assertTrue(price.appliesTo(LocalDateTime.parse("2020-06-14T12:00:00")));
		assertTrue(price.appliesTo(LocalDateTime.parse("2020-06-14T23:59:59")));

		assertFalse(price.appliesTo(LocalDateTime.parse("2020-06-13T23:59:59")));
		assertFalse(price.appliesTo(LocalDateTime.parse("2020-06-15T00:00:00")));
	}
}

