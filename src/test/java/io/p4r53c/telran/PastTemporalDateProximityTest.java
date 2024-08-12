package io.p4r53c.telran;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.MinguoDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.temporal.Temporal;

class PastTemporalDateProximityTest {

    @Test
    void testAdjustIntoWithLocalDate() {
        Temporal[] temporals = {
                LocalDate.of(1900, 3, 6),
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 3, 8),
                LocalDate.of(2022, 5, 2),
                LocalDate.of(2022, 5, 2), // Duplicated. Tested.
                LocalDate.of(2022, 4, 3),
                LocalDate.of(2023, 1, 6),
                LocalDate.of(2123, 1, 6)
        };

        PastTemporalDateProximity adjuster = new PastTemporalDateProximity(temporals);

        Temporal testTemporal = LocalDate.of(2022, 6, 1);
        Temporal expectedTemporal = LocalDate.of(2022, 5, 2);
        Temporal result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = LocalDate.of(1955, 6, 1);
        expectedTemporal = LocalDate.of(1900, 3, 6);
        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = LocalDate.of(1899, 12, 31);
        result = adjuster.adjustInto(testTemporal);
        assertEquals(null, result);

        testTemporal = LocalDate.now();
        expectedTemporal = LocalDate.of(2023, 1, 6);
        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = LocalDate.now();
        expectedTemporal = LocalDate.of(2023, 1, 6);
        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);
    }

    // The following test methods do not contain duplicate temporals, as tested above.
    @Test
    void testAdjustIntoWithLocalDateTime() {
        Temporal[] temporals = {
                LocalDateTime.of(1900, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 1, 10, 0),
                LocalDateTime.of(2022, 3, 1, 10, 0),
                LocalDateTime.of(2022, 5, 1, 10, 0),
                LocalDateTime.of(2123, 1, 1, 0, 0)
        };

        PastTemporalDateProximity adjuster = new PastTemporalDateProximity(temporals);

        Temporal testTemporal = LocalDateTime.of(2022, 4, 1, 10, 0);
        Temporal expectedTemporal = LocalDateTime.of(2022, 3, 1, 10, 0);
        Temporal result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = LocalDateTime.of(2022, 6, 1, 10, 0);
        expectedTemporal = LocalDateTime.of(2022, 5, 1, 10, 0);

        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = LocalDateTime.of(1899, 12, 31, 23, 59);
        expectedTemporal = null;

        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = LocalDateTime.of(2123, 1, 2, 23, 59);
        expectedTemporal = LocalDateTime.of(2123, 1, 1, 23, 59);

        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);
    }

    @Test
    void testAdjustIntoWithZonedDateTime() {
        Temporal[] temporals = {
                ZonedDateTime.of(1900, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2022, 1, 1, 10, 0, 0, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2022, 3, 1, 10, 0, 0, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2022, 5, 1, 10, 0, 0, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2123, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"))
        };

        PastTemporalDateProximity adjuster = new PastTemporalDateProximity(temporals);

        Temporal testTemporal = ZonedDateTime.of(2022, 4, 1, 10, 0, 0, 0, ZoneId.of("UTC"));
        Temporal expectedTemporal = ZonedDateTime.of(2022, 3, 1, 10, 0, 0, 0, ZoneId.of("UTC"));

        Temporal result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = ZonedDateTime.of(2022, 6, 1, 10, 0, 0, 0, ZoneId.of("UTC"));
        expectedTemporal = ZonedDateTime.of(2022, 5, 1, 10, 0, 0, 0, ZoneId.of("UTC"));

        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = ZonedDateTime.of(1899, 12, 31, 23, 59, 59, 0, ZoneId.of("UTC"));
        expectedTemporal = null;

        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = ZonedDateTime.of(2123, 1, 2, 0, 0, 0, 0, ZoneId.of("UTC"));
        expectedTemporal = ZonedDateTime.of(2123, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));

        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);
    }

    @Test
    void testAdjustIntoWithMinguoDate() {
        Temporal[] temporals = {
                MinguoDate.of(1, 1, 1),
                MinguoDate.of(111, 1, 1), // MinguoDate equivalent of 2022-01-01
                MinguoDate.of(111, 3, 1),
                MinguoDate.of(111, 5, 1),
                MinguoDate.of(2123, 1, 1)
        };

        PastTemporalDateProximity adjuster = new PastTemporalDateProximity(temporals);

        // MinguoDate equivalent of 2022-04-01
        Temporal testTemporal = MinguoDate.of(111, 4, 1);

        // Expect to get MinguoDate equivalent of 2022-03-01
        Temporal expectedTemporal = MinguoDate.of(111, 3, 1);

        Temporal result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        // MinguoDate equivalent of 2022-06-01
        testTemporal = MinguoDate.of(111, 6, 1);

        // Expect to get MinguoDate equivalent of 2022-05-01
        expectedTemporal = MinguoDate.of(111, 5, 1);
        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = MinguoDate.of(0, 12, 31);
        expectedTemporal = null;

        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = MinguoDate.of(2123, 1, 2);
        expectedTemporal = MinguoDate.of(2123, 1, 1);

        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);
    }

    @Test
    void testAdjustIntoWithThaiBuddhistDate() {
        Temporal[] temporals = {
                ThaiBuddhistDate.of(1, 1, 1),
                ThaiBuddhistDate.of(2566, 1, 1), // ThaiBuddhistDate equivalent of 2023-01-01
                ThaiBuddhistDate.of(2566, 3, 1),
                ThaiBuddhistDate.of(2566, 5, 1),
                ThaiBuddhistDate.of(5000, 1, 1)
        };

        PastTemporalDateProximity adjuster = new PastTemporalDateProximity(temporals);

        // ThaiBuddhistDate equivalent of 2023-04-01
        Temporal testTemporal = ThaiBuddhistDate.of(2566, 4, 1);

        // Expect to get ThaiBuddhistDate equivalent of 2023-03-01
        Temporal expectedTemporal = ThaiBuddhistDate.of(2566, 3, 1);

        Temporal result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        // ThaiBuddhistDate equivalent of 2023-06-01
        testTemporal = ThaiBuddhistDate.of(2566, 6, 1);

        // Expect to get ThaiBuddhistDate equivalent of 2023-05-01
        expectedTemporal = ThaiBuddhistDate.of(2566, 5, 1);
        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = ThaiBuddhistDate.of(0, 12, 31);
        expectedTemporal = null;

        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);

        testTemporal = ThaiBuddhistDate.of(5000, 1, 2);
        expectedTemporal = ThaiBuddhistDate.of(5000, 1, 1);
        
        result = adjuster.adjustInto(testTemporal);
        assertEquals(expectedTemporal, result);
    }
}