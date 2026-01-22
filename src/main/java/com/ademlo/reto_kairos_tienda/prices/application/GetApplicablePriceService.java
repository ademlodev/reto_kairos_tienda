package com.ademlo.reto_kairos_tienda.prices.application;

import com.ademlo.reto_kairos_tienda.prices.domain.GetApplicablePriceUseCase;
import com.ademlo.reto_kairos_tienda.prices.domain.PriceRepository;
import com.ademlo.reto_kairos_tienda.prices.domain.entities.ApplicablePrice;
import com.ademlo.reto_kairos_tienda.prices.domain.entities.Price;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del caso de uso de obtención de precio aplicable.
 * <p>
 * Esta clase forma parte de la capa de aplicación y delega en el
 * repositorio de dominio para obtener los datos necesarios.
 */
@Service
public class GetApplicablePriceService implements GetApplicablePriceUseCase {

    private final PriceRepository priceRepository;

    public GetApplicablePriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Optional<ApplicablePrice> getApplicablePrice(BrandId brandId,
                                                        ProductId productId,
                                                        LocalDateTime applicationDate) {

        List<Price> prices = priceRepository.findBy(brandId, productId, applicationDate);

        return prices.stream()
                .findFirst()
                .map(ApplicablePrice::fromPrice);
    }
}

