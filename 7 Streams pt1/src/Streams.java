
import Implementation.Data;
import java.util.Arrays;
import java.util.Iterator;
import java.util.OptionalDouble;
import java.util.TreeSet;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) throws Exception {
        
        /*
        * Stampa delle prime 4 parole distinte in ordine alfabetico
        * nella sigla delle Winx
        */
        String[] words = Data.TESTO.split("[\n,' ]+");
        System.out.println(Arrays.toString(words) + "\n\n");
        
        
        
        //METODO 1
        TreeSet<String> wordsSet = new TreeSet<>(Arrays.asList(words));
        
        
        Iterator<String> it = wordsSet.iterator();
        for (int i = 0; i < 4; i++) {
            if (it.hasNext())
            System.out.println(it.next());
        }
        System.out.println("\n\n");
        
        
        
        
        
        
        //METODO 2
        Stream.of(words)
        .sorted()
        .distinct()
        .limit(4)
        .forEach(System.out::println);
        System.out.println("\n\n");
        
        





        

        
        /*
        * Creazione di uno stream
        */
        
        Integer[] arr = {1, 2, 3};
        Arrays.stream(arr).forEach(System.out::println);
        System.out.println("\n\n");
        
        


        
        TreeSet<Double> set = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(Double.valueOf(i));
        }
        
        set.stream().forEach(System.out::println);
        System.out.println("\n\n");
        
        
        
        
        
        Stream.of(1, 2, 3, 4, 5).forEach(System.out::println);
        System.out.println("\n\n");
        
        
        



        
        
        
        
        /*
        * Utilizzo del .generate()
        */
        
        int[] ultimi = {1, 1};
        Stream.generate(() -> {
            int res = ultimi[0];
            int next = ultimi[0] + ultimi[1];
            
            ultimi[0] = ultimi[1];
            ultimi[1] = next;
            
            return res;
        }).limit(14).forEach(System.out::println);
        System.out.println("\n\n");
        
        
        




        
        /*
        * Stream per dati numerici, utilizzo del metodo .average
        */
        
        OptionalDouble avg = IntStream.iterate(0, (x) -> {return x + 2;}).limit(100).average();
        if (avg.isPresent())
            System.out.println(avg.getAsDouble());
        else
            System.out.println("Impossibile calcolare media");
        System.out.println("\n\n");
        
    }
}

