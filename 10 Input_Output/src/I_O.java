import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class I_O {
    public static final int EOF = -1;


    public static void main(String[] args) throws Exception {
        String S = LetturaFile("input.txt");
        System.out.println(S);
    }





    public static String LetturaFile(String filename) throws IOException {
        StringBuilder S = new StringBuilder(); 


        try (Reader r = new FileReader(filename)) {
            char[] buffer = new char[8];
            int n;

            while ( (n = r.read(buffer)) != EOF) {
                /*
                 * Per appendere tanti caratteri quanti ne ho letti ricorro agli offet per l'append:
                 * first_param = buffer
                 * second_param = indice (incluso) da cui iniziare la ricopiatura
                 * third_param = indice (escliso) in cui terminare la copiatura
                 */
                S.append(buffer,0, n);
            }
        }
        
        return S.toString();
        
    }
}
