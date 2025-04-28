# Interfacce funzionali

Sono interfacce aventi un singolo metodo. Possono essere implementate come interfacce classiche, oppure utilizzando degli shortcut quali lambda expressions e method references.

Esistono alcune interfacce funzionali pre-dichiarate all'interno delle librerie standard Java. Tra queste è di rilevante importanza l'interfaccia funzionale `Comparator`, che definisce un unico metodo, avente la seguente forma:

`public int comapre(Object o1, Object o2)`


Tale interfaccia funzionale è fondamentale poiché permette di costruire algoritmi di ordinamento efficienti e personalizzati. Nel seguito verrà illustrato come implementare suddetta interfaccia funzionale in tre modi differenti.


# Obiettivo

Il nostro obiettivo è quello implementare un algoritmo di ordinamento personalizzato, che ordini dati di tipo `double` in maniera **decrescente** (dal più grande al più piccolo).

Nel file `FunctionalInterfaces.java` è presente un main runnabile che sfrutta tutte le soluzioni di seguito proposte stampando a schermo l'output prodotto da ciascuna di esse.


## Soluzione 1 - implementazione estesa

Nel package `Implementation` creiamo un file chiamato `CustomCompare.java`, nel quale definiamo una classe che implementa l'interfaccia `Compare`:

```
package Implementation;

import java.util.Comparator;

public class CustomCompare implements Comparator {
    ...
}
```

al suo interno definiamo per esteso l'unico metodo dell'interfaccia funzionale:
```
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
```


## Soluzione 1.5 - Classe anonima

Senza dover ricorrere ad una classe creata appositamente per implementare l'interfaccia funzionale, è possibile ricorre a quella che è detta "**classe anonima**", la quale permette di istanziare una classe che implementi l'interfaccia funzionale direttamente dove serve, senza che questa sia prima definita in un file apposito, a patto di specificare il comportamento che il metodo dell'interfaccia funzionale deve avere.

Il codice per fare ciò è presente nel file `FunctionalInterfaces.java` ed è il seguente:

```
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
```

Viene creata una variabile di nome `CustomComparator` (il nome ovviamente è arbitrario), di tipo `Comparator` che viene istanziata con un costruttore per l'interfaccia funzionale `Comparator` (`= new Comparator()`), cosa che normalmente sarebbe impossibile da fare, se non che immediatamente dopo tale operazione, anziché il classico `;`, è presente il corpo di quella che sembra essere una funzione.

Tale funzione altro non è che l'implementazione esplicita dell'unico metodo che l'interfaccia funzionale `Comparator` ha, e dunque il compilatore comprende che questa non è altro che una scorciatoia per ottenere quanto fatto per esteso nella soluzione 1.

Di fatto, seppure implicitamente, sta venendo costruita una classe annidata alla prima di nome `ClasseOriginale.$1`, che implementa l'interfaccia `Comparator`.

In soldoni le soluzioni 1 e 1.5 differiscono per due fattori:
1) La soluzione 1.5 è più rapida da scrivere della 1
2) Nella soluzione 1 è possibile dare un nome alla classe che implementa `Comparator`, nella 1.5 no


## Soluzione 2 - Lambda expressions

È possibile utilizzare una lambda expression per implementare in maniera rapida un interfaccia funzionale all'interno di programma, ancor più velocemente di una classe anonima.

Piuttosto che definire una classe che implementi il body di un metodo appartenente ad una interfaccia funzionale -specialmente se questo è particolarmente corto- è possibile definirlo "al volo" senza tutta la sintassi a contorno.


La sintassi per fare ciò è la seguente:

```
Interfaccia nome_istanza = (p1, p2, ...) -> {
    //corpo della funzione
}
```

Nel nostro caso avremo precisamente:

```
Comparator CustomCompare_lambdaexp = (a, b) -> {
    return ( (Double) b).compareTo( (Double) a );
};
```

(dove è stato utilizzato il metodo `.compareTo` della classe `Double` per comodità)

È inoltre possibile evitare di dover effettuare il cast a `Double` all'interno della lambda expression se questa viene riportata direttamente all'interno del metodo `.sort` della classe `Arrays`, dove normalmente andrebbe riportata l'istanza della classe che implementa l'interfaccia `Comparator`:

```
Arrays.sort(v, (a,b) -> {
    return b.compareTo(a);
});
```

In quanto il compilatore verifica che il tipo degli elementi all'interno del vettore possegga i metodi utilizzati all'interno della lambda expression e, in caso affermativo, effettua automaticamente il cast opportuno.

## Soluzione 3 - Method references

Una ulteriore soluzione è quella di utilizzare una **method reference**.

Per fare ciò è necessario possedere un metodo a cui fare riferimento, cioè un metodo che possa sostituirsi a quello che farebbe il singolo metodo dell'interfaccia funzionale di nostro interesse.

Nel package implementation è presente il file `ToBeReferenced.java` che, oltre ad altri metodi utili per gli argomenti successivi, presenta il metodo `double_compare` che verrà utilizzato per effettuare le comaprazioni all'interno dell'algoritmo di sorting.

Per poterlo utilizzare non ci resta che passarne il riferimento come secondo parametro del metodo `.sort`.

Anche in questo caso il metodo dovrebbe rispettare la stessa forma di quello presente nell'interfaccia funzionale, cioè dovrebbe essere:

`public int comapre(Object o1, Object o2)`

ma se il riferimento ad esso viene inserito direttamente come parametro nella chiamata al metodo `.sort` non è necessario rispettare la dichiarazione di cui sopra a patto che il valore di ritorno sia sempre di tipo int e che il tipo dei parametri sia coerente con quello degli elementi all'interno del vettore.

Nel nostro caso dunque il metodo referenziato è:

```
public static int double_compare(Double a, Double b) {

    //utilizziamo i parametri al contrario per ottenere l'ordinamento inverso
    return b.compareTo(a);
}
```

E la referenziazione viene fatta come segue:

```
...

Arrays.sort(v3, ToBeReferenced::double_compare); 

...
```