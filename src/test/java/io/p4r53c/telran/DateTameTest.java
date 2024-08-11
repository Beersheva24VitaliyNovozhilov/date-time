package io.p4r53c.telran;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

class DateTameTest {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(DateTameTest.class);
    
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    @Test
    void LocalDateTest() {
        LocalDate currentLocalDate = LocalDate.now();
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        ZonedDateTime currentZonedDateTime = ZonedDateTime.now();
        Instant currentInstant = Instant.now();

        LocalTime currentLocalTime = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.forLanguageTag("ru"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

        logger.info("Local Date: [{}]", currentLocalDate.format(dateFormatter));
        logger.info("Local DateTime: [{}]", currentLocalDateTime.format(dateTimeFormatter));
        logger.info("Zoned Date Time: [{}]", currentZonedDateTime);
        logger.info("Instant: [{}]", currentInstant);
        logger.info("Local Time: [{}]", currentLocalTime);

        assertEquals(currentLocalDate, currentLocalDateTime.toLocalDate());
    }

    @Test
    void nextFriday13Test() {
        LocalDate currentLocalDate = LocalDate.of(2024, 8, 11);
        LocalDate expectedFridayThirteenDate = LocalDate.of(2024, 9, 13);

        assertEquals(expectedFridayThirteenDate, currentLocalDate.with(new NextFriday13()));
        assertThrowsExactly(UnsupportedTemporalTypeException.class, () -> LocalTime.now().with(new NextFriday13()));
    }

}
