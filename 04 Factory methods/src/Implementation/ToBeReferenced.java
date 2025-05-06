package Implementation;

import java.util.function.IntSupplier;

import NewType.NewType;

public class ToBeReferenced {

    //Factory Method
    public static IntSupplier fibonacci() {
        int[] ultimi = {1, 1};
        
        return () -> {
            int res = ultimi[0];
            int next = ultimi[0] + ultimi[1];
            
            ultimi[0] = ultimi[1];
            ultimi[1] = next;
            
            return res;
        };
    }



    


    
    public static int test() {
        return 12;
    }


    public static IntSupplier alternative() {

        return ToBeReferenced::test;
    }
}
