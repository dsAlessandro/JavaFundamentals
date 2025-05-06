import java.util.Arrays;
import java.util.Comparator;

import Implementation.ToBeReferenced;
import NewType.NewType;




public class Comparing {
    public static void main(String[] args) throws Exception {


        //Vogliamo ordinare le stringhe per lunghezze crescenti
        
        
        
        //METODO 1
        System.out.println("\n\nMETODO 1");

        String[] words1 = {"comes", "ciao", "woooow", "123456", "nowayys"};

        class OrderByLength implements Comparator {
            @Override
            public int compare(Object o1, Object o2) {
                String s1 = (String) o1;
                String s2 = (String) o2;

                return s1.length() - s2.length();
            }
        }

        Comparator cmp = new OrderByLength();
        Arrays.sort(words1, cmp);

        System.out.println(Arrays.toString(words1));







        //METODO 2
        System.out.println("\n\nMETODO 2");

        String[] words2 = {"comes", "ciao", "woooow", "123456", "nowayys"};

        Comparator cmp2 = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String s1 = (String) o1;
                String s2 = (String) o2;

                return s1.length() - s2.length();
            }
        };

        Arrays.sort(words2, cmp2);

        System.out.println(Arrays.toString(words2));





        //METODO 3
        System.out.println("\n\nMETODO 3");

        String[] words3 = {"comes", "ciao", "woooow", "123456", "nowayys"};

        Arrays.sort(words3, (s1, s2) -> {return s1.length() - s2.length();});

        System.out.println(Arrays.toString(words3));





        //METODO 4
        System.out.println("\n\nMETODO 4");

        String[] words4 = {"comes", "ciao", "woooow", "123456", "nowayys"};

        Arrays.sort(words4, Comparator.comparing(ToBeReferenced::Example2));

        System.out.println(Arrays.toString(words4));









        //METODO 5
        System.out.println("\n\nMETODO 5");

        String[] words5 = {"comes", "ciao", "woooow", "123456", "nowayys"};

        Arrays.sort(words5, Comparator.comparing(String::length));

        System.out.println(Arrays.toString(words5));













        NewType[] arr = new NewType[5];
        arr[0] = new NewType(1, 1, 1);
        arr[1] = new NewType(4, 4, 4);
        arr[2] = new NewType(5, 5, 5);
        arr[3] = new NewType(2, 2, 2);
        arr[4] = new NewType(3, 3, 3);


        //versione standard
        System.out.println("\n\nVersione standard");

        Arrays.sort(arr, Comparator.comparing(ToBeReferenced::Example3));
        for (int i = 0; i < 5; i++) {
            System.out.println(arr[i].toString());
        }

        //spazio per chiarezza nell'output
        System.out.println();



        //versione secondaria
        System.out.println("\n\nVersione secondaria");
        Arrays.sort(arr, Comparator.comparing(NewType::sum));

        for (int i = 0; i < 5; i++) {
            System.out.println(arr[i].toString());
        }
    }

}
