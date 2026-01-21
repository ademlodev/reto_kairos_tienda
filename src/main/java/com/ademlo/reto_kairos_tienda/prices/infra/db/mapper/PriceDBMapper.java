package com.ademlo.reto_kairos_tienda.prices.infra.db.mapper;

import com.ademlo.reto_kairos_tienda.prices.domain.entities.Price;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.DateTimeRange;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.Money;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.PriceListId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;
import com.ademlo.reto_kairos_tienda.prices.infra.db.entities.PriceDB;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PriceDBMapper {

    public List<Price> toDomainEntity(List<PriceDB> priceDBList) {
        return priceDBList.stream()
                .map(this::toDomainEntity)
                .toList();
    }

    public Price toDomainEntity(PriceDB priceDB) {
        return new Price(
                new BrandId(priceDB.getBrandId()),
                new ProductId(priceDB.getProductId()),
                new PriceListId(Math.toIntExact(priceDB.getPriceList())),
                new DateTimeRange(priceDB.getStartDate(), priceDB.getEndDate()),
                priceDB.getPriority(),
                new Money(BigDecimal.valueOf(priceDB.getPrice()), priceDB.getCurrency())
        );
    }
}
