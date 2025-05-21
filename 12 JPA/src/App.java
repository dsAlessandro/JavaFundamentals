import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.*;

public class App {
    static class TempoVar implements TemporalAdjuster {

        @Override
        public Temporal adjustInto(Temporal temporal) {
            return temporal.plus(Duration.ofMinutes(45));
        }

    }

    public static void main(String[] args) throws Exception {
        TempoVar MarioDeStasi = new TempoVar();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");



        int hour = 9;
        int min = 0;
        LocalTime Partenza = LocalTime.of(hour, min);

        System.out.println("Partiamo alle " + Partenza);
        
        LocalTime PartenzaReale = Partenza.with(MarioDeStasi);
        System.out.println("Partiamo realmente alle " + PartenzaReale);
    }
}
