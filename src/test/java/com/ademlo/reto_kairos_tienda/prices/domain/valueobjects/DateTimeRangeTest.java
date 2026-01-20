package com.ademlo.reto_kairos_tienda.prices.domain.valueobjects;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeRangeTest {

	@Test
	void contains_returnsTrue_forDatesInsideInclusiveRange() {
		LocalDateTime start = LocalDateTime.parse("2020-06-14T00:00:00");
		LocalDateTime end = LocalDateTime.parse("2020-06-14T23:59:59");
		DateTimeRange range = new DateTimeRange(start, end);

		assertTrue(range.contains(start));
		assertTrue(range.contains(end));
		assertTrue(range.contains(LocalDateTime.parse("2020-06-14T12:00:00")));
	}

	@Test
	void contains_returnsFalse_forDatesOutsideRange() {
		LocalDateTime start = LocalDateTime.parse("2020-06-14T00:00:00");
		LocalDateTime end = LocalDateTime.parse("2020-06-14T23:59:59");
		DateTimeRange range = new DateTimeRange(start, end);

		assertFalse(range.contains(LocalDateTime.parse("2020-06-13T23:59:59")));
		assertFalse(range.contains(LocalDateTime.parse("2020-06-15T00:00:00")));
	}

	@Test
	void constructor_throwsException_whenEndBeforeStart() {
		LocalDateTime start = LocalDateTime.parse("2020-06-14T10:00:00");
		LocalDateTime end = LocalDateTime.parse("2020-06-14T09:59:59");

		assertThrows(IllegalArgumentException.class, () -> new DateTimeRange(start, end));
	}
}

