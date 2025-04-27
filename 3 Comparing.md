# Sorting

Il metodo `.sort` nella classe `Arrays` prevede due parametri:
1) il vettore da ordinare
2) una classe che implementi l'interfaccia funzionale `Comparator` il cui metodo `compare` illustri come ordinare gli elementi del vettore


Come visto nel documento relativo all'ordinamento ciò può essere fatto in più modi.

# Problema

Supponiamo di voler ordinare un vettore di stringhe per lunghezze crescenti.

L'istruzione:
`Arrays.sort(words, String::length); `
non compila perché Arrays.sort si aspetta un `Comparator`, cioè qualcosa che abbia al suo interno una funzione che accetta due parametri e ritorna un intero. `length()` è un metodo all'interno di String che non riceve parametri (lavora sulla variabile implicita `this`) e ritorna un intero. Possiamo però comunque usare tale funzione per ordinare un array di String.

# Soluzioni convenzionali

Ci sono diveri metodi per risolvere il problema, procediamo ad elencarli




## Metodo 1
Costruiamo una classe chiamata `OrderByLength` che implmenti l'interfaccia funzionale `Comparator` e facciamo in modo che il suo metodo `compare` faccia ciò che vogliamo.

## Metodo 2
Procediamo in maniera analoga a prima, ma risolvendo tutto con una classe anonima

## Metodo 3
Ci limitiamo ad utilizzare una lambda expression


# Comparator.comparing()

La semplice istruzione
`Arrays.sort(words)`
Ordina sì il vettore, ma lo fa secondo l'ordinamento "naturale" dello stesso, che nel caso delle stringhe è quello alfabetico.

Nella classe `ToBeReferenced` viene proposto un metodo chiamato `Example1` il quale non fa altro che ritornare la stringa che riceve come parametro. Questo metodo può essere usato in combinazione con il metodo `comparing` dell'interfaccia funzionale `Comparator`.

Suddetto metodo riceve un unico parametro, una method reference, ad un metodo statico che riceve in ingresso dati dello stesso tipo di quelli contenuti nel vettore da ordinare e ritorna un tipo di dato primitivo (`int, String, double, float`, ecc...). Associato a ciascun elemento del vettore suddetto dato primitivo, procede poi ad ordinare tali valori secondo il loro ordinamento naturale. Tale ordinamento sui valori estratti si ripercuote sui valori originali.

In soldoni, le due istruzioni:

`Arrays.sort(words);`
`Arrays.sort(words, Comparator.comparing(ToBeReferenced::Example1));`

per come è fatto il metodo Example1, sono completamente identiche.


# Soluzioni avanzate

## Metodo 4
Sfruttando il metodo `.comparing()` di cui sopra, implementiamo nella classe `ToBeReferenced` un nuovo metodo, detto `Example2`, che riceve come parametro una stringa per poi ritornarne la lunghezza.

Stando a quanto detto prima, refereziandolo come parametro di `Comparator.comparing()` ad ogni stringa del vettore sarà associata la sua lunghezza (un valore intero) e queste lunghezze saranno ordinate secondo l'ordinamento naturale di quel tipo di dati, che in questo caso è l'ordinamento crescente. Questo si ripercuote sul vettore originale fornendoci esattamente ciò che volevamo.

## Metodo 5
Un altro metodo ancora per ottenere lo stesso risultato è quello di referenziare, all'interno di `Comparator.comparing()`, direttamente il metodo `String::length`.

Ciò pare contraddittorio, in quanto il metodo `.length()` non riceve alcun parametro, diversamente da come siamo abituati a vedere nei metodi referenziati in questo contesto.

Questa è un ulteriore possibilità che suddetto metodo fornisce: possiamo referenziare un metodo _non statico_ della classe di cui fanno parte gli elementi del vettore. Facendo ciò suddetto metodo verrà chiamato su ogni elemento del vettore e il valore estratto -in questo caso un intero- sarà quello utilizzato per l'ordinamento.

# Esempio bonus
Viene infine fornita una nuova classe, detta `NewType`. Vogliamo ordinarla per somma dei termini crescenti. Per fare ciò proponiamo due versioni:

## Versione standard
ove referenziamo un metodo presente nella classe `ToBeReferenced`, detto `Example3` che estrae tale somma dall'oggetto ricevuto in ingresso

## Versione secondaria
ove un metodo _non statico_ della classe `NewType`, detto `sum`, svolge la stessa funzione sul parametro implicito `this`.


Si nota che le due versioni forniscono il medesimo risultato.