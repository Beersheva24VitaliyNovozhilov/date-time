package io.p4r53c.telran;

import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;

public class PastTemporalDateProximity implements TemporalAdjuster {

    private Temporal[] temporals;

    public PastTemporalDateProximity(Temporal[] temporals) {
        this.temporals = Arrays.copyOf(temporals, temporals.length);
        Arrays.sort(this.temporals, this::compareTemporals);
    }

    /**
     * Adjusts the given Temporal object to the closest past date in the sorted
     * array of temporals.
     *
     * @param temporal the Temporal object to be adjusted
     * @return the closest past Temporal object in the array, or null if no such
     *         object exists
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        int low = 0;
        int high = temporals.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (compareTemporals(temporals[mid], temporal) < 0) {
                result = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result != -1 ? temporals[result] : null;
    }

    /**
     * Compares two Temporal objects in chronological order.
     *
     * @param t1 the first Temporal object to compare
     * @param t2 the second Temporal object to compare
     * @return a negative integer if t1 is before t2, zero if t1 is equal to t2, and
     *         a positive integer if t1 is after t2
     */
    private int compareTemporals(Temporal t1, Temporal t2) {
        int result = compareYears(t1, t2);

        if (result == 0) {
            result = compareMonths(t1, t2);
            if (result == 0) {
                result = compareDays(t1, t2);
            }
        }

        return result;
    }

    /**
     * Compares two Temporal objects based on their year values.
     *
     * @param t1 the first Temporal object to compare
     * @param t2 the second Temporal object to compare
     * @return a negative integer if t1's year is before t2's year, zero if t1's
     *         year is equal to t2's year, and a positive integer if t1's year is
     *         after t2's year
     */
    private int compareYears(Temporal t1, Temporal t2) {
        int result = 0;

        if (t1.isSupported(ChronoField.YEAR) && t2.isSupported(ChronoField.YEAR)) {
            result = Integer.compare(t1.get(ChronoField.YEAR), t2.get(ChronoField.YEAR));
        }

        return result;
    }

    /**
     * Compares two Temporal objects based on their month values.
     *
     * @param t1 the first Temporal object to compare
     * @param t2 the second Temporal object to compare
     * @return a negative integer if t1's month is before t2's month, zero if t1's
     *         month is equal to t2's month, and a positive integer if t1's month is
     *         after t2's month
     */
    private int compareMonths(Temporal t1, Temporal t2) {
        int result = 0;

        if (t1.isSupported(ChronoField.MONTH_OF_YEAR) && t2.isSupported(ChronoField.MONTH_OF_YEAR)) {
            result = Integer.compare(t1.get(ChronoField.MONTH_OF_YEAR), t2.get(ChronoField.MONTH_OF_YEAR));
        }

        return result;
    }

    /**
     * Compares two Temporal objects based on their day values.
     *
     * @param t1 the first Temporal object to compare
     * @param t2 the second Temporal object to compare
     * @return a negative integer if t1's day is before t2's day, zero if t1's day
     *         is equal to t2's day, and a positive integer if t1's day is after
     *         t2's day
     */
    private int compareDays(Temporal t1, Temporal t2) {
        int result = 0;

        if (t1.isSupported(ChronoField.DAY_OF_MONTH) && t2.isSupported(ChronoField.DAY_OF_MONTH)) {
            result = Integer.compare(t1.get(ChronoField.DAY_OF_MONTH), t2.get(ChronoField.DAY_OF_MONTH));
        }

        return result;
    }
}