
import Implementation.Data;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) throws Exception {
        String[] words = Data.TESTO.split("[\n,' ]+");
        
        
        System.out.println(Arrays.toString(words) + "\n\n");



        //METODO 1
        TreeSet<String> wordsSet = new TreeSet<>();
        for (int i = 0; i < words.length; i++) {
            wordsSet.add(words[i]);
        }

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

        Stream.of(wordsSet)
        .sorted()
        .distinct()
        .limit(4)
        .forEach(System.out::println);

    }
}

