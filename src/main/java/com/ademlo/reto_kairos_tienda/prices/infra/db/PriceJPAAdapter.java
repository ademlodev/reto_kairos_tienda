package com.ademlo.reto_kairos_tienda.prices.infra.db;

import com.ademlo.reto_kairos_tienda.prices.domain.PriceRepository;
import com.ademlo.reto_kairos_tienda.prices.domain.entities.Price;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;
import com.ademlo.reto_kairos_tienda.prices.infra.db.mapper.PriceDBMapper;
import org.springframework.stereotype.Component;

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
    public List<Price> findByBrandIdAndProductId(BrandId brandId, ProductId productId) {
        return priceDBMapper.toDomainEntity(priceJPARepository.findByBrandIdAndProductId(brandId.getValue(), productId.getValue()));
    }
}
