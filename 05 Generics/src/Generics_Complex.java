import java.util.Iterator;

import Implementation.Complex;
import Implementation.List;

public class Generics_Complex {
    public static void main(String[] args) throws Exception {
        // Prima lista
        List<Complex> L1 = new List<Complex>();


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
        for (Complex c : L1) {
            System.out.println(c.toString());
        }




        System.out.println("Elementi della seconda lista");
        for (Complex c : L2) {
            System.out.println(c.toString());
        }


        Complex C = new Complex(1, 1);
        System.out.println("Provo a cancellare l'elemento " + C.toString());
        L1.deleteElement(C);
        
        
        C.setBoth(0, 1);
        System.out.println("Provo a cancellare l'elemento " + C.toString());
        L1.deleteElement(C);

        System.out.println("Elementi della prima lista");
        for (Complex c : L1) {
            System.out.println(c.toString());
        }
    }
}
