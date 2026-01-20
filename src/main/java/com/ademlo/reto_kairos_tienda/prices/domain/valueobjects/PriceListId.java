package com.ademlo.reto_kairos_tienda.prices.domain.valueobjects;

import java.util.Objects;

/**
 * Identificador de la tarifa (price list).
 */
public final class PriceListId {

	private final Integer value;

	public PriceListId(Integer value) {
		this.value = Objects.requireNonNull(value, "priceListId must not be null");
	}

	public Integer getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
		 return true;
		}
		if (o == null || getClass() != o.getClass()) {
		 return false;
		}
		PriceListId that = (PriceListId) o;
		return value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return "PriceListId{" + "value=" + value + '}';
	}
}

