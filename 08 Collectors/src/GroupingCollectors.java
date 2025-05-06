import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

import java.util.IntSummaryStatistics;
import java.util.stream.Stream;

import Implementation.Data;

public class GroupingCollectors {
    public static void main(String[] args) throws Exception {
        String[] lyrics = Data.TESTO.split("[\n,' ]+");

        Map<Integer, List<String> > M = Stream.of(lyrics)
        .distinct() //opzionale, aggiunto per estetica
        .collect(Collectors.groupingBy(String::length));


        List<String> L;
        for (Map.Entry<Integer, List<String> > e : M.entrySet()) {
            System.out.println("Lunghezza : " + e.getKey());
            L = e.getValue();

            for (String s : L) {
                System.out.println(s);
            }

            System.out.println("\n\n");
        }
        System.out.println("\n\n");









        //Alternativa: passaggio di downstream collector al groupingBy
        Map<Integer, Long> M2 = Stream.of(lyrics)
                                .distinct()
                                .collect(Collectors.groupingBy(String::length, Collectors.counting()));

        for (Map.Entry<Integer, Long> e : M2.entrySet()) {
            System.out.println("Lunghezza : " + e.getKey() + " -> " + e.getValue());
        }
        System.out.println("\n\n");
        





        //Alternativa: specifica del tipo di mappa
        Map<Integer, Long> M3 = Stream.of(lyrics)
                                .distinct()
                                .collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.counting()));

        for (Map.Entry<Integer, Long> e : M3.entrySet()) {
            System.out.println("Lunghezza : " + e.getKey() + " -> " + e.getValue());
        }
        System.out.println("\n\n");
        





        //Utilizzo di collectingAndThen
        Map<Integer, String> M4 = Stream.of(lyrics)
                                    .distinct()
                                    .collect(
                                        groupingBy(
                                            String::length,
                                            collectingAndThen(
                                                counting(),
                                                (l) -> {return l.toString() + " (come stringa)";}
                                            )
                                        )
                                    );
        

        for (Map.Entry<Integer, String> e : M4.entrySet()) {
            System.out.println("Lunghezza : " + e.getKey() + " -> " + e.getValue());
        }
        System.out.println("\n\n");








        //Utilizzo di mapping
        Map<Integer, IntSummaryStatistics> M5 = Stream.of(lyrics)
                                    .distinct()
                                    .collect(
                                        groupingBy(
                                            String::length,
                                            mapping(
                                                (s) -> {return s.length();},
                                                summarizingInt(i -> i)
                                            )
                                        )
                                    );
        

        for (Map.Entry<Integer, IntSummaryStatistics> e : M5.entrySet()) {
            System.out.println("Lunghezza : " + e.getKey() + 
            "\nNumero di parole : " + e.getValue().getCount() + 
            "\nSomma delle lunghezze (lunghezza * no_parole) : " + e.getValue().getSum() + 
            "\nMinima lunghezza (lol) : " + e.getValue().getMin() + 
            "\nMassima lunghezza (lol) : " + e.getValue().getMax() + 
            "\nLunghezza media (lol) : " + e.getValue().getAverage() +
            "\n"
            );
        }
        System.out.println("\n\n");

    }
}
