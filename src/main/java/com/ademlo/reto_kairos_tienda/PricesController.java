package com.ademlo.reto_kairos_tienda;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Esqueleto de controlador para la consulta del precio aplicable.
 * <p>
 * Implementación pendiente. La firma del método sigue el contrato definido
 * en {@code src/main/resources/openapi/pricing-api.yaml}.
 */
@RestController
@RequestMapping("/api/v1/prices")
public class PricesController {

	@GetMapping("/applicable")
	public ResponseEntity<Void> getApplicablePrice(
			@RequestParam("applicationDate") String applicationDate,
			@RequestParam("productId") Long productId,
			@RequestParam("brandId") Long brandId) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}

