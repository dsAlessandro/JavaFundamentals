# Limiti delle Collections

Le collection in java sono utili perché permettono di salvare dati ed accedervi in maniera comoda e funzionale. Ad ogni modo queste hanno dei limiti, che possiamo rapidamente evidenziare con un esempio

## Esempio

Nel package `Implementation` è presente il file `Data.java` nel quale è definita una variabile `static` chiamata `TESTO` che contiene l'intera sigla delle "Winx" sotto forma di grande stringa.

Il nostro obiettivo è quello di stampare, di tutta la sigla, solo le prime 4 parole (in ordine alfabetico e senza duplicati). Vediamo come è possibile approcciare il problema utilizzando le collections:

### Soluzione 1

Per prima cosa occorre salvare le singole parole in una `Collection<String>`, la più comoda in questo caso è un array di String in quanto questo è automaticamente generato quando applichiamo il metodo `.split()` alla stringa contenente il testo:

```
String[] words = Data.TESTO.split("[\n,' ]+");
```

A questo punto, prima di procedere alla stampa delle prime 4 stringhe, è necessario imporre i due vincoli:
1) Le stringhe siano ordinate alfabeticamente
2) Non ci siano duplicati

I modi per fare ciò sono molteplici, ma il più veloce è probabilmente la conversione da array di stringhe a `TreeSet`, una collection citata nello scorso pacchetto. Possiamo effettuare la conversione comodamente con l'istruzione:

```
TreeSet<String> wordsSet = new TreeSet<>(Arrays.asList(words));
```

A questo punto entrambi i vincoli saranno rispettati nella struttura dati `wordsSet` e pertando possiamo procedere con la stampa delle prime 4 stringhe al suo interno con:

```
Iterator<String> it = wordsSet.iterator();
for (int i = 0; i < 4; i++) {
    if (it.hasNext())
        System.out.println(it.next());
}
```

ottenendo quanto ci aspettavamo

### Soluzione 2 - Streams

Un'alternativa a questo processo semplice dal punto di vista logico ma comunque oneroso dal punto di vista delle istruzioni (abbiamo dovuto convertire una struttura dati in un'altra, generande un'iteratore e scriverci un ciclo for sopra appositamente per questo) è quella di utilizzare uno `stream`.

Concretamente tutto si risolve in questa sequenza di istruzioni:

```
Stream.of(words).sorted().distinct().limit(4).forEach(System.out::println);
```

o, se vogliamo maggiore chiarezza dal punto di vista della lettura:

```
Stream.of(words)
.sorted()
.distinct()
.limit(4)
.forEach(System.out:println);
```

Il vantaggio fondamentale di questa soluzione è proprio la chiarezza e l'immediatezza. Se qualcuno dovesse leggere il nostro codice precedente potrebbe avere difficoltà a capire contestualmente ciò che ci eravamo preffisati di fare. Potrebbe non essere chiaro se la scelta di utilizzare un `TreeSet` fosse arbitraria o centrasse con il nostro obiettivo (_ha usato un **Tree**Set perché voleva le stringhe ordinate? Ha scelto un Tree**Set** perché le voleva distinte? Ha scelto un **TreeSet** perché voleva entrambe le cose?_  su cosa, precisamente, va posta l'enfasi?)

L'impiego di uno `Stream` risolve completamente questo problema poiché tutto ciò con cui partiamo è un flusso -stream- di stringhe, privo di qualsivoglia caratteristica, ed è solo dopo che noi (programmatore) introduciamo dei vincoli da rispettare che il nostro flusso assume una forma ben definita.

Dal flusso di stringhe generico abbiamo imposto che queste fossero ordinate (`.sorted()`), dopodiché abbiamo richiesto che fossero distinte (`.distinct()`), poi che ci si fermasse alle prima 4 ottenute **dal flusso risultante** (`.limit(4)`) e infine che per ciascuna di quelle avanzanti avvenisse un'operazione di stampa a schermo (`.forEach(System.out::println)`).

Non c'è spazio per l'interpretazione: chiunque, leggendo questo pezzo di codice, potrebbe benissimo concludere quale fosse la richiesta del problema e nessuono potrebbe obiettare che non stia accadendo quanto richiesto.

# Stream

Come già detto, per Stream si intende un flusso di dati, siano questi stringhe, interi, double e così via.
Gli elementi del flusso scorrono uno per volta, ed è per questo che possiamo applicare comandi come `forEach(System.out::println)` o `limit(4)`, poiché per ogni dato viene effettuato qualcosa (sia questo stamparlo o aumentare il contatore per poi "chiudere" il rubinetto, interrompere il flusso).

Ad ogni modo da un flusso di dati se ne può generare un altro: ricevuto un flusso, possiamo riorganizzarlo e poi farlo ripartire: è il caso di `sorted()`, che accumula i dati provenienti da uno stream, li ordina, e poi li fa ripartire uno per volta sotto forma di nuovo stream, e di `distinct()`, che nuovamente li accumula, cancella i duplicati, e poi li rimanda fuori sotto forma di stream.

È evidente che se in ingresso si ha uno stream di `<T>`, qualunque sia l'operazione intermedia applicata, in uscita si possono avere solo due cose:
1) Un nuovo stream di `<T>`
2) Nulla

Cioè le operazioni possono essere "intermedie", che ricevono uno stream e producono uno stream o "terminali" che ricevono uno stream e ne terminano il flusso.

È chiaro che le operazioni terminali sono sempre le ultime, e l'esempio più concreto di operazione terminale è quella del metodo `forEach`, il quale riceve come parametro qualcosa di analogo a quanto visto per il metodo `.comparing()` della classe `Comparator`, approfondito nel terzo pacchetto:

Più precisamente il metodo `.forEach` si aspetta un oggetto di una classe che implementa l'interfaccia funzionale `Consumer<T>`, il cui unico metodo è il metodo
```
void accept(<T> t)
```

(A differenza del metodo `.comparing()` che si aspettava qualcosa che implementasse l'interfaccia `Function<T, R>`, con il metodo `R apply(<T> t)`)

Pertanto, sempre come visto per il metodo `comparing`, possiamo posizionare al suo interno una lambda expression oppure una method reference coerente con quanto si aspetta il metodo `forEach`.

L'importanza di questo metodo sta nel fatto che ci permette, dopo una serie di operazioni di filtraggio e riorganizzazione, di operare facendo ciò che ci interessa su dei dati raffinati e pronti all'utilizzo.

## Come creare uno stream?

Per creare uno stream a partire da una struttura dati è possibile ricorrere ai seguenti metodi:

1) `Arrays.stream(nome_array)` se abbiamo un array

```
Integer[] arr = {1, 2, 3};
Arrays.stream(arr);
```


2) `nome_collection.stream()` se abbiamo una collection (che sia una group container)
```
TreeSet<Double> set = new TreeSet<>();
for (int i = 0; i < 10; i++) {
    set.add(Double.valueOf(i));
}
set.stream();
```


3) `Stream.of(series_of_arguments / array)` se abbiamo una serie di dati oppure un array
```
Integer[] arr = {1, 2, 3};
Stream.of(arr);

// Same as
Stream.of(1, 2, 3);
```

# Source generation

È possibile voler creare un flusso di dati a partire da una qualche particolare proprietà. Un esempio di flusso di dati potrebbe essere la sequenza di Fibonacci: non vogliamo calcolarne ogni elemento fino all'iterazione n-esima, salvarlo in un vettore e poi convertire questo in un flusso, vogliamo piuttosto che il flusso stesso sia in grado di "determinare" il prossime elemento che deve produrre. È possibile fare ciò o cose simili a questa con particolari metodi.

## generate

Si ha un oggetto che implementa l'interfaccia `Supplier<T>` con il suo singolo metodo `<T> get()` dal quale lo stream otterrà il prossimo elemento del flusso:

```
Stream<Integer>.
```


## iterate

Si fornisce come parametro un "`seed`", vale a dire un valore iniziale, seguito da un'oggetto che implementa l'interfaccia `UnaryOperator<T>` con il suo singolo metodo `<T> apply(<T> t)`. Il `seed` farà da 'innesco', sarà il primo elemento del flusso e ogni successivo elemento verrà calcolato eseguendo il metodo `apply` a cui sarà passato come parametro il precedente.

```
Stream<Integer>.iterate(0, (x) -> {return x+2;});
```

Il flusso sopra descritto conterrà tutti i numeri interi pari da 0 in su, potenzialmente all'infinito (o all'arrivo di un overflow), a meno che non venga inserito un qualche `.limit()`.

### N.B.

Lo stream sopra riportato è uno stream di **Integer**, ma le operazioni fatte al suo interno (somma tra due numeri) sono del tipo `Integer + int` (x + 2), le quali vengono effettuate nei tre passaggi:
1) Conversione di x da Integer ad int (`x.intValue()`)
2) Somma dell'int ottenuto con l'int 2
3) Riconversione dell'int ottenuto in Integer (`Integer.valueOf(x.intValue() + 2)`)

Tutto ciò occupa memoria, è lento ed introduce una sequenza di istruzioni 'extra' che abbassano l'efficienza del flusso. Se occorre lavorare con dati di tipo numerico, poiché non esistono Stream di `int` o `double` o `float` ma solo delle loro corrispondenti classi (`Integer`, `Double`, ecc...) sono stati introdotti degli stream specifici appositamente pensati per questi tipi di dati:
```
DoubleStream
IntStream
LongStream
```
che, oltre ai metodi standard per gli stream, introducono dei metodi aggiuntivi compatibili col fatto che i dati al loro interno sono numerici. I metodi in questione sono, ad esempio, `range(start, end)` e `average()`:

```
OptionalDouble avg = IntStream
                    .iterate(0, (x) -> {return x + 2;})
                    .limit(100)
                    .average();
```

Pet passare da un `IntStream` ad uno `Stream<T>` (cioè uno stream generico di oggetti) è possibile utilizzare il metodo `mapToObject` (esattamente come si farebbe con il metodo `.map` affrontato nel successivo pacchetto) convertendo ciascun `int` nello stream in un qualche tipo di oggetto.



## empty

A differenza degli altri questo metodo non permette la creazione di un flusso con particolari prorietà, piuttosto ne crea uno vuoto.