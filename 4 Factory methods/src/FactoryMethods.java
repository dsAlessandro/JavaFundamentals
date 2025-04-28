import java.util.function.IntSupplier;

import Implementation.ToBeReferenced;


public class FactoryMethods {

    //Preparazione per la sintassi estesa
    static class fibonacci implements IntSupplier {
        int[] ultimi = {1, 1};
    

        @Override
        public int getAsInt() {
            int res = ultimi[0];
            int next = ultimi[0] + ultimi[1];
    
            ultimi[0] = ultimi[1];
            ultimi[1] = next;
    
            return res;
        }

    }
    






    
    public static void main(String[] args) throws Exception {

        //output della sintassi estesa
        fibonacci temp1 = new fibonacci();
        
        for (int i = 0; i < 10; i++) {
            System.out.println(temp1.getAsInt());
        }








        //output della sintassi compatta
        IntSupplier temp2 = ToBeReferenced.fibonacci();
        for (int i = 0; i < 10; i++) {
            System.out.println(temp2.getAsInt());
        }







        //esempio extra con method reference
        IntSupplier temp3 = ToBeReferenced.alternative();
        for (int i = 0; i < 10; i++) {
            System.out.println(temp3.getAsInt());
        }

    }

}
