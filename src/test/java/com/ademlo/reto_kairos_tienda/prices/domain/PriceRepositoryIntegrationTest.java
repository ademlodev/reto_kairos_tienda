package com.ademlo.reto_kairos_tienda.prices.domain;

import com.ademlo.reto_kairos_tienda.prices.domain.entities.Price;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PriceRepositoryIntegrationTest {

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void scenario1_requestAt2020_06_14_10_00_returnsPriceList1() {
        BrandId brandId = new BrandId(1L);
        ProductId productId = new ProductId(35455L);
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        List<Price> prices = priceRepository.findBy(brandId, productId, applicationDate);
        assertThat(prices).isNotEmpty();
        Price applicable = prices.stream()
                .filter(p -> p.getDateTimeRange().contains(applicationDate))
                .max((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority()))
                .orElse(null);
        assertThat(applicable).isNotNull();
        assertThat(applicable.getPriceListId().getValue()).isEqualTo(1);
        assertThat(applicable.getMoney().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(35.50));
        assertThat(applicable.getMoney().getCurrency()).isEqualTo("EUR");
    }

    @Test
    void scenario2_requestAt2020_06_14_16_00_returnsPriceList2_highestPriority() {
        BrandId brandId = new BrandId(1L);
        ProductId productId = new ProductId(35455L);
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T16:00:00");
        List<Price> prices = priceRepository.findBy(brandId, productId, applicationDate);
        assertThat(prices).isNotEmpty();
        Price applicable = prices.stream()
                .filter(p -> p.getDateTimeRange().contains(applicationDate))
                .max((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority()))
                .orElse(null);
        assertThat(applicable).isNotNull();
        assertThat(applicable.getPriceListId().getValue()).isEqualTo(2);
        assertThat(applicable.getDateTimeRange().getStart()).isEqualTo(LocalDateTime.parse("2020-06-14T15:00:00"));
        assertThat(applicable.getDateTimeRange().getEnd()).isEqualTo(LocalDateTime.parse("2020-06-14T18:30:00"));
        assertThat(applicable.getMoney().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(25.45));
        assertThat(applicable.getMoney().getCurrency()).isEqualTo("EUR");
    }

    @Test
    void scenario3_requestAt2020_06_14_21_00_returnsPriceList1() {
        BrandId brandId = new BrandId(1L);
        ProductId productId = new ProductId(35455L);
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T21:00:00");
        List<Price> prices = priceRepository.findBy(brandId, productId, applicationDate);
        assertThat(prices).isNotEmpty();
        Price applicable = prices.stream()
                .filter(p -> p.getDateTimeRange().contains(applicationDate))
                .max((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority()))
                .orElse(null);
        assertThat(applicable).isNotNull();
        assertThat(applicable.getPriceListId().getValue()).isEqualTo(1);
        assertThat(applicable.getDateTimeRange().getStart()).isEqualTo(LocalDateTime.parse("2020-06-14T00:00:00"));
        assertThat(applicable.getDateTimeRange().getEnd()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
        assertThat(applicable.getMoney().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(35.5));
        assertThat(applicable.getMoney().getCurrency()).isEqualTo("EUR");
    }

    @Test
    void scenario4_requestAt2020_06_15_10_00_returnsPriceList3() {
        BrandId brandId = new BrandId(1L);
        ProductId productId = new ProductId(35455L);
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-15T10:00:00");
        List<Price> prices = priceRepository.findBy(brandId, productId, applicationDate);
        assertThat(prices).isNotEmpty();
        Price applicable = prices.stream()
                .filter(p -> p.getDateTimeRange().contains(applicationDate))
                .max((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority()))
                .orElse(null);
        assertThat(applicable).isNotNull();
        assertThat(applicable.getPriceListId().getValue()).isEqualTo(3);
        assertThat(applicable.getDateTimeRange().getStart()).isEqualTo(LocalDateTime.parse("2020-06-15T00:00:00"));
        assertThat(applicable.getDateTimeRange().getEnd()).isEqualTo(LocalDateTime.parse("2020-06-15T11:00:00"));
        assertThat(applicable.getMoney().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(30.5));
        assertThat(applicable.getMoney().getCurrency()).isEqualTo("EUR");
    }

    @Test
    void scenario5_requestAt2020_06_16_21_00_returnsPriceList4() {
        BrandId brandId = new BrandId(1L);
        ProductId productId = new ProductId(35455L);
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-16T21:00:00");
        List<Price> prices = priceRepository.findBy(brandId, productId, applicationDate);
        assertThat(prices).isNotEmpty();
        Price applicable = prices.stream()
                .filter(p -> p.getDateTimeRange().contains(applicationDate))
                .max((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority()))
                .orElse(null);
        assertThat(applicable).isNotNull();
        assertThat(applicable.getPriceListId().getValue()).isEqualTo(4);
        assertThat(applicable.getDateTimeRange().getStart()).isEqualTo(LocalDateTime.parse("2020-06-15T16:00:00"));
        assertThat(applicable.getDateTimeRange().getEnd()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
        assertThat(applicable.getMoney().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(38.95));
        assertThat(applicable.getMoney().getCurrency()).isEqualTo("EUR");
    }

    @Test
    void boundary_applicationDateEqualsStartDate_isApplicable() {
        BrandId brandId = new BrandId(1L);
        ProductId productId = new ProductId(35455L);
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-15T00:00:00");
        List<Price> prices = priceRepository.findBy(brandId, productId, applicationDate);
        assertThat(prices).isNotEmpty();
        Price applicable = prices.stream()
                .filter(p -> p.getDateTimeRange().contains(applicationDate))
                .max((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority()))
                .orElse(null);
        assertThat(applicable).isNotNull();
        assertThat(applicable.getPriceListId().getValue()).isEqualTo(3);
        assertThat(applicable.getDateTimeRange().getStart()).isEqualTo(LocalDateTime.parse("2020-06-15T00:00:00"));
        assertThat(applicable.getDateTimeRange().getEnd()).isEqualTo(LocalDateTime.parse("2020-06-15T11:00:00"));
        assertThat(applicable.getMoney().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(30.5));
        assertThat(applicable.getMoney().getCurrency()).isEqualTo("EUR");
    }

    @Test
    void boundary_applicationDateEqualsEndDate_isApplicable() {
        BrandId brandId = new BrandId(1L);
        ProductId productId = new ProductId(35455L);
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T18:30:00");
        List<Price> prices = priceRepository.findBy(brandId, productId, applicationDate);
        assertThat(prices).isNotEmpty();
        Price applicable = prices.stream()
                .filter(p -> p.getDateTimeRange().contains(applicationDate))
                .max((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority()))
                .orElse(null);
        assertThat(applicable).isNotNull();
        assertThat(applicable.getPriceListId().getValue()).isEqualTo(2);
        assertThat(applicable.getDateTimeRange().getStart()).isEqualTo(LocalDateTime.parse("2020-06-14T15:00:00"));
        assertThat(applicable.getDateTimeRange().getEnd()).isEqualTo(LocalDateTime.parse("2020-06-14T18:30:00"));
        assertThat(applicable.getMoney().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(25.45));
        assertThat(applicable.getMoney().getCurrency()).isEqualTo("EUR");
    }

    @Test
    void noPriceFound_returns_EmptyList() {
        BrandId brandId = new BrandId(1L);
        ProductId productId = new ProductId(35455L);
        LocalDateTime applicationDate = LocalDateTime.parse("2020-01-01T10:00:00");
        List<Price> prices = priceRepository.findBy(brandId, productId, applicationDate);
        assertThat(prices).isEmpty();
    }
}
