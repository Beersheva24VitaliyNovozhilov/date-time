package io.p4r53c.telran;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextFriday13 implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        Temporal temp = set13Month(temporal);

        while (temp.get(ChronoField.DAY_OF_WEEK) != DayOfWeek.FRIDAY.getValue()) {
            temp = temp.plus(1, ChronoUnit.MONTHS);
        }

        return temp;
    }

    /**
     * Sets the day of the month to 13 and moves to the next month if the current
     * day is 13 or later.
     * 
     * Cheating way:
     * <code>temporal.with(ChronoField.MONTH_OF_YEAR, 1).with(ChronoField.DAY_OF_MONTH, 13);</code>
     *
     * @param temporal the temporal object to be adjusted
     * @return the adjusted temporal object
     */
    private Temporal set13Month(Temporal temporal) {

        if (temporal.get(ChronoField.DAY_OF_MONTH) >= 13) {
            temporal = temporal.plus(1, ChronoUnit.MONTHS);
        }

        temporal = temporal.with(ChronoField.DAY_OF_MONTH, 13);

        return temporal;
    }
}
