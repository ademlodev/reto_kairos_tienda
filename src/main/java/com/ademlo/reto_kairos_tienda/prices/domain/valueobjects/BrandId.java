package com.ademlo.reto_kairos_tienda.prices.domain.valueobjects;

import java.util.Objects;

/**
 * Identificador de marca/cadena.
 */
public final class BrandId {

	private final Long value;

	public BrandId(Long value) {
		this.value = Objects.requireNonNull(value, "brandId must not be null");
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
		BrandId brandId = (BrandId) o;
		return value.equals(brandId.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return "BrandId{" + "value=" + value + '}';
	}
}

