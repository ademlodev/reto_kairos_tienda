package com.ademlo.reto_kairos_tienda.fakes;

import com.ademlo.reto_kairos_tienda.prices.domain.PriceRepository;
import com.ademlo.reto_kairos_tienda.prices.domain.entities.Price;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Fake in-memory implementation of {@link PriceRepository} for integration tests.
 */
public class InMemoryPriceRepository implements PriceRepository {

    private final List<Price> prices;

    public InMemoryPriceRepository(List<Price> prices) {
        this.prices = new ArrayList<>(prices);
    }

    public void addPrice(Price price) {
        prices.add(price);
    }

    @Override
    public List<Price> findBy(BrandId brandId, ProductId productId, LocalDateTime applicationDate) {
        return prices.stream()
                .filter(price -> price.getBrandId().equals(brandId) &&
                        price.getProductId().equals(productId) && price.getDateTimeRange().contains(applicationDate))
                .sorted(Comparator.comparing(Price::getPriority).reversed())
                .collect(Collectors.toList());
    }
}
