# Collections

Nel progetto precedente abbiamo costruito una Linked List capace di ospitare elementi di qualsiasi tipo. Di questa abbiamo poi costruito una seconda versione, chiamata "Sorted List", in grado di ospitare elementi di qualsiasi tipo ordinatamente, a patto che questi implementassero l'interfaccia `Comparable`.

Il limite di questo progetto è che la nostra è -appunto- una linked list, con tutti i vantaggi e gli svantaggi che tale scelta implementativa comporta.

Siamo riusciti a costruirla in maniera relativamente facile dunque è altrettanto semplice concludere che gli sviluppatori del linguaggio Java hanno potuto fare cose ben più complesse e comode di quanto siamo riusciti a fare noi.

Suddetti sviluppatori ci forniscono le `Collections`. `Collection` è un tipo in grado di contenere elementi, questo infatti implementa tutti i metodi propri di una struttura dati alla stregua di una linked list (ad esempio `.add()`, `.remove()`, `.removeIf()`, ecc...)

Ovviamente anche le `Collections` sono tipizzate, e possiamo dunque scrivere cose come:

```
Collection<Integer> c1 = ...
Collection<Complex> c2 = ...
Collection<String> c3 = ...
```
e così via.

`Collection` se vogliamo è un'interfaccia: non possiamo istanziare un collector generico, però possiamo usarlo come tipo quando non sappiamo quale tipo di collector verrà utilizzato ma vogliamo comunque poterlo ospitare in una variabile (un po' come Object può accogliere String o Integer)

Esiste una serie di classi tipizzate, tutte implementanti l'interfaccia `Collection`, che forniscono le operazioni proprio dell'ADT a cui fanno riferimento con i tempi più efficienti possibili.

Queste classi si dividono in due macro-categorie:

1) I `Group containers`
2) Gli `Associative containers`

## Group containers

Sono `Collections` che implementano l'interfaccia `Iterable`, permettono quindi di iterare sui loro elementi con il costrutto `for each`. I group containers sono fondamentalmente tre:

1) `Set<E>`
2) `Queue<E>`
3) `List<E>`

Dal nome dell'ADT a cui fanno riferimento possiamo apprezzare le **funzioni** che questi collector forniscono. Ad ogni modo quelle sopra elencate rimangono delle interfacce: le effettive classi da instanziare garantiscano le funzioni dell'interfaccia che implementano ma lo fanno con strategie implementative diverse, a seconda di ciò che il programmatore deve ottenere.


### Set

Può essere istanziato come:

- `Set<Integer> S = new HashSet<>();`
- `Set<Integer> S = new LinkedHashSet<>();`


Un'altra sotto-interfaccia 'di `Set<E>` è `SortedSet<E>`, che può essere istanziata solo come:

- `SortedSet<Integer> S = new TreeSet<>();`

(un container di tipo `Set` può contenere un oggetto che implementa un `SortedSet`)

E conesente di tenere in ordine gli elementi inseriti nel set.


### Queue

Può essere istanziato come:

- `Queue<Integer> S = new LinkedList<>();`
- `Queue<Integer> S = new ArrayDeque<>();`
- `Queue<Integer> S = new PriorityQueue<>();`


### List

Può essere istanziato come:

- `List<Integer> S = new LinkedList<>();`
- `List<Integer> S = new ArrayList<>();`






## Associative containters

Sono `Collections` che non l'interfaccia `Iterable`, ma che di fatto implementano i 'dizionari': permettono l'associazione di una chiave ad un valore (infatti la loro tipizzazione prevede due parametri). Le interfaccie disponibili sono:

1) `Map<K, V>`
2) `SortedMap<K, V>`


Non implementanto l'interfaccia `Iterable`, permettono lo scorrimento dei propri elementi in tre modalità diverse:

1) `.keySet()`, che ritorna un set contenente tutte le chiavi esistenti nel dizionario
2) `.values()`, che ritorna tutti i valori salvati all'interno del dizionario
1) `.entrySet()`, che ritorna un set contenente tutte le coppie chiave : valore

Il tipo di dato salvato nella collezione ritornata dal metodo `.entrySet()` è `Map.Entry<K, V>`, con `<K, V>` che sono gli stessi del dizionario.
 
Un oggetto di tipo `Map.Entry<K, V>` possiede due metodi:
```
public K getKey();
public V getValue();
```

da utilizzare per ottenere i due dati di interesse

### Map

Può essere istanziata come:

- `Map<String, Integer> S = new HashMap<>();`
- `Map<String, Integer> S = new LinkedHashMap<>();`


### SortedMap

Può essere istanziata come:

- `SorterdMap<Integer> S = new TreeMap<>();`


Di fatto `SortedMap<K, V>` è una sotto-interfaccia di `Map<K, V>` dunque una `Map` può contenere una `SortedMap`.