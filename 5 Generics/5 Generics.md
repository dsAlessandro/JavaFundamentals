# Lista universale

Prendendo ad esempio linguaggi di programmazione di alto livello come Python, vogliamo creare una struttura dati che implementi una Linked List ma con un twist: non vogliamo che questa sia vincolata ad ospitare un tipo di dato definito a priori.

Per comprendere meglio la natura del problema, pensiamo a come una Linked List è implementata in C:

```
struct node_s {
    <type> val;
    struct node_s* next;
};

typedef struct node_s* link;

struct linkedlist_s {
    link head;
    link tail;
    int n;
};

typedef struct linkedlist_s* LinkedList;

```

o qualcosa di simile.

Il punto è il parametro `<type>` nella definizione della `struct node_s`. Quando costruiamo una linked list siamo obbligati a scegliere che tipo di dato mettere al posto di `<type>` (sia questo `int`, `char`, o il più generico `item`), rendendo la lista non-generalizzabile.

# Generics

Introdotti in Java 5, i 'Generics' introducono un livello di parametrizzazione delle strutture dati ulteriore, ancora più flessibile e resistente agli errori del predecessore `Object`.

La soluzione al problema di cui sopra sarebbe stata quella di definire una lista di `Object`, che dunque avrebbe potuto ospitare oggetti di qualunque altra classe. Il problema di questa soluzione è l'eterogeneità dei valori all'interno della lista, che non necessariamente sarebbero stati tutti dello stesso tipo (avremmo potuto avere `String`, `Double` e `Integer` tutti nella stessa lista).

E cosa sarebbe accaduto se questa lista fosse dovuta essere ordinata? Come si sarebbe potuto fare un'ordinamento tra elementi eterogenei?

Nonostante possa essere necessario in alcuni casi avere dati eterogenei all'interno di una struttura dati, questo non è il nostro caso. Come possiamo quindi creare una struttura dati che sia omogenea **e** parametrica? La risposta è _tipizzandola_.

## Tipizzazione / parametrizzazione

Nella definizione di una classe in Java è possibile introdurre un simbolo di parametrizzazione. Facciamo riferimento al file `List.java`, presente nel package `Implementation`: leggiamo la definizione di tale classe:

`public class List<T>`

la presenza del termine `<T>` è ciò che rende la nostra lista parametrica.

`T` è un parametro che chiunque voglia creare una lista dovrà esplicitare, ma che noi -al momento della scrittura della classe / struttura dati- possiamo lasciare 'implicito'.

Noi cioè andiamo a costruire una lista che gestirà elementi di tipo `T`, **qualunque esso sia**. I metodi all'interno della classe, gli iteratori in essa definiti, tutti forniscono e accettano valori di tipo `T`, nonostante questo non sia noto a priori.

Vediamo poi come i due main forniti, presenti nei file `Generics_Integer` e `Generics_Complex` ambi istanzino una lista, ma lo facciano con parametri diversi:
```
List<Integer> L1 = new List();
List<Complex> L1 = new List();
```

## Cosa comporta ciò?

Possiamo immaginare che vengano create al momento dell'esecuzione due classi diverse, identiche alla classe `List` scritta da noi, che però al posto del parametro `T` nel primo caso hanno `Integer` e nel secondo `Complex`.

Consideriamo il metodo:

`public void addElement(T el)`

nella classe `List`.

Questo riceve come parametro un elemento di tipo `T`, che nel primo caso sarà un `Integer` e nel secondo un `Complex`. **Ciò significa che se nella prima lista passassimo come parametro al metodo `addElement` qualcosa che non è un `Integer` il compilatore ci segnalerebbe un errore.**

In questo modo abbiamo ottenuto entrambi gli obiettivi che ci eravamo prefissati:
1) Abbiamo ottenuto una lista 'universale', capace di contenere elementi di ogni tipo
2) Abbiamo reso tale lista forzatamente omogenea, potendo dunque sfruttare ed invocare metodi come `.compareTo` o `.equals` stando sicuri che questi ritornino risultati sensati.

# Extra: la classe Complex

Per rendere la trattazione ancora più completa, oltre all'esempio col tipo `Integer` è stato proposto un esempio con il tipo `Complex` da me implementato nel package `Implementation`. È rilevante osservare come io abbia `@Override`-ato alcuni metodi che `Complex` eredita dalla classe generale `Object`:

```
@Override
public boolean equals(Object o) {
    if (o instanceof Complex) {
        Complex c = (Complex) o;
        return this.Re == c.Re && this.Im == c.Im;
    }

    else {
        return false;
    }
}

@Override
public String toString() {
    return "( " + Re + ", " + Im + " )";
}
```

questi metodi 'generali' (cioè che esistono per qualsiasi classe, alla peggio implementati nella maniera 'standard' dalla classe Object) sono poi utilizzati nella lista per effettuare delle operazioni di stampa e confronti. In questo modo chiunque voglia sviluppare una lista di un tipo non-standard (come io ho fatto con `Complex`), può farlo semplicemente definendo la classe e, a sua discrezione, può decidere se a sua volta `@Override`-are tali metodi o meno.

Per predisporre la classe `Complex` alla possibilità di essere elemento di una lista **ordinata** ho fatto si che questa implementasse l'interfaccia `Comparable<Complex>`.

Suddetta interfaccia, che in termini generali è `Comparable<T>` a sua volta fa uso dei Generics per rendersi universale: chiunque voglia dirsi di tipo `Comparable` deve specificare con che tipo di dato è effettivamente _comparable_ e illustrare come avviene tale comparazione. Nel nostro caso un numero complesso può solo essere confrontato con un altro numero complesso (e non, per dire, con interi, reali o simili). Anche qui il confronto è fatto maniera più o meno arbitraria, e cioè sfruttando il modulo del numero complesso.

In ogni caso, in questa maniera il codice è totalmente generale e controllato, permettendo una maggiore chiarezza e sicurezza.

### E cosa succede se non specifico alcun tipo?

La risposta è semplice ed è: il tipo viene automaticamente rimpiazzato con `Object`.

Se anziché:

`List<Integer> L1 = new List();`

avessi scritto

`List L1 = new List();`

il compilatore lo avrebbe interpretato automaticamente con

`List<Object> L1 = new List();`

rendendo dunque vano l'impiego dei Generics.