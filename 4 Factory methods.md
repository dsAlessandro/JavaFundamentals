# Factory Methods

Sono dei metodi di funzioni che ritornano un'istanza di una classe. Gli elementi stessi della classe sono stabiliti dal factory method.

Negli esempi seguenti utilizziamo come riferimento l'interfaccia funzionale `IntSupplier`.

Vogliamo dunque creare delle classi che implementino tale interfaccia e vogliamo che il singolo metodo dell'interfaccia, `getAsInt`, ritorni la sequenza di fibonacci un intero alla volta.

# Sintassi estesa

Nella nostra App:

```
public class FactoryMethods {
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
        fibonacci temp1 = new fibonacci();
        
        for (int i = 0; i < 10; i++) {
            System.out.println(temp1.getAsInt());
        }
    }
}
```

Ciò ritorna come output:
```
1
1
2
3
5
8
13
21
34
55
```

## Osservazioni chiave
1) La classe `fibonacci` è `static` in quanto non è associata a ciascuna istanza di FactoryMethods. Semplicemente esiste al suo interno e può essere istanziata all'occorrenza.

2) Il vettore di interi `ultimi` non è di tipo `static`, il che significa che ogni qual volta si istanzia un oggetto di classe `fibonacci` questo ha un proprio contatore che riparte dall'inizio.


# Sintassi compatta
In una classe a parte:
```
public class ToBeReferenced {

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

}
```

La classe `ToBeReferenced` ospita un metodo, chiamato `fibonacci` che non riceve parametri. Questo metodo è di tipo `public static`, di fatto è alla stregua di una funzione C.

Il valore di ritorno di suddetto metodo è un `IntSupplier`.

## Cosa succede realmente?
Il compilatore praticamente capisce questo:

Questo metodo deve ritornare un'oggetto di una classe che implementa l'interfaccia funzionale `IntSupplier`. Suddetta classe può presentare degli attributi, che andrebbero specificati ad inizio metodo e nel nostro caso coincidono con il vettore  `int[] ultimi = {1, 1}`. Gli attributi sono però opzionali, l'unica cosa che realmente occorre ritornare è una lambda expression o una method reference che implementi il metodo fondamentale dell'interfaccia funzionale di nostro interesse, ovvero `getAsInt`. Il valore di ritorno, che nell'esempio di cui sopra è una lambda expression, è ciò che verrà eseguito ogni qual volta il metodo `getAsInt` di quella istanza viene chiamato.

Il vettore da noi creato è parte dell'istanza e dunque il riferimento ad esso non viene perso quando viene raggiunta l'istruzione di return.

Nella nostra app dunque vedremo:

```
public class FactoryMethods {

    public static void main(String[] args) throws Exception {

        IntSupplier temp2 = ToBeReferenced.fibonacci();

        for (int i = 0; i < 10; i++) {
            System.out.println(temp2.getAsInt());
        }

    }

}
```

Ciò ritorna come output:
```
1
1
2
3
5
8
13
21
34
55
```

## NOTA BENE
Quando all'interno dei factory methods ci serviamo di attributi che non siano vettori, questi devono essere dichiarati come **final**.

Non è strettamente necessario che la keyword `final` sia utilizzata nella dichiarazione, a patto che l'attributo non venga mai modificato all'interno della lambda expression.

Questo discorso teoricamente vale anche per i vettori, ma di fatto di un vettore salviamo solamente un riferimento che non cambia mai, dunque le modifiche fatte all'interno del vettore non comportano problemi.

In soldoni: se occorre modificare nel corso delle chiamate dei valori, questi devono essere salvati all'interno di vettori.

L'insieme delle variabili locali definite all'interno di un factory method e utilizate poi dalla lambda expression è detto "`closure`" della lambda expression.

# Esempio extra
Come anticipato prima, oltre ad una lambda expression è possibile far ritornare ad un factory method una method reference. Nel nostro caso creiamo un metodo nella classe `ToBeReferenced` chiamato `test` così formato:
```
public static int test() {
        return 12;
    }
```

Procediamo poi ad implementare un factory method che ritorni un riferimento a questo:
```
public static IntSupplier alternative() {

    return ToBeReferenced::test;
}
```

Molto banale ma pur sempre funzionale.

Infine procediamo ad utilizzarlo nella nostra app:
```
public class FactoryMethods {

    public static void main(String[] args) throws Exception {

        IntSupplier temp3 = ToBeReferenced.alternative();

        for (int i = 0; i < 10; i++) {
            System.out.println(temp3.getAsInt());
        }

    }

}
```

Ciò ritorna come output:
```
12
12
12
12
12
12
12
12
12
12
```

Proprio come ci aspettavamo.


# Com'è fatto il metodo .comparing()?

Al netto di tutto ciò, possiamo finalmente "abbozzare" come sia fatto il factory method `comparing` dell'interfaccia `Comparator`:

```
public static Comparator comparing(Function extractor) {
    return (o1, o2) -> {
        Object key_1 = extractor.extract(o1);
        Object key_2 = extractor(o2);

        return ( (Comparable) key_1 ).compareTo( key_2 );
    }
}
```

Cioè riceve in ingresso un oggetto che implementa l'interfaccia funzionale `Function` del quale sfrutta il metodo `extract()`. Per la precisione ciò che fa il metodo `comparing` è fornire la lambda expression da usare per fare i diversi confronti nell'ordinamento. La lambda expression in questione è 'standard', e cioè prevede che dai due oggetti da confrontare venga estratto un valore di tipo `Comparable` (per questo in precedenza è stato detto che il valore di ritorno del metodo passato come parametro deve essere standard, poiché per questi è noto il metodo `compareTo` default), e venga restituito il confronto trai due valori estratti. In base a suddetto valore verranno ordinati nel vettore gli oggetti ricevuti in ingresso.