package io.p4r53c.telran;

import java.time.chrono.ChronoLocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;

public class PastTemporalDateProximity implements TemporalAdjuster {

    private ChronoLocalDate[] chronoLocalDates;

    public PastTemporalDateProximity(Temporal[] temporals) {
        this.chronoLocalDates = Arrays.copyOf(new ChronoLocalDate[temporals.length], temporals.length);

        int index = 0;
        for (Temporal temporal : temporals) {
            this.chronoLocalDates[index++] = ChronoLocalDate.from(temporal);
        }
        Arrays.sort(this.chronoLocalDates);
    }

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