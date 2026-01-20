package com.ademlo.reto_kairos_tienda.prices.domain;

import com.ademlo.reto_kairos_tienda.prices.domain.entities.Price;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;

import java.util.List;

/**
 * Puerto de acceso a los datos de precios.
 * <p>
 * La implementación concreta (in-memory, JPA, etc.) se proporcionará
 * en la capa de infraestructura.
 */
public interface PriceRepository {

	List<Price> findByBrandIdAndProductId(BrandId brandId, ProductId productId);
}

