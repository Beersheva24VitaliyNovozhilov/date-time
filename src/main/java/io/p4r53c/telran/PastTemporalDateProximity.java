package io.p4r53c.telran;

import java.time.chrono.ChronoLocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;

/**
 * Class that implements the TemporalAdjuster interface and provides a way to find the
 * closest past date from a given set of past dates.
 *
 * @author p4r53c
 */
public class PastTemporalDateProximity implements TemporalAdjuster {

    private ChronoLocalDate[] chronoLocalDates;

    /**
     * Constructs a new PastTemporalDateProximity object.
     *
     * @param temporals the array of past Temporal objects
     */
    public PastTemporalDateProximity(Temporal[] temporals) {
        this.chronoLocalDates = Arrays.copyOf(new ChronoLocalDate[temporals.length], temporals.length);

        int index = 0;
        for (Temporal temporal : temporals) {
            this.chronoLocalDates[index++] = ChronoLocalDate.from(temporal);
        }
        Arrays.sort(this.chronoLocalDates);
    }

    /**
     * Adjusts the given Temporal object to the closest past date from the set of past dates.
     *
     * @param temporal the Temporal object to be adjusted
     * @return the adjusted Temporal object or null if no past date is found
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        ChronoLocalDate targetDate = ChronoLocalDate.from(temporal);

        int low = 0;
        int high = chronoLocalDates.length - 1;
        int resultIndex = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (chronoLocalDates[mid].isBefore(targetDate)) {
                resultIndex = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return resultIndex != -1 ? temporal.with(chronoLocalDates[resultIndex]) : null;
    }
}
