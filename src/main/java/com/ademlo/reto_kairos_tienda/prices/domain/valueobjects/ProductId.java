package com.ademlo.reto_kairos_tienda.prices.domain.valueobjects;

import java.util.Objects;

/**
 * Identificador de producto.
 */
public final class ProductId {

	private final Long value;

	public ProductId(Long value) {
		this.value = Objects.requireNonNull(value, "productId must not be null");
	}

	public Long getValue() {
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
		ProductId that = (ProductId) o;
		return value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return "ProductId{" + "value=" + value + '}';
	}
}

