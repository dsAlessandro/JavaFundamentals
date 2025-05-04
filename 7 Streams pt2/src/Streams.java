import java.util.Comparator;
import java.util.Optional;
import java.util.OptionalInt;
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





        /*
         * Esempio di utilizzo del metodo findAny
         */
        OptionalInt res = IntStream.range(0, 1000000)
        .parallel() //permette di eseguire le diverse operazioni in parallelo, a patto che ci siano le risorse
        .map(i -> i+2)
        .sorted()
        .findAny();
        if (res.isPresent()) {
            System.out.println(res.getAsInt() + "\n\n");
        }
        else {
            System.out.println("Impossibile eseguire le operazioni");
        }

        /*
         * in fase di testing il primo risultato ottenuto è stato 656252
         * a dispetto di 2, ovvero il risultato finale del primo elemento (cioè 0)
         */







        /*
         * Esempio di utilizzo del metodo findFirst
         */
        res = IntStream.range(0, 1000000)
        .parallel() //permette di eseguire le diverse operazioni in parallelo, a patto che ci siano le risorse
        .map(i -> i+2)
        .findFirst();
        if (res.isPresent()) {
            System.out.println(res.getAsInt() + "\n\n");
        }
        else {
            System.out.println("Impossibile eseguire le operazioni");
        }
        /*
         * in fase di testing ritorna 2
         */





         /*
          * Esempio di utilizzo del metodo max
          */
        Optional<Integer> a = Stream.of("piacere", "mi", "chiamo", "alessandro")
        .map((s) -> {
            return Integer.valueOf(s.length());
        })
        .max((o1, o2) -> {
            return o1.compareTo(o2);
        });
        
        if (a.isPresent()) {
            System.out.println(a.get() + "\n\n");
        }
        else {
            System.out.println("Impossibile eseguire le operazioni");
        }






         /*
          * Esempio di utilizzo del metodo count
          */
        long b = Stream.of("piacere", "mi", "chiamo", "alessandro")
        .map((s) -> {
            return Integer.valueOf(s.length());
        })
        .count();

        System.out.println(a + "\n\n");


        IntStream.range(1, 100)
        .forEach(System.out::println);
        System.out.println("\n\n");





        /*
         * Esempio di utilizzo di (any/all/none)Match
         */
        boolean check;
        check = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .anyMatch((i) -> {
            return i > 3;
        });
        if (check) {
            System.out.println("Qualcuno è passato");
        }
        else {
            System.out.println("Nessuno è passato");
        }




        check = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .allMatch((i) -> {
            return i > 3;
        });
        if (check) {
            System.out.println("Tutti sono passati");
        }
        else {
            System.out.println("Non tutti sono passati");
        }



        check = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .noneMatch((i) -> {
            return i > 3;
        });
        if (check) {
            System.out.println("Nessuno è passato");
        }
        else {
            System.out.println("Qualcuno è passato");
        }
        System.out.println("\n\n");




        /*
         * Esempio di utilizzo del metodo reduce
         */
    
        Optional<Integer> tmp = Stream.of(1, 2, 3, 4, 5)
        .reduce((t1, t2) -> {return t1+t2;});
        if (tmp.isPresent()) {
            System.out.println(tmp.get() + "\n\n");
        }
        else {
            System.out.println("Impossibile eseguire le operazioni");
        }




        /*
         * Esempio di utilizzo del metodo collect
         */
        class Accumulator {
            int sum = 0;
        }
        
        int sum = IntStream.range(0, 200)
        .collect(
            //primo parametro, il riferimento al metodo che permette di creare l'accumulatore
            Accumulator::new,
        
            //secondo parametro, una lambda expression che illustra come accumulare i dati
            (A, i) -> A.sum += i,
        
            //terzo parametro, una lambda expression che illustra come compattare i dati accumulati in
            //diversi accumulatori
            (A1, A2) -> A1.sum += A2.sum
        ).sum;
        System.out.println(sum + "\n\n");


    }
}
