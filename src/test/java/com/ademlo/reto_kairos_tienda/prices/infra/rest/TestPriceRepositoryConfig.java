package com.ademlo.reto_kairos_tienda.prices.infra.rest;

import com.ademlo.reto_kairos_tienda.fakes.InMemoryPriceRepository;
import com.ademlo.reto_kairos_tienda.prices.application.GetApplicablePriceService;
import com.ademlo.reto_kairos_tienda.prices.domain.GetApplicablePriceUseCase;
import com.ademlo.reto_kairos_tienda.prices.domain.PriceRepository;
import com.ademlo.reto_kairos_tienda.prices.domain.entities.Price;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.DateTimeRange;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.Money;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.PriceListId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * Configuración de tests: repositorio in-memory precargado con los datos del Background de docs/scenarios.md.
 */

@TestConfiguration
public class TestPriceRepositoryConfig {

    @Bean
    public PriceRepository priceRepository() {
        return new InMemoryPriceRepository(seedBackgroundPrices());
    }

    @Bean
    public GetApplicablePriceUseCase getApplicablePriceUseCase(PriceRepository priceRepository) {
        return new GetApplicablePriceService(priceRepository);
    }

    private static List<Price> seedBackgroundPrices() {
        BrandId brandId = new BrandId(1L);
        ProductId productId = new ProductId(35455L);

        List<Price> prices = new ArrayList<>();

        prices.add(new Price(
                brandId,
                productId,
                new PriceListId(1),
                new DateTimeRange(
                        LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59")
                ),
                0,
                new Money(new BigDecimal("35.50"), "EUR")
        ));

        prices.add(new Price(
                brandId,
                productId,
                new PriceListId(2),
                new DateTimeRange(
                        LocalDateTime.parse("2020-06-14T15:00:00"),
                        LocalDateTime.parse("2020-06-14T18:30:00")
                ),
                1,
                new Money(new BigDecimal("25.45"), "EUR")
        ));

        prices.add(new Price(
                brandId,
                productId,
                new PriceListId(3),
                new DateTimeRange(
                        LocalDateTime.parse("2020-06-15T00:00:00"),
                        LocalDateTime.parse("2020-06-15T11:00:00")
                ),
                1,
                new Money(new BigDecimal("30.50"), "EUR")
        ));

        prices.add(new Price(
                brandId,
                productId,
                new PriceListId(4),
                new DateTimeRange(
                        LocalDateTime.parse("2020-06-15T16:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59")
                ),
                1,
                new Money(new BigDecimal("38.95"), "EUR")
        ));

        return prices;
    }


}

