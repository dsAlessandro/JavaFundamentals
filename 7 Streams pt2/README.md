# Operazioni intermedie

Andiamo ora ad analizzare nel dettaglio tutte (o almeno la maggior parte) delle operazioni intermedie applicabili agli stream

## filter

Come intuibile dal nome, serve a filtrare quali elementi del flusso passano allo step succesivo e quali no. È facilmente intuibile come il parametro da passare al metodo sia un'oggetto che implementa l'interfaccia funzionale `Predicate<T>` avente come singolo metodo `boolean test<T t>`, se il valore di ritorno del metodo a cui viene passato come parametro un elemento del flusso è `true`, l'elemento procede allo step successivo, altrimenti viene filtrato.

Come esempio proviamo a stampare tutti i numeri pari da 0 a 100 ma senza sfruttare il metodo `.iterate()`:
```
IntStream.range(0, 101)
.filter((i) -> {
    return i % 2 == 0;
})
.forEach(System.out::println);
```


## limit

Già visto negli esempi del pacchetto precedente, decreta quanti elementi in totale potranno attraversare questo step. Supponiamo di avere in ingresso tutti i numeri interi da 0 a 1000 ma di voler far passare solo i primi 30 numeri pari. Possiamo farlo come segue:

```
IntStream.range(0, 1001)
.filter((i) -> {
    return i % 2 == 0;
})
.limit(30)
.forEach(System.out::println);
```


## skip

Analogo a `limit`, consiste nel saltare i primi `n` elementi in ingresso al flusso (dove `n` è il singolo parametro del metodo) ma lasciando passare tutti i successivi. Un esempio potrebbe essere analogo al precedente, eccetto che si vogliono stampare solo gli ultimi 15 numeri pari dei 30 prodotti precedentemente:

```
IntStream.range(0, 1001)
.filter((i) -> {
    return i % 2 == 0;
})
.limit(30)
.skip(15)
.forEach(System.out::println);
```

## sorted

Anche questo già anticipato nello scorso pacchetto, immagazzina tutti i numeri che riceve dallo step precedente del flusso per poi ordinarli. Può ricevere un parametro opzionale, ovvero un oggetto di una classe che implementa l'interfaccia `Comparator` per esplicare che criterio di ordinamento utilizzare. Quando questo non è riportato viene utilizzato l'ordinamento naturale. Si comporta precisamente come il metodo `.sort()` della classe `Arrays` e dunque possono essere sfruttate tutte le conoscenze sul factory method `.comparing` viste in precedenza.

```
Stream.of("piacere", "mi", "chiamo", "alessandro")
.sorted()
.forEach(System.out::println);

Stream.of("piacere", "mi", "chiamo", "alessandro")
.sorted(Comparator.comparing(String::length))
.forEach(System.out::println);
```


## distinct

Decisamente autoesplicativo. Raccoglie tutti gli elementi del flusso, ne elimina i duplicati e li fa procedere in uscita. Si osservi che il passaggio è stabile (se la prima istanza della stringa "ciao" originariamente si trovava prima della prima istanza della stringa "panino" dopo l'eliminazione dei duplicati continuerà a valere questa relazione in uscita)

```
Stream.of("Alessandro", "Luca", "Alessandro", "Gennaro", "Michele", "Luca")
.distinct()
.forEach(System.out::println);
```


## map

Forse l'operazione intermedia più importante. Serve a convertire un flusso di un certo tipo di dato `T1` in un flusso di un tipo di dato diverso, diciamo `T2`.

Necessita come parametro un oggetto di una classe che implementi l'interfaccia funzionale `Function<T, R>` con il suo singolo metodo `<R> apply(<T> t)`. Ciascun elemento del flusso di partenza passa per il metodo `apply` che esegue le istruzioni in esso definite e ne ritorna il risultato, tipicamente di un tipo di dato diverso.

Possiamo usare il metodo `.map()` per convertire un flusso di stringhe in un flusso di numeri interi ottenuti prendendo la lunghezza di ciascuna stringa:

```
Stream.of("piacere", "mi", "chiamo", "alessandro")
.map(String::length)
.forEach(System.out::println);
```


# Operazioni terminali

Andiamo ora ad analizzare nel dettaglio tutte (o almeno la maggior parte) delle operazioni terminali applicabili agli stream

## findAny

Restituisce il primo elemento che **cronologicamente** arriva alla fine dello stream. Non per forza è il primo degli elementi ad inizio flusso in quanto se i diversi step sono stati eseguiti in parallelo per più elementi questo non necessariamente sarebbe il primo a finire.
Per esempio se il flusso di partenza era:
```
Stream.of(1, 2, 3, 4, 5, 6)
//serie di passaggi
//...
.findAny();
```

non è detto che l'elemento '1' sarà il primo a completare tutti i passaggi, potrebbe essere l'elemento '2' o '6' e così via. il metodo `findAny` ritornerà il primo che trova **cronologicamente**

## findFirst

Restituisce, di tutti gli elementi che arrivano all'ultimo step (previa eventuali operazioni di filtraggio, limitazione e skip), quello che ordinatamente doveva essere il primo. Riprendendo l'esempio di prima, se l'1 dovesse arrivare allo step sarebbe anche l'elemento ritornato. Altrimenti il 2, poi il 3 e così via.

### N.B.

Se nel mezzo sono stati fatti dei mappaggi e dunque l'1 adesso è un double o una stringa il discorso non cambia. Nel flusso viene preservata la cognizione di ordine.

## min/max

Autoesplicativi, ritornano il minimo o il massimo degli elementi che arrivano allo step terminale.

Quando lo stream è fatto con elementi di una classe (`String`, `Double`, ecc...) è necessario passare come parametro ai metodi `min` e `max` un oggetto di una classe che implementi l'interfaccia funzionale `Comparator`, affinché possa essere espresso un criterio per decretare il massimo o il minimo. Questo non può essere fatto per gli stream di dati primitivi numerici (come `IntStream` o `DoubleStream`) in quanto questi stream sono stati creati per rendere veloci ed efficienti le operazioni su tali tipi di dati. Se si vuole introdurre un criterio specifico occorrerà effettuare un operazione di mapping.

## count

Ritorna il numero di elementi che hanno raggiunto lo step terminale dello stream

## forEach

Ampiamente discusso precedentemente

## (any/all/none)Match

Ricevono come parametro un oggetto di una classe che implementa l'interfaccia `Predicate<T>` il cui unico metodo è `boolean test(<T> t)`.

1) anyMatch: ritorna `true` se almeno un elemento ha ritornato vero per il test
2) allMatch: ritorna `true` se tutti gli elementi hanno ritornato vero per il test
3) noneMatch: ritorna `true` se tutti gli elementi hanno ritornato falso per il test

vien da sé che anyMatch e allMatch sono operatori complementari

## reduce 

È un'operazione che combina tra loro gli elementi dello stream. Riceve come parametro un oggetto di una classe che implementa l'interfaccia `BinaryOperator<T>` il cui unico metodo `T apply(<T> t1, <T> t2)` riceve due elementi consecutivi dello stream ed eseguendo le istruzioni riportate nel metodo ne calcola un terzo che rimpiazzerà i due ricevuti in ingresso.

Supponendo che l'operazione specificata sia quella di somma tra interi, partendo dallo stream:
`1, 2, 3, 4, 5`

Si passerà a:
`3, 3, 4, 5`

Poi a:
`6, 4, 5`

Poi a:
`10, 5`

infine a:
`15`

che è il risultato ritornato.



## collect

L'ultimo metodo terminale di nostro interesse è il metodo `collect`. Similmente a `reduce`, anche questo permette di compattare tutti gli elementi dello stream in un unico risultato, ma in maniera differente.

Il metodo `.collect()` necessita di tre parametri:

1) Il riferimento ad metodo che permette di creare un "accumulatore", vale a dire una classe predisposta da noi per compattare i dati
2) Il riferimento ad un metodo o la descrizione di una lambda expression che specifichi come quell'accumulatore deve compattare i dati
3) Il riferimento ad un metodo o la descrizione di una lambda expression che specifichi come i dati compattati da più collettori possano essere a loro volta ricompattati insieme

Esempio:

```
class Accumulator {
    int sum = 0;
}

int sum = IntStream.range(0, 200)
.collect(

    //primo parametro, il riferimento al metodo che permette di creare l'accumulatore
    Accumulator::new,

    //secondo parametro, una lambda expression che illustra come accumulare i dati
    (A, i) -> A.sum += i,

    //terzo parametro, una lambda expression che illustra come compattare i dati accumulati in
    //diversi accumulatori
    (A1, A2) -> A1.sum += A2.sum
).sum
```

In questo modo verranno creati più oggetti della classe `Accumulator` i quali calcoleranno le somme parziali di elementi dello stream. Questi verranno poi man mano sommati in un unico oggetto di classe `Accumulator` il cui vlaore `.sum` viene salvato nella variabile `sum`, ottenendo la somma complessiva di tutti gli elementi dello stream.


Chiaramente non è necessario perdere completamente traccia degli elementi dello stream: banalmente il nostro accumulatore potrebbe essere una `Collection` nella quale salvare gli elementi dello stream col metodo `.add`.

Poiché questo tipo di operazione è molto frequente in informatica la Stream API di Java fornisce una serie di metodi appositamente per questo scopo. Osserveremo tali metodi nel prossimo pacchetto.


# Tipi di operazioni

Le operazioni possono essere di due tipi:
1) Stateless
2) Stateful

## Stateless

Sono operazioni eseguibili in parallelo, che dipendono esclusivamente dall'elemento corrente e che dunque non richiedono alcun salvataggio in memoria (ex: `map`, `filter`)

## Stateful

Sono operazioni che non dipendono esclusivamente dall'elemento corrente ma anche (in qualche forma) dagli altri e dunque richiedono memoria aggiuntiva. A loro volta si dividono in:

1) Bounded: se la memoria necessaria per l'avanzamento è limitata (ex: `limit`, `skip`, che necessitano solo di un contatore)
2) Unbounded: se la memoria necessaria per l'avanzamento non è limitata ma dipende dal flusso in ingresso (ex: `sorted`, `distinct`, che hanno bisogno di accumulare tutti gli elementi del flusso prima di poter ripartire)