package com.ademlo.reto_kairos_tienda.core.exceptions;

/**
 * DTO simple de error (alineado con el contrato OpenAPI).
 */
public record ErrorResponse(String message, String error) {
}

