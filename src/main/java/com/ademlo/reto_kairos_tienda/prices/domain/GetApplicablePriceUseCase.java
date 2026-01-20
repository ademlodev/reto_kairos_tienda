package com.ademlo.reto_kairos_tienda.prices.domain;

import com.ademlo.reto_kairos_tienda.prices.domain.entities.ApplicablePrice;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Puerto de dominio para el caso de uso de obtención del precio aplicable.
 */
public interface GetApplicablePriceUseCase {

	/**
	 * Obtiene, si existe, el precio aplicable para la combinación dada
	 * de marca, producto y fecha/hora de aplicación.
	 */
	Optional<ApplicablePrice> getApplicablePrice(BrandId brandId,
												 ProductId productId,
												 LocalDateTime applicationDate);
}

