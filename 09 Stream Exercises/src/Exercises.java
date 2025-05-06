import Implementation.ToBeReferenced;

public class Exercises {
    public static void main(String[] args) throws Exception {
        String[] lyrics = Implementation.Data.TESTO.split("[ ,.\n']+");


        System.out.println("\nExercise 1");
        ToBeReferenced.Ex1(lyrics);
        System.out.println("\n\n"); 


        System.out.println("\nExercise 1 (alt)");
        ToBeReferenced.Ex1_alt(lyrics);
        System.out.println("\n\n"); 


        System.out.println("\nExercise 2");
        ToBeReferenced.Ex2(lyrics);
        System.out.println("\n\n"); 
        System.out.println("\n\n"); 


        System.out.println("\nExercise 3");
        ToBeReferenced.Ex3(lyrics);
        System.out.println("\n\n"); 
        System.out.println("\n\n"); 


        System.out.println("\nExercise 3 (alt)");
        ToBeReferenced.Ex3_alt(lyrics);
        System.out.println("\n\n"); 
        System.out.println("\n\n"); 


        System.out.println("\nExercise 4");
        ToBeReferenced.Ex4(lyrics);
        System.out.println("\n\n"); 




    }
}
