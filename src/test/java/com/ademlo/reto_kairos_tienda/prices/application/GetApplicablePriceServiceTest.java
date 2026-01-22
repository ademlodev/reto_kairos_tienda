package com.ademlo.reto_kairos_tienda.prices.application;

import com.ademlo.reto_kairos_tienda.fakes.InMemoryPriceRepository;
import com.ademlo.reto_kairos_tienda.prices.domain.entities.ApplicablePrice;
import com.ademlo.reto_kairos_tienda.prices.domain.entities.Price;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.DateTimeRange;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.Money;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.PriceListId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetApplicablePriceServiceTest {

    private InMemoryPriceRepository priceRepository;
    private GetApplicablePriceService service;

    @BeforeEach
    void setUp() {
        priceRepository = new InMemoryPriceRepository(new ArrayList<>());
        service = new GetApplicablePriceService(priceRepository);
    }

    @Test
    void returnsHighestPriorityPriceWhenMultipleMatchSameDate() {
        BrandId brandId = new BrandId(1L);
        ProductId productId = new ProductId(35455L);

        // Base price list 1
        priceRepository.addPrice(new Price(
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

        // Overlapping higher priority price list 2
        priceRepository.addPrice(new Price(
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

        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T16:00:00");

        Optional<ApplicablePrice> result = service.getApplicablePrice(brandId, productId, applicationDate);

        assertTrue(result.isPresent());
        ApplicablePrice applicable = result.get();
        assertEquals(2, applicable.getPriceListId().getValue());
        assertEquals(LocalDateTime.parse("2020-06-14T15:00:00"), applicable.getStartDate());
        assertEquals(LocalDateTime.parse("2020-06-14T18:30:00"), applicable.getEndDate());
        assertEquals(new BigDecimal("25.45"), applicable.getMoney().getAmount());
        assertEquals("EUR", applicable.getMoney().getCurrency());
    }

    @Test
    void returnsEmptyWhenNoPriceMatchesDate() {
        BrandId brandId = new BrandId(1L);
        ProductId productId = new ProductId(35455L);

        priceRepository.addPrice(new Price(
                brandId,
                productId,
                new PriceListId(1),
                new DateTimeRange(
                        LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-06-14T23:59:59")
                ),
                0,
                new Money(new BigDecimal("35.50"), "EUR")
        ));

        // Application date outside any range
        LocalDateTime applicationDate = LocalDateTime.parse("2020-01-01T10:00:00");

        Optional<ApplicablePrice> result = service.getApplicablePrice(brandId, productId, applicationDate);

        assertTrue(result.isEmpty());
    }

    @Test
    void respectsInclusiveBoundsOfDateTimeRange() {
        BrandId brandId = new BrandId(1L);
        ProductId productId = new ProductId(35455L);

        priceRepository.addPrice(new Price(
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

        // Lower bound
        Optional<ApplicablePrice> atStart = service.getApplicablePrice(
                brandId,
                productId,
                LocalDateTime.parse("2020-06-15T00:00:00")
        );
        assertTrue(atStart.isPresent());

        // Upper bound
        Optional<ApplicablePrice> atEnd = service.getApplicablePrice(
                brandId,
                productId,
                LocalDateTime.parse("2020-06-15T11:00:00")
        );
        assertTrue(atEnd.isPresent());
    }
}

