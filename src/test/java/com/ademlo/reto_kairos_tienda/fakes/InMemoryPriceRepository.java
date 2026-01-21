package com.ademlo.reto_kairos_tienda.fakes;

import com.ademlo.reto_kairos_tienda.prices.domain.PriceRepository;
import com.ademlo.reto_kairos_tienda.prices.domain.entities.Price;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;

import java.util.ArrayList;
import java.util.List;

/**
 * Fake in-memory implementation of {@link PriceRepository} for integration tests.
 */
public class InMemoryPriceRepository implements PriceRepository {
    //private static final class InMemoryPriceRepository implements PriceRepository {

        private final List<Price> prices;

        public InMemoryPriceRepository(List<Price> prices) {
            this.prices = new ArrayList<>(prices);
        }

        @Override
        public List<Price> findByBrandIdAndProductId(BrandId brandId, ProductId productId) {
            List<Price> result = new ArrayList<>();
            for (Price price : prices) {
                if (price.getBrandId().equals(brandId) && price.getProductId().equals(productId)) {
                    result.add(price);
                }
            }
            return result;
        }
    //}
}
