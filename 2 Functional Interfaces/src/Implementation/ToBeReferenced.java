package Implementation;

import java.util.function.IntSupplier;


public class ToBeReferenced {
    public static int double_compare(Double a, Double b) {

        //utilizziamo i parametri al contrario per ottenere l'ordinamento inverso
        return b.compareTo(a);
    }
}
