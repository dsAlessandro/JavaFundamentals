import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalAmount;
import java.util.Locale;

import static java.util.stream.Collectors.*;

public class DateTime {
    public static String nicerPrinter(String s) {
        return 
        s.chars()
        .mapToObj((i) -> (char) i)
        .collect(
            mapping((c) -> {
                    if (c.compareTo('P') == 0) {
                        return "";
                    }
                    if (c.compareTo('Y') == 0) 
                        return " Anni ";

                    if (c.compareTo('M') == 0) 
                        return " Mesi ";

                    if (c.compareTo('D') == 0) 
                        return " Giorni ";

                    return c.toString();
                },
                joining()
            )
        );
    }

    public static void main(String[] args) throws Exception {
        Temporal i = Instant.now();


        System.out.println("\n\nIstante attuale (UTC):");
        System.out.println(i);


        Temporal t1 = LocalDate.now();
        Temporal t2 = LocalTime.now();
        System.out.println("\n\nData e ora attuali (Italiane):");
        System.out.println(t1 + " " + t2);

        LocalDate xmas = LocalDate.of(2025, 12, 25);
        System.out.println("\n\nNatale è caduto di");
        System.out.println(xmas.getDayOfWeek());

        System.out.println("\n\nOppure in italiano");
        DayOfWeek d = xmas.getDayOfWeek();
        System.out.println(d.getDisplayName(TextStyle.FULL, Locale.ITALIAN));

        TemporalAmount toXMAS = Period.between((LocalDate) t1, (LocalDate) xmas);
        System.out.println("\n\nNatale è tra:");
        System.out.println(toXMAS);
        System.out.println("Che possiamo tradurre come:");
        System.out.println(nicerPrinter(toXMAS.toString()));


        Temporal tenBeforeXMAS = xmas.minus(Period.ofDays(10));
        System.out.println("\n\nLa data 10 giorni prima di Natale è:");
        System.out.println(tenBeforeXMAS);


        LocalDate firstOfxmasMonth = xmas.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println("\n\nIl primo giorno del mese in cui cade natale è:");
        System.out.println(firstOfxmasMonth);


        System.out.println("\n\nNumero di giorni infra-setimanali tra il " + xmas + " e il " + firstOfxmasMonth);
        long n_infra = 
            firstOfxmasMonth.datesUntil(xmas.plusDays(1))
            .filter((D) -> D.getDayOfWeek() != DayOfWeek.SATURDAY)
            .filter((D) -> D.getDayOfWeek() != DayOfWeek.SUNDAY)
            .count();
        System.out.println(n_infra);
    }
}
