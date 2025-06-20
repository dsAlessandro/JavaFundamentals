package Implementation;

import java.util.function.Supplier;

public class FibonacciSupplier implements Supplier<Integer> {
    Integer[] ultimi = {1, 1};
    
    @Override
    public Integer get() {
        int res = ultimi[0];
        int next = ultimi[0] + ultimi[1];
        
        ultimi[0] = ultimi[1];
        ultimi[1] = next;
        
        return res;
    }
}
