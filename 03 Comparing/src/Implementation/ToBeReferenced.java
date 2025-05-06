package Implementation;

import java.util.function.IntSupplier;

import NewType.NewType;

public class ToBeReferenced {

    public static String Example1(String s) {
        return s;
    }


    public static int Example2(String s) {
        return s.length();
    }


    public static int Example3(NewType t) {
        return t.x + t.y + t.z;
    }


    public static int test() {
        return 12;
    }





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


    public static IntSupplier alternative() {

        return ToBeReferenced::test;
    }
}
