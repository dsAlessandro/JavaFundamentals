import java.util.Iterator;

import Implementation.List;
import Implementation.SortedList;

public class Generics_Integer {
    public static void main(String[] args) throws Exception {
        List<Integer> L1 = new List();


        L1.addElement(Integer.valueOf(0));



        L1.addElement(Integer.valueOf(1));


        L1.addElement(Integer.valueOf(2));



        L1.addElement(Integer.valueOf(3));



        L1.addElement(Integer.valueOf(4));



        // Seconda lista
        List<Integer> L2 = new List<Integer>();


        L2.addElement(Integer.valueOf(4));


        L2.addElement(Integer.valueOf(3));



        L2.addElement(Integer.valueOf(2));


        L2.addElement(Integer.valueOf(1));



        L2.addElement(Integer.valueOf(0));





        System.out.println("Elementi della prima lista");
        for (Integer i : L1) {
            System.out.println(i.toString());
        }




        System.out.println("Elementi della seconda lista");
        for (Integer i : L2) {
            System.out.println(i.toString());
        }


        Integer c = Integer.valueOf(21);
        System.out.println("Provo a cancellare l'elemento " + c.toString());
        L1.deleteElement(c);
        
        
        c = Integer.valueOf(2);
        System.out.println("Provo a cancellare l'elemento " + c.toString());
        L1.deleteElement(c);

        System.out.println("Elementi della prima lista");
        for (Integer i : L1) {
            System.out.println(i.toString());
        }




        //Test con lista ordinata
        SortedList<Integer> L3 = new SortedList<>();

        //elementi inseriti in ordine casuale
        
        L3.addElement(Integer.valueOf(0));

        L3.addElement(Integer.valueOf(4));

        L3.addElement(Integer.valueOf(1));

        L3.addElement(Integer.valueOf(3));

        L3.addElement(Integer.valueOf(2));

        System.out.println("Elementi della lista ordinata");
        for (Integer i : L3) {
            System.out.println(i.toString());
        }

    }
}
