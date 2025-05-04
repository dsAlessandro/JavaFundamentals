import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) throws Exception {
        /*
         * Stampa dei numeri pari da 0 a 100
         */

        IntStream.range(0, 101)
        .filter((i) -> {
            return i % 2 == 0;
        })
        .forEach(System.out::println);
        System.out.println("\n\n");
        
        
        
        
        /*
        * Stampa dei primi 30 numeri pari a partire dai numeri da 0 a 1000
        */
        
        IntStream.range(0, 101)
        .filter((i) -> {
            return i % 2 == 0;
        })
        .limit(30)
        .forEach(System.out::println);
        System.out.println("\n\n");
        




        /*
        * Stampa degli ultimi 15 numeri pari a partire dai primi 30 numeri pari da 0 a 1000
        */
        
        IntStream.range(0, 101)
        .filter((i) -> {
            return i % 2 == 0;
        })
        .limit(30)
        .skip(15)
        .forEach(System.out::println);
        System.out.println("\n\n");
        
        
        
        
        
        /*
        * Stampa ordinata delle diverse stringhe 
        * "piacere", "mi", "chiamo", "alessandro"
        * prima alfabeticamente (ordinamento naturale)
        * poi in ordine di lunghezza crescente
        */
        Stream.of("piacere", "mi", "chiamo", "alessandro")
        .sorted()
        .forEach(System.out::println);
        System.out.println("\n\n");
        
        Stream.of("piacere", "mi", "chiamo", "alessandro")
        .sorted(Comparator.comparing(String::length))
        .forEach(System.out::println);
        System.out.println("\n\n");





        /*
         * Stampa delle stringhe
         * "Alessandro", "Luca", "Alessandro", "Gennaro", "Michele", "Luca"
         * previa eliminazione dei duplicati
         */
        Stream.of("Alessandro", "Luca", "Alessandro", "Gennaro", "Michele", "Luca")
        .distinct()
        .forEach(System.out::println);
        System.out.println("\n\n");
        
        
        
        
        
        
        /*
         * Passaggio da uno Stream di stringhe ad uno Stream di interi 
         * ottenuto considerando la lunghezza di ciascuna stringa
         */
        Stream.of("piacere", "mi", "chiamo", "alessandro")
        .map(String::length)
        .forEach(System.out::println);
        System.out.println("\n\n");

    }
}
