import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors; 
import java.util.stream.Stream;



public class SummarizingCollectors {
    public static void main(String[] args) throws Exception {
        List<Integer> L = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            L.add(i);
        }

        Stream<Integer> S = L.stream();


        //Esempio di counting
        long tot = S.collect(Collectors.counting());
        System.out.println("Count : " + tot + "\n\n");


        
        //Esempio di maxBy e minBy
        S = L.stream();
        Optional<Integer> max = S.collect(Collectors.maxBy((a, b) -> {return a.compareTo(b);}));

        S = L.stream();
        Optional<Integer> min = S.collect(Collectors.minBy((a, b) -> {return a.compareTo(b);}));

        System.out.println("max : " + max.orElse(0) + " min : " + min.orElse(0) + "\n\n");
        


        //Esempio di summingType
        S = L.stream();
        long sum = S.collect(Collectors.summingInt(a -> a.intValue()));
        System.out.println("sum : " + sum + "\n\n");


        //Esempio di averagingType
        S = L.stream();
        double avg = S.collect(Collectors.averagingInt(a -> a.intValue()));
        System.out.println("avg : " + avg + "\n\n");


        //Esempio di summarizingType
        S = L.stream();
        IntSummaryStatistics stat = S.collect(Collectors.summarizingInt((a) -> a.intValue()));
        System.out.println("count : " + stat.getCount() + " sum : " + stat.getSum() + " min : " + 
            stat.getMin() + " max : " + stat.getMax() + " avg : " + stat.getAverage() + "\n\n");



            //Esempio di toList, toSet e toCollection
        S = L.stream();
        List<Integer> L2 = S.collect(Collectors.toList());
        S = L.stream();
        Set<Integer> S2 = S.collect(Collectors.toSet());
        S = L.stream();
        Queue<Integer> Q = S.collect(Collectors.toCollection(() -> {
            return new PriorityQueue<Integer>();
        }));

        // equivalente a
        //Queue<Integer> Q2 = S.collect(Collectors.toCollection(PriorityQueue::new));

        Iterator<Integer> it_l = L2.iterator(); 
        Iterator<Integer> it_s = S2.iterator(); 
        Iterator<Integer> it_q = Q.iterator(); 

        System.out.println("Elementi nella lista:");
        while (it_l.hasNext()) {
            System.out.println(it_l.next());
        }


        System.out.println("\n\nElementi nell'insieme:");
        while (it_s.hasNext()) {
            System.out.println(it_s.next());
        }
        System.out.println("\n\nElementi nella coda:");
        while (it_q.hasNext()) {
            System.out.println(it_q.next());
        }
        System.out.println("\n\n");



        S = L.stream();
        String joined = S.map(a -> a.toString()).collect(Collectors.joining("->", "prefisso[", "]suffisso"));
        System.out.println(joined);

    }
}
