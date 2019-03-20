package ni.jug.exchangerate.util;

import java.time.LocalDate;

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

}
