package com.ademlo.reto_kairos_tienda.prices.domain.valueobjects;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Valor monetario con importe y moneda.
 */
public final class Money {

	private final BigDecimal amount;
	private final String currency;

	public Money(BigDecimal amount, String currency) {
		this.amount = Objects.requireNonNull(amount, "amount must not be null");
		this.currency = Objects.requireNonNull(currency, "currency must not be null");
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
		 return true;
		}
		if (o == null || getClass() != o.getClass()) {
		 return false;
		}
		Money money = (Money) o;
		return amount.compareTo(money.amount) == 0 && currency.equals(money.currency);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount.stripTrailingZeros(), currency);
	}

	@Override
	public String toString() {
		return amount + " " + currency;
	}
}

