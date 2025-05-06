package Implementation;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ToBeReferenced {
    public static void Ex1(String[] S) {
        IntSummaryStatistics res = 
        Stream.of(S)
        .collect(
            summarizingInt((s) -> s.length())
        );

        System.out.println("Max len : " + res.getMax() + "\nMin len : " + res.getMin());
    }


    public static void Ex1_alt(String[] S) {
        //definizione dell'accumulatore
        class Acc {
            Integer min = Integer.MAX_VALUE;
            Integer max = Integer.MIN_VALUE;
        };


        Acc res = 
        Stream.of(S)
        .map(String::length)
        .collect(
            Acc::new, //supplier

            // accumulator
            (a, l) -> {
                if (l > a.max) {
                    a.max = l;
                }
                if (l < a.min) {
                    a.min = l;
                }
            },

            // combiner
            (a1, a2) -> {
                if (a1.min > a2.min) {
                    a1.min = a2.min;
                }
                if (a1.max < a2.max) {
                    a1.max = a2.max;
                }
                return;
            }
        );

        System.out.println("Max len : " + res.max + "\nMin len : " + res.min);
    }


    public static void Ex2(String[] S) {
        Map<String, Long> res = 
        Stream.of(S)
        .map(String::toLowerCase)
        .collect(
            groupingBy(
                (s) -> s,
                counting()
            )
        );

        for (Map.Entry<String, Long> e : res.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }




    public static void Ex3(String[] S) {

        Set<Character> res = 
        Stream.of(S)
        .map(String::toCharArray)
        .collect(
            TreeSet::new,
            (s, arr) -> {
                for (Character el : arr) {
                    s.add(el);
                }
            },
            (s1, s2) -> {
                s1.addAll(s2);
            }
        );

        
        System.out.println(res.toString());
    }
    
    
    public static void Ex3_alt(String[] S) {
        
        
        Set<Character> res = 
        Stream.of(S)
        .flatMap((s) -> {
            List<Character> L = new ArrayList<>();
            for (char c : s.toCharArray()) {
                L.add(Character.valueOf(c));
            }
            return L.stream();
        })
        .collect(
            toSet()
            );
            
            
            System.out.println(res.toString());
        }
        
        


    public static void Ex4(String[] S) {

        Map<Character, Long> res = 
        Stream.of(S)
        .flatMap(   (s) -> (s.chars()).mapToObj((c) -> (char) c)  )
        .collect(
            groupingBy(
                c -> c,
                counting()
            )
        );

        for (Map.Entry<Character, Long> e : res.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}
