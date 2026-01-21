package com.ademlo.reto_kairos_tienda.prices.infra.rest;

import com.ademlo.reto_kairos_tienda.prices.domain.GetApplicablePriceUseCase;
import com.ademlo.reto_kairos_tienda.prices.domain.entities.ApplicablePrice;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.BrandId;
import com.ademlo.reto_kairos_tienda.prices.domain.valueobjects.ProductId;
import com.ademlo.reto_kairos_tienda.prices.infra.rest.dto.ApplicablePriceResponse;
import com.ademlo.reto_kairos_tienda.prices.infra.rest.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Esqueleto de controlador para la consulta del precio aplicable.
 * <p>
 * Implementación pendiente. La firma del método sigue el contrato definido
 * en {@code src/main/resources/openapi/pricing-api.yaml}.
 */
@RestController
@RequestMapping("/api/v1/prices")
public class PricesController {

	private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	private final GetApplicablePriceUseCase getApplicablePriceUseCase;

	public PricesController(GetApplicablePriceUseCase getApplicablePriceUseCase) {
		this.getApplicablePriceUseCase = getApplicablePriceUseCase;
	}

	@GetMapping("/applicable")
	public ResponseEntity<?> getApplicablePrice(
			@RequestParam("applicationDate")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
			LocalDateTime applicationDate,
			@RequestParam("productId") Long productId,
			@RequestParam("brandId") Long brandId) {

		Optional<ApplicablePrice> result = getApplicablePriceUseCase.getApplicablePrice(
				new BrandId(brandId),
				new ProductId(productId),
				applicationDate
		);

		if (result.isEmpty()) {
			return ResponseEntity.status(404).body(new ErrorResponse(
					"No price found for given brand, product and applicationDate.",
					"NOT_FOUND"
			));
		}

		ApplicablePrice applicablePrice = result.get();

		return ResponseEntity.ok(new ApplicablePriceResponse(
				applicablePrice.getProductId().getValue(),
				applicablePrice.getBrandId().getValue(),
				applicablePrice.getPriceListId().getValue(),
				applicablePrice.getStartDate().format(ISO),
				applicablePrice.getEndDate().format(ISO),
				applicablePrice.getMoney().getAmount(),
				applicablePrice.getMoney().getCurrency(),
				true
		));
	}
}

