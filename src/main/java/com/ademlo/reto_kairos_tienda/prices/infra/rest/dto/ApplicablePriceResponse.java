package com.ademlo.reto_kairos_tienda.prices.infra.rest.dto;

import java.math.BigDecimal;

/**
 * DTO de respuesta del endpoint de precio aplicable.
 * Mantiene fechas como String ISO-8601 para alinearse con el contrato OpenAPI.
 */
public record ApplicablePriceResponse(
		Long productId,
		Long brandId,
		Integer priceList,
		String startDate,
		String endDate,
		BigDecimal price,
		String currency,
		boolean found
) {
}

