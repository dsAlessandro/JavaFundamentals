import java.util.Iterator;

import Implementation.Complex;
import Implementation.List;

public class Generics_Complex {
    public static void main(String[] args) throws Exception {
        // Prima lista
        List<Complex> L1 = new List();


        L1.addElement(new Complex(0, 1));



        L1.addElement(new Complex(1, 2));


        L1.addElement(new Complex(2, 3));



        L1.addElement(new Complex(3, 4));



        L1.addElement(new Complex(4, 5));



        // Seconda lista
        List<Complex> L2 = new List();


        L2.addElement(new Complex(5,4));


        L2.addElement(new Complex(4, 3));



        L2.addElement(new Complex(3, 2));


        L2.addElement(new Complex(2, 1));



        L2.addElement(new Complex(1, 0));





        System.out.println("Elementi della prima lista");
        Iterator<Complex> it1 = L1.iterator();
        while (it1.hasNext()) {
            System.out.println(it1.next().toString());
        }




        System.out.println("Elementi della seconda lista");
        Iterator<Complex> it2 = L2.iterator();
        while (it2.hasNext()) {
            System.out.println(it2.next().toString());
        }


        Complex c = new Complex(1, 1);
        System.out.println("Provo a cancellare l'elemento " + c.toString());
        L1.deleteElement(c);
        
        
        c.setBoth(0, 1);
        System.out.println("Provo a cancellare l'elemento " + c.toString());
        L1.deleteElement(c);

        System.out.println("Elementi della prima lista");
        it1 = L1.iterator();
        while (it1.hasNext()) {
            System.out.println(it1.next().toString());
        }
    }
}
