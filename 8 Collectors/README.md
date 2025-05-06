# Collectors

È una classe standard Java che introduce una serie di metodi statici da abbinare al metodo `.collect` degli stream

## Summarizing collectors

Il loro scopo è quello di compattare "riassuntivamente" i dati all'interno dello stream. Con "riassuntivamente" si sottointende la perdita delle informazione sui dati specifi contenuti nello stream, ad esempio la somma di tutti gli elementi nello stream -per quanto possa servire e dunque sia necessaria- comporta la perdita delle informazioni relative a quali elementi l'abbiano effettivamente composta

### counting

ritorna un dato di tipo `long` corrispondente al numero di elementi che sono arrivati fino a questo step

### maxBy / minBy

ritorna un dato di tipo `<T>`, dove `<T>` è il tipo di dati contenuti nello stream allo step precedente. Necessita come parametro un oggetto di una classe che implementa l'interfaccia funzionale `Comparator` per stabilire quale sia il massimo o il minimo.

### summingType

dove `Type` può essere `int`, `long` o `double`.

Riceve come parametro un oggetto di una classe che implementa l'interfaccia funzionale `Function<T, R>`, il cui unico metodo `<R> apply(<T> t)` viene applicato agli elementi dello stream estraendone un `int`, `long` o `double`, a seconda del campo `Type`.

Se lo stream è già di dati di questo tipo il parametro non è necessario.

Il valore di ritorno sarà la somma di tutti i valori estratti


### averagingType

dove `Type` può essere `int`, `long` o `double`.

Riceve come parametro un oggetto di una classe che implementa l'interfaccia funzionale `Function<T, R>`, il cui unico metodo `<R> apply(<T> t)` viene applicato agli elementi dello stream estraendone un `int`, `long` o `double`, a seconda del campo `Type`.

Se lo stream è già di dati di questo tipo il parametro non è necessario.

Il valore di ritorno sarà la media di tutti i valori estratti

### summarizingType

dove `Type` può essere `int`, `long` o `double`.

Riceve come parametro un oggetto di una classe che implementa l'interfaccia funzionale `Function<T, R>`, il cui unico metodo `<R> apply(<T> t)` viene applicato agli elementi dello stream estraendone un `int`, `long` o `double`, a seconda del campo `Type`.

Se lo stream è già di dati di questo tipo il parametro non è necessario.

Il valore di ritorno sarà un `TypeSummaryStatistics`, ovvero un oggetto di una classe con a disposizione i seguenti metodi:

1) `getCount`, restituisce il numeri di elementi in quello step dello stream
2) `getSum`, restituisce la somma di tutti gli elementi in quello step dello stream
3) `getMin`, restituisce il minimo di tutti gli elementi in quello step dello stream
3) `getMax`, restituisce il massimo di tutti gli elementi in quello step dello stream
3) `getAverage`, restituisce la media degli elementi in quello step dello stream


### toList

restituisce una `List<T>` contenente gli elementi in quello step dello stream

### toSet

restituisce una `Set<T>` contenente gli elementi in quello step dello stream


### toCollection

analogo a `toList` e `toSet`, ma con la differenza che come parametro si aspetta un oggetto che implementi la classe `Supplier<T>`, il cui unico metodo `<T> get()` restituisce una `Collection` specifica in cui salvare i dati contenuti nello stream

### joining 

Funzionante solo per `Stream<Stirng>`, concatena tutti gli elementi a quello step dello stream tra loro.
Accetta tre parametri opzionali:
1) un separatore tra un stringa e l'altra
2) un prefisso alla stringa complessiva
3) un suffisso alla stringa complessiva

## Grouping collectors

Il loro scopo è quello di restituire una `Map` costruita dallo stream

### groupingBy

Necessita come parametro un oggetto di una classe che implementa l'interfaccia `Function<T, K>` detto "classificatore". Lo scopo del classificatore è quello di applicare l'unico metodo dell'interfaccia, ovverro `<K> apply(<T> t)` sugli elementi dello stream estraendone un valore che farà da chiave. A ciascuna chiave corrisponde un "Downstream collector", di default si tratta di una lista, e l'elemento che ha prodotto quella chiave viene aggiunto alla lista corrsipondente.

Precisamente avviene questo:

1) Ogni elmento dello stream passa per il classificatore
2) Il risultato del classificatore (la chiave) sperara gli elementi in **più stream diversi**
3) Ciascuno di questi implicitamente termina con l'istruzione `.collect(Collectors.toList())`
4) Ognuna delle liste prodotte viene aggiunta ad una `Map<K, List<T> >`

Quando diciamo che è possibile specificare un "downstream collector" ci riferiamo a cosa precisamente viene passato come parametro al metodo `.collect()` illustrato nel punto 3, al posto di `Collectors.toList()`. Vediamolo con un esempio:

#### Esempio

Partiamo da uno stream di stringhe e decidiamo di raggruparre tali tali stringhe per la loro lunghezza. L'operazione di `groupingBy` dunque ritornerà una `Map< int, List<String> >` che associa ad ogni intero (= lunghezza della stringa) le parole lunghe quanto quell'intero.

Sotto forma di codice è qualcosa del genere:

```
Map<Integer, List<String> > M =
Stream.of(lyrics)
.distinct()
.collect(Collectors.groupingBy(String::length));
```

Supponiamo poi di voler semplicemente contare quante parole abbiano una certa lunghezza, piuttosto che salvarle in liste. Rimanendo coerenti con quanto "immaginato" nell'elenco di step precedentemente riportato, immaginiamo di avere diversi stream (uno per ogni lunghezza) e concludiamo che banalmente possiamo calcolare il numero di elementi per ciascuna lunghezza con `.collect(Collectors.counting())`, ottenendo dunque qualcosa del tipo:

```
Map<Integer, Long> M2 =
Stream.of(lyrics)
.distinct()
.collect(Collectors.groupingBy(String::length, Collectors.counting()));

for (Map.Entry<Integer, Long> e : M2.entrySet()) {
    System.out.println("Lunghezza : " + e.getKey() + " -> " + e.getValue());
}
```


È possibile esplicitare come secondo parametro un supplier (vale a dire un metodo che ritorni una mappa) per ottenere come risultato non una `HashMap`, che è la mappa di ritorno predefinita, ma una mappa di proprio interesse (ad esempio una `TreeMap`), cioè fare qualcosa del genere:

```
Map<Integer, Long> M3 =
Stream.of(lyrics)
.distinct()
.collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.counting()));

for (Map.Entry<Integer, Long> e : M3.entrySet()) {
    System.out.println("Lunghezza : " + e.getKey() + " -> " + e.getValue());
}
```


### partitioningBy

È un caso specificiale del `groupingBy`, dove la mappa è necessariamente di tipo `Map<Boolean, ...>` il primo parametro è un oggetto di una classe che implementa l'interfaccia `Function<T, Boolean>`, in grado di verificare se un elemento dello stream rispetta o meno un predicato. Tutti quelli che lo rispettano risponderanno alla chiave `true`, tutti quelli che non lo rispettano risponderanno alla chiave `false`


### collectingAndThen

Permette di effettuare una ulteriore operazioni di mapping ai risultati prodotti. Per comprendere meglio, ricapitoliamo brevemente cosa accade (viene dato per scontato il `Collectors.` di fronte ai suoi metodi):

1) Si arriva allo step finale dello stream, un `.collect()`
2) Si utilizza il metodo `groupingBy()`
3) Si specificano i parametri

nel nostro caso esclusivamente il metodo di estrazione della chiave e il tipo di collettore da associare alle diverse chiavi, arriviamo quindi a qualcosa del tipo:

```
.collect(
    groupingBy(
        clasifier,
        downstream 
    )
)

```
Rimaniamo coerenti con l'esempio di prima, di estrarre come chiave la lunghezza della stringa, si ha:


```
.collect(
    groupingBy(
        String::length,
        downstream 
    )
)
```

Aggiungiamo dunque un twist: piuttosto che semplicemente contare il numero di elementi per ogni stream prodotto, e dunque ottenere un risultato `long`, vogliamo che il valore di ritorno sia una `String` contenente il numero al suo interno. Questa è quella che chiamiamo **ulteriore operazione di mapping**, prima di inserire l'elemento nella mappa corrispondentemente alla chiave estreatta, lo modifichiamo un'ultima volta. Arriviamo quindi a

4) specifichiamo il tipo di downstream di nostro interesse. Se questo è un downstream standard semplicemente invochiamo il corrispondente metodo della classe `Collectors`, altrimenti procediamo con `collectingAndThen()`, ottenendo qualcosa del tipo:

```
.collect(
    groupingBy(
        String::length,
        collectingAndThen(
            first_param,
            second_param
        ) 
    )
)
```

dove il `first_param` è il collettore da cui partiamo, nel nostro caso `counting()`, mentre il secondo deve essere un oggetto di una classe che implementa l'interfaccia `Function<long, String>`, poiché `counting()` ritorna un `long` e noi vogliamo convertirlo in una `String`


Il risultato finale pertanto sarà:

```
.collect(
    groupingBy(
        String::length,
        collectingAndThen(
            counting(),
            (l) -> l.toString
        ) 
    )
)
```


### mapping

Svolge lo stesso identico ruolo di `collectingAndThen` ma invertendo l'ordine con cui vengono effettuate le operazioni.

Immaginiamo che il classificatore sia stato eseguito, e dunque il singolo stream sia stato diviso in più stream, ciascuno corrispondente agli elementi aventi una specifica chiave. A questo punto ciò che il metodo `mapping` ci permette di fare è:

1) esplicitare una `Function<T, R>` che mappa ciascun elemento di ciascuno stream a qualcos'altro, secondo quanto specificato dal metodo `<R> apply(<T> t)` dell'interfaccia, applicato ad ogni elemento dei vari stream
2) esplicitare un grouping collector, come `counting()`, `sum()`, ecc...

È evidente che se il collettore esplicitato è `counting` l'operazione di mapping è fondamentalmente inutile. È altresì vero che con la conversione (ad esempio) di stringhe in interi è possibile sfruttare metodi come `sum` o `summarazingType`, ottenendo risultati specifici.


## Custom collectors

Come anticipato nello scorso pacchetto, è possibile implementare il proprio Collector personalizzato. Quella dei collector difatti è un'interfaccia:

`Collector<T, A, R>`

dove `<T>` è il tipo di dato che ci aspettiamo nello stream, `<A>` è l'accumulatore utilizzato per il collector e `<R>` è il risultato che vogliamo ottenere da questo (per `.counting()` è un `long`)

In quanto interfaccia presenta dei metodi da implementare:

1) `Supplier<A> supplier()`: fornisce un oggetto di una classe che implementa l'interfaccia `Supplier<A>`, avente un unico metodo: `<A> get()`

2) `BiConsumer<A, T> accumulator()`: fornisce un oggetto di una classe che implementa l'interfaccia `BiConsumer<A, T>`, avente un unico metodo: `void accept(<A> a, <T> t)` il quale deve inserire l'elemento `t` nell'accumulatore `a`

3) `BinaryOperator<A> combiner()`: fornisce un oggetto di una classe che implementa l'interfaccia `BinaryOperator<A>`, avente un unico metodo: `<A> apply(<A> a1, <A> a2)` il quale combina l'accumulatore parziale `a1` con l'accumulatore parziale `a2` ritornando un accumulatore complessivo (potenzialmente riciclando uno dei due)

4) `Function<A, R> finiser()`: fornisce un oggetto di una classe che implementa l'interfaccia `Function<A, R>`, avente un unico metodo: `<R> apply(<A> a)` il quale estrae dall'accumulatore `a` un valore di tipo `<R>`, il risultato dell'operqazione di `collect`

5) `Set<Characteristics> characteristics()`: fornisce un `Set<Characteristics>` con all'interno dei dati di tipo `Characteristics` di nostro interesse


Onde evitare di dover creare una classe che implementi l'interfaccia `Collector`, è possibile utilizzare il factory method `Collector.of` passando come parametri delle lambda expression / delle method reference coerenti con quanto richiesto sopra.


Esistono tre tipi di dati `Characteristics`, che sono:
1) `IDENTITY_FINISH` che specifica il fatto che il risultato del `.collect` è l'accumulatore stesso e dunque non è necessaria alcuna operazione di `finish`

2) `CONCURRENT` che specifica il fatto che l'operazione di inserzione di un elemento in un accumulatore (`accumulator()`) può essere eseguita contemporaneamente su più elementi per uno stesso contenutore (`<A> a`)

3) `UNORDERED` che specifica il fatto che l'ordine degli elementi nello stream può essere trascurato (se nello stream si aveva 1 -> 2 -> 3 -> ... è importante che si immagazzini prima 1, poi 2, poi 3 in questo preciso ordine)

Se sia `CONCURRENT` che `UNORDERED` sono contemporaneamente vere, in presenza del metodo `.parallel()` verrà generato un unico accumulatore e non sarà mai necessario usare il metodo `combine()`