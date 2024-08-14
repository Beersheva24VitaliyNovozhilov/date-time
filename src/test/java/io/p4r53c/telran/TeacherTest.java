package io.p4r53c.telran;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.chrono.MinguoDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

class TeacherTest {
    Temporal[] temporals = {
            MinguoDate.now().minus(1, ChronoUnit.DAYS),
            ThaiBuddhistDate.now().plus(3, ChronoUnit.DAYS),
            LocalDate.now().minus(2, ChronoUnit.YEARS),
            LocalDateTime.now().plus(1, ChronoUnit.MONTHS)
    };
    PastTemporalDateProximity adjuster = new PastTemporalDateProximity(temporals);

    @Test
    void localDateTimeAsMinguoDate() {
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime expected = LocalDateTime.now().minusDays(1);
        assertEquals(0, ChronoUnit.SECONDS.between(ldt.with(adjuster), expected));
    }

    @Test
    void minguoDateAsZonedDateTime() {
        MinguoDate md = MinguoDate.now().plus(2, ChronoUnit.MONTHS);
        MinguoDate expected = MinguoDate.now().plus(1, ChronoUnit.MONTHS);
        assertEquals(expected, md.with(adjuster));
    }

    @Test
    void zonedDateTimeAsThaiBuddhist() {
        Temporal zdt = ZonedDateTime.now().plusDays(4);
        ZonedDateTime expected = ZonedDateTime.now().plus(3, ChronoUnit.DAYS);
        assertEquals(0, ChronoUnit.SECONDS.between(zdt.with(adjuster), expected));
    }

    @Test
    void localDateNotFound() {
        LocalDate ld = LocalDate.now().minusYears(3);
        assertNull(ld.with(adjuster));
    }

}
