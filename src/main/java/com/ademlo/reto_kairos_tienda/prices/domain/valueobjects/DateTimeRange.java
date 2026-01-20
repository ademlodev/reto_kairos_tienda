package com.ademlo.reto_kairos_tienda.prices.domain.valueobjects;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Rango de fechas/horas inclusivo [start, end].
 */
public final class DateTimeRange {

	private final LocalDateTime start;
	private final LocalDateTime end;

	public DateTimeRange(LocalDateTime start, LocalDateTime end) {
		this.start = Objects.requireNonNull(start, "start must not be null");
		this.end = Objects.requireNonNull(end, "end must not be null");
		if (this.end.isBefore(this.start)) {
			throw new IllegalArgumentException("end must be greater than or equal to start");
		}
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public boolean contains(LocalDateTime dateTime) {
		Objects.requireNonNull(dateTime, "dateTime must not be null");
		return !dateTime.isBefore(start) && !dateTime.isAfter(end);
	}
}

