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