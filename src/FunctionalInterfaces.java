import java.util.Arrays;
import java.util.Comparator;
import Implementation.CustomCompare;
import Implementation.ToBeReferenced;

public class FunctionalInterfaces {
    public static void main(String[] args) throws Exception {
        
        //Soluzione 1
        System.out.println("\n\nSoluzione 1");
        Double[] v1 = {17.0, 12.3, 14.3, 13.0, 99.0, 17.0};
        Arrays.sort(v1, new CustomCompare());
        System.out.println(Arrays.toString(v1));






        //Soluzione 1.5, implementazione con classe anonima
        Comparator CustomComparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Double d1 = (Double) o1;
                Double d2 = (Double) o2;
                if (d1 < d2)
                    return 1;
                if (d1 > d2)
                    return -1;
                return 0;
            }
        };

        System.out.println("\n\nSoluzione 1.5");
        Double[] v1_5 = {17.0, 12.3, 14.3, 13.0, 99.0, 17.0};
        Arrays.sort(v1_5, new CustomCompare());
        System.out.println(Arrays.toString(v1_5));







        //Soluzione 2, lambda expression
        System.out.println("\n\nSoluzione 2");
        Double[] v2 = {17.0, 12.3, 14.3, 13.0, 99.0, 17.0};
        Comparator CustomCompare_lambdaexp = (a, b) -> {
            return ( (Double) b).compareTo( (Double) a );
        };

        Arrays.sort(v2, CustomCompare_lambdaexp);

        System.out.println(Arrays.toString(v2));








        //Soluzione 3, method reference

        System.out.println("\n\nSoluzione 3");
        Double[] v3 = {17.0, 12.3, 14.3, 13.0, 99.0, 17.0};
        Arrays.sort(v3, ToBeReferenced::double_compare); 

        System.out.println(Arrays.toString(v3));

    }
}
