package com.ademlo.reto_kairos_tienda.prices.domain.entities;

import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.Money;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.PriceListId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa el resultado del caso de uso de búsqueda de precio aplicable,
 * listo para ser mapeado a la capa API.
 */
public final class ApplicablePrice {

	private final BrandId brandId;
	private final ProductId productId;
	private final PriceListId priceListId;
	private final LocalDateTime startDate;
	private final LocalDateTime endDate;
	private final Money money;

	public ApplicablePrice(BrandId brandId,
	                       ProductId productId,
	                       PriceListId priceListId,
	                       LocalDateTime startDate,
	                       LocalDateTime endDate,
	                       Money money) {
		this.brandId = Objects.requireNonNull(brandId, "brandId must not be null");
		this.productId = Objects.requireNonNull(productId, "productId must not be null");
		this.priceListId = Objects.requireNonNull(priceListId, "priceListId must not be null");
		this.startDate = Objects.requireNonNull(startDate, "startDate must not be null");
		this.endDate = Objects.requireNonNull(endDate, "endDate must not be null");
		this.money = Objects.requireNonNull(money, "money must not be null");
	}

	public static ApplicablePrice fromPrice(Price price) {
		return new ApplicablePrice(
				price.getBrandId(),
				price.getProductId(),
				price.getPriceListId(),
				price.getDateTimeRange().getStart(),
				price.getDateTimeRange().getEnd(),
				price.getMoney()
		);
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

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public Money getMoney() {
		return money;
	}
}

