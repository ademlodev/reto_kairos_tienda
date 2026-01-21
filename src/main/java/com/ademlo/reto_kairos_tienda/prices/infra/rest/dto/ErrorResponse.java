package com.ademlo.reto_kairos_tienda.prices.infra.rest.dto;

/**
 * DTO simple de error (alineado con el contrato OpenAPI).
 */
public record ErrorResponse(String message, String error) {
}

