package io.p4r53c.telran;

import java.time.LocalDate;
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
     * Adjusts the given Temporal object to the closest date in the past from the
     * stored dates.
     *
     * @param temporal the Temporal object to be adjusted
     * @return the adjusted Temporal object, or null if no adjustment is possible
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
        return resultIndex != -1 ? temporal.with(localDates[resultIndex]) : null;
    }

    /**
     * Converts a Temporal object to a LocalDate.
     *
     * @param temporal the Temporal object to be converted
     * @return the converted LocalDate
     */
    private LocalDate convertToLocalDate(Temporal temporal) {
        try {
            return LocalDate.from(temporal);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Cannot convert temporal to LocalDate: " + temporal.getClass().getName());
        }
    }
}
