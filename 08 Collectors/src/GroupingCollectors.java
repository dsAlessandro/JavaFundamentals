import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.stream.Stream;

import Implementation.Data;

public class GroupingCollectors {
    public static void main(String[] args) throws Exception {
        String[] lyrics = Data.TESTO.split("[\n,' ]+");



        System.out.println("groupingBy #1");
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
        System.out.println("groupingBy #2, con utilizzo di downstream");
        Map<Integer, Long> M2 = Stream.of(lyrics)
                                .distinct()
                                .collect(Collectors.groupingBy(String::length, Collectors.counting()));

        for (Map.Entry<Integer, Long> e : M2.entrySet()) {
            System.out.println("Lunghezza : " + e.getKey() + " -> " + e.getValue());
        }
        System.out.println("\n\n");
        



        //Alternativa: specifica del tipo di mappa
        System.out.println("groupingBy #3, con specifica del tipo di mappa da utilizzare");
        Map<Integer, Long> M3 = Stream.of(lyrics)
                                .distinct()
                                .collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.counting()));

        for (Map.Entry<Integer, Long> e : M3.entrySet()) {
            System.out.println("Lunghezza : " + e.getKey() + " -> " + e.getValue());
        }
        System.out.println("\n\n");
        




        //Utilizzo di collectingAndThen
        System.out.println("collectingAndThen");
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
        System.out.println("mapping");
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


        
        //Implementazione di un custom collector
        /*
         * Dato un arrai di stringhe distinte, le si vuole dividere per lunghezza
         * e a ciascuna lunghezza si vuole far corrispondere una List<Integer>
         * di dimensione 5 che conta per ogni vocale quante parole iniziano per quella
         * vocale
         */
        Map<Integer, List<Integer>> M6 = 
        Stream.of(lyrics)
        .distinct()
        .collect(
            groupingBy(
                String::length,
                Collector.of(
                    () -> {
                        ArrayList<Integer> l = new ArrayList<Integer>();
                        for (int i = 0; i < 5; i++) {
                            l.add(0);
                        }
                        return l;
                    },
                    (l, S) -> {
                        char c = S.charAt(0);
                        if ('A' <= c && c <= 'Z') {
                            c = (char) (c - 'A' + 'a');
                        }

                        switch (c) {
                            case 'a': {
                                Integer att = l.get(0);
                                l.set(0, att+1);
                                break;
                            }
                            case 'e': {
                                Integer att = l.get(1);
                                l.set(1, att+1);
                                break;
                            }
                            case 'i': {
                                Integer att = l.get(2);
                                l.set(2, att+1);
                                break;
                            }
                            case 'o': {
                                Integer att = l.get(3);
                                l.set(3, att+1);
                                break;
                            }
                            case 'u': {
                                Integer att = l.get(4);
                                l.set(4, att+1);
                                break;
                            }
                            default: break;
                        }
                    },
                    (l1, l2) -> {
                        Integer att1, att2;
                        for (int i = 0; i < 5; i++) {
                            att1 = l1.get(i);
                            att2 = l2.get(i);
                            l1.set(i, att1 + att2);
                        }
                        return l1;
                    },
                    (l) -> {
                        return l;
                    }
                )
            )
        );

        for (Map.Entry<Integer, List<Integer>> E : M6.entrySet()) {
            System.out.println("Lunghezza: " + E.getKey());
            System.out.println(E.getValue() + "\n");
        }
    }
}
