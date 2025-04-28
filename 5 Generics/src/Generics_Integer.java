import java.util.Iterator;

import Implementation.Complex;
import Implementation.List;

public class Generics_Integer {
    public static void main(String[] args) throws Exception {
        List<Integer> L1 = new List();


        L1.addElement(Integer.valueOf(0));



        L1.addElement(Integer.valueOf(1));


        L1.addElement(Integer.valueOf(2));



        L1.addElement(Integer.valueOf(3));



        L1.addElement(Integer.valueOf(4));



        // Seconda lista
        List<Integer> L2 = new List();


        L2.addElement(Integer.valueOf(4));


        L2.addElement(Integer.valueOf(3));



        L2.addElement(Integer.valueOf(2));


        L2.addElement(Integer.valueOf(1));



        L2.addElement(Integer.valueOf(0));





        System.out.println("Elementi della prima lista");
        Iterator<Integer> it1 = L1.iterator();
        while (it1.hasNext()) {
            System.out.println(it1.next().toString());
        }




        System.out.println("Elementi della seconda lista");
        Iterator<Integer> it2 = L2.iterator();
        while (it2.hasNext()) {
            System.out.println(it2.next().toString());
        }


        Integer c = Integer.valueOf(21);
        System.out.println("Provo a cancellare l'elemento " + c.toString());
        L1.deleteElement(c);
        
        
        c = Integer.valueOf(2);
        System.out.println("Provo a cancellare l'elemento " + c.toString());
        L1.deleteElement(c);

        System.out.println("Elementi della prima lista");
        it1 = L1.iterator();
        while (it1.hasNext()) {
            System.out.println(it1.next().toString());
        }
    }
}
