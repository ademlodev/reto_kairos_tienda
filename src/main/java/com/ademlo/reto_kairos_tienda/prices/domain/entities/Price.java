package com.ademlo.reto_kairos_tienda.prices.domain.entities;

import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.DateTimeRange;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.Money;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.PriceListId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;

import java.util.Objects;

/**
 * Tarifa de precio tal como se define en el enunciado (tabla PRICES).
 */
public final class Price {

	private final BrandId brandId;
	private final ProductId productId;
	private final PriceListId priceListId;
	private final DateTimeRange dateTimeRange;
	private final int priority;
	private final Money money;

	public Price(BrandId brandId,
	             ProductId productId,
	             PriceListId priceListId,
	             DateTimeRange dateTimeRange,
	             int priority,
	             Money money) {
		this.brandId = Objects.requireNonNull(brandId, "brandId must not be null");
		this.productId = Objects.requireNonNull(productId, "productId must not be null");
		this.priceListId = Objects.requireNonNull(priceListId, "priceListId must not be null");
		this.dateTimeRange = Objects.requireNonNull(dateTimeRange, "dateTimeRange must not be null");
		this.priority = priority;
		this.money = Objects.requireNonNull(money, "money must not be null");
	}

	public BrandId getBrandId() {
		return brandId;
	}

	public ProductId getProductId() {
		return productId;
	}

	public PriceListId getPriceListId() {
		return priceListId;
	}

	public DateTimeRange getDateTimeRange() {
		return dateTimeRange;
	}

	public int getPriority() {
		return priority;
	}

	public Money getMoney() {
		return money;
	}

	/**
	 * Indica si este precio aplica para la fecha/hora dada.
	 */
	public boolean appliesTo(java.time.LocalDateTime applicationDate) {
		return dateTimeRange.contains(applicationDate);
	}
}

