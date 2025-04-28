package Implementation;

import java.util.Comparator;

public class CustomCompare implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Double a = (Double) o1;
        Double b = (Double) o2;


        if (a < b)
            return 1;
        
        if (a > b)
            return -1;

        return 0;
    }

}
