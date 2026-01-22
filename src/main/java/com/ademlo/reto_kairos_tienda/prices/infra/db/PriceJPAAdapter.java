package com.ademlo.reto_kairos_tienda.prices.infra.db;

import com.ademlo.reto_kairos_tienda.prices.domain.PriceRepository;
import com.ademlo.reto_kairos_tienda.prices.domain.entities.Price;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;
import com.ademlo.reto_kairos_tienda.prices.infra.db.mapper.PriceDBMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PriceJPAAdapter implements PriceRepository {

    private final PriceJPARepository priceJPARepository;
    private final PriceDBMapper priceDBMapper;

    public PriceJPAAdapter(PriceJPARepository priceJPARepository, PriceDBMapper priceDBMapper) {
        this.priceDBMapper = priceDBMapper;
        this.priceJPARepository = priceJPARepository;
    }

    @Override
    public List<Price> findBy(BrandId brandId, ProductId productId, LocalDateTime applicationDate) {
        return priceDBMapper.toDomainEntity(priceJPARepository.findBy(brandId.getValue(), productId.getValue(),
                Timestamp.valueOf(applicationDate)));
    }
}
