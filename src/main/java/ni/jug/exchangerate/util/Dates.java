package ni.jug.exchangerate.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author aalaniz
 */
public final class Dates {

    private Dates() {
    }

    public static int toIsoNumericFormat(LocalDate date) {
        return (date.getYear()*100 + date.getMonthValue())*100 + date.getDayOfMonth();
    }

    public static long daysForCurrentPeriod() {
        LocalDate firstDay = LocalDate.now().withDayOfMonth(1);
        LocalDate firstDayNextMonth = firstDay.plusMonths(1);
        return ChronoUnit.DAYS.between(firstDay, firstDayNextMonth);
    }

    public static long daysForPeriod(LocalDate period) {
        LocalDate firstDay = period.withDayOfMonth(1);
        LocalDate firstDayNextMonth = firstDay.plusMonths(1);
        return ChronoUnit.DAYS.between(firstDay, firstDayNextMonth);
    }

}
