package io.p4r53c.telran;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.temporal.Temporal;

class PastTemporalDateProximityTest {

    private Temporal[] unsortedTemporals = {
            LocalDate.of(2012, 3, 6),
            LocalDate.of(2022, 1, 1),
            LocalDate.of(2022, 3, 8),
            LocalDate.of(2022, 5, 2),
            LocalDate.of(2022, 5, 2),
            LocalDate.of(2022, 4, 3),
            LocalDate.of(2023, 1, 6)
    };

    private Temporal[] temporalsEmpty = new Temporal[0];

    @Test
    void testEmptyTemporalsArray() {
        PastTemporalDateProximity proximity = new PastTemporalDateProximity(temporalsEmpty);
        Temporal temporal = LocalDate.of(2001, 1, 1);
        assertNull(proximity.adjustInto(temporal));
    }

    @Test
    void testMultipleTemporalsArrayNoPast() {
        PastTemporalDateProximity proximity = new PastTemporalDateProximity(unsortedTemporals);
        Temporal temporal = LocalDate.of(2001, 1, 1);
        assertNull(proximity.adjustInto(temporal));
    }

    @Test
    void testMultipleElementsInTemporalsArrayFirst() {
        PastTemporalDateProximity proximity = new PastTemporalDateProximity(unsortedTemporals);
        Temporal temporal = LocalDate.of(2022, 1, 4);
        assertEquals(unsortedTemporals[1], proximity.adjustInto(temporal));
    }

    @Test
    void testMultipleElementsInTemporalsArraySecond() {

        PastTemporalDateProximity proximity = new PastTemporalDateProximity(unsortedTemporals);
        Temporal temporal = LocalDate.of(2022, 4, 2);
        assertEquals(unsortedTemporals[2], proximity.adjustInto(temporal));
    }

    @Test
    void testMultipleElementsInTemporalsArrayThird() {
        PastTemporalDateProximity proximity = new PastTemporalDateProximity(unsortedTemporals);
        Temporal temporal = LocalDate.of(2014, 6, 7);
        assertEquals(unsortedTemporals[0], proximity.adjustInto(temporal));
    }

    @Test
    void testMultipleElementsInTemporalsArrayFirstOfDouble() {
        PastTemporalDateProximity proximity = new PastTemporalDateProximity(unsortedTemporals);
        Temporal temporal = LocalDate.of(2022, 6, 6);
        assertEquals(unsortedTemporals[4], proximity.adjustInto(temporal));
    }

    @Test
    void testMultipleElementsInTemporalsArrayNow() {
        PastTemporalDateProximity proximity = new PastTemporalDateProximity(unsortedTemporals);
        Temporal temporal = LocalDate.now();
        assertEquals(unsortedTemporals[6], proximity.adjustInto(temporal));
    }

    @Test
    void testMultipleElementsInTemporalsArrayFarFuture() {
        PastTemporalDateProximity proximity = new PastTemporalDateProximity(unsortedTemporals);
        Temporal temporal = LocalDate.of(2222, 12, 31);
        assertEquals(unsortedTemporals[6], proximity.adjustInto(temporal));
    }

}