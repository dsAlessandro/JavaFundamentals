import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class Collections {
    public static void main(String[] args) throws Exception {
        int n = 10;


        Set<Integer> S1 = new HashSet<>();
        Set<Integer> S2 = new LinkedHashSet<>();
        Set<Integer> S3 = new TreeSet<>();

        Queue<Integer> Q1 = new LinkedList<>();
        Queue<Integer> Q2 = new ArrayDeque<>();
        Queue<Integer> Q3 = new PriorityQueue<>();

        List<Integer> L1 = new LinkedList<>();
        List<Integer> L2 = new ArrayList<>();


        for (int i = n; i >= 0; i--) {
            S1.add(i);
            S2.add(i);
            S3.add(i);

            Q1.add(i);
            Q2.add(i);
            Q3.add(i);

            L1.add(i);
            L2.add(i);
        }

        System.out.println("\nGroup Containers:\n");
        System.out.println("SETS");

        System.out.println("HashSet:");
        
        for (Integer i : S1) {
            System.out.println(i);
        }



        System.out.println("\nLinkedHashSet:");
        
        for (Integer i : S2) {
            System.out.println(i);
        }




        System.out.println("\nSortedSet:");
        
        for (Integer i : S3) {
            System.out.println(i);
        }






        System.out.println("\nQUEUES");

        System.out.println("LinkedList:");
        
        for (Integer i : Q1) {
            System.out.println(i);
        }



        System.out.println("\nArrayList:");
        
        for (Integer i : Q2) {
            System.out.println(i);
        }




        System.out.println("\nPriorityQueue:");
        
        for (Integer i : Q3) {
            System.out.println(i);
        }




        System.out.println("\nLISTS");

        System.out.println("LinkedList");
        
        for (Integer i : L1) {
            System.out.println(i);
        }



        System.out.println("\nArrayList:");
        
        for (Integer i : L2) {
            System.out.println(i);
        }






        Map<String, Integer> M1 = new HashMap<>();
        Map<String, Integer> M2 = new LinkedHashMap<>();
        Map<String, Integer> M3 = new TreeMap<>();

        M1.put("Ciao", 4);
        M2.put("Ciao", 4);
        M3.put("Ciao", 4);
        
        M1.put("Lombrico", 77);
        M2.put("Lombrico", 77);
        M3.put("Lombrico", 77);

        M1.put("Incredibile", 12);
        M2.put("Incredibile", 12);
        M3.put("Incredibile", 12);


        System.out.println("\nAssociative Containers:\n");
        System.out.println("MAPS");
        
        System.out.println("HashMap:");
        for (Map.Entry<String, Integer> e : M1.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }


        System.out.println("\nLinkedHashMap:");
        for (Map.Entry<String, Integer> e : M2.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }

        System.out.println("\nSORTED MAPS");
        System.out.println("TreeMap:");

        for (Map.Entry<String, Integer> e : M3.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }


    }
}
