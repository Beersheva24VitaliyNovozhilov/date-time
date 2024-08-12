package io.p4r53c.telran;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;

public class PastTemporalDateProximity implements TemporalAdjuster {

    private LocalDate[] localDates;

    public PastTemporalDateProximity(Temporal[] temporals) {
        this.localDates = new LocalDate[temporals.length];

        int index = 0;
        for (Temporal temporal : temporals) {
            this.localDates[index++] = convertToLocalDate(temporal);
        }

        Arrays.sort(this.localDates);
    }

    /**
     * Adjusts the given temporal object to the nearest past date from the sorted
     * array of local dates.
     *
     * @param temporal the temporal object to be adjusted
     * @return the adjusted temporal object, or null if no past date is found
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        LocalDate targetDate = convertToLocalDate(temporal);

        int low = 0;
        int high = localDates.length - 1;
        int resultIndex = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (localDates[mid].isBefore(targetDate)) {
                resultIndex = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        Temporal result;

        if (resultIndex != -1) {
            LocalDate nearestPastDate = localDates[resultIndex];
            result = adjustTemporalDate(temporal, nearestPastDate);
        } else {
            result = null;
        }

        return result;
    }

    /**
     * Converts a Temporal object to a LocalDate.
     *
     * @param temporal the Temporal object to be converted
     * @return the converted LocalDate
     */
    private LocalDate convertToLocalDate(Temporal temporal) {
        LocalDate result;

        try {
            result = LocalDate.from(temporal);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Cannot convert temporal to LocalDate: " + temporal.getClass().getName());
        }

        return result;
    }

    /**
     * Adjusts a Temporal object to match a given LocalDate.
     *
     * If the Temporal object is a ChronoLocalDate, it uses the Chronology of the
     * Temporal object to create a new ChronoLocalDate with the given date.
     * Otherwise, it adjusts the Temporal object by setting its year, month, and day
     * to match the given date.
     *
     * @param temporal the Temporal object to be adjusted
     * @param date     the LocalDate to which the Temporal object should be adjusted
     * @return the adjusted Temporal object
     */
    private Temporal adjustTemporalDate(Temporal temporal, LocalDate date) {
        Temporal result;

        if (temporal instanceof ChronoLocalDate) {
            Chronology chronology = ((ChronoLocalDate) temporal).getChronology();
            ChronoLocalDate chronoDate = chronology.date(date);
            result = chronoDate;
        } else {
            result = temporal
                    .with(ChronoField.YEAR, date.getYear())
                    .with(ChronoField.MONTH_OF_YEAR, date.getMonthValue())
                    .with(ChronoField.DAY_OF_MONTH, date.getDayOfMonth());
        }

        return result;
    }
}