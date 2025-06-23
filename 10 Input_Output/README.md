# I/O Stream

L'input e l'output in Java avvengono abbracciando il concetto di stream, cioè di flusso di dati. Ad ogni modo gli stream di input/output sono implementativamente diversi dagli stream visti nei pacchetti precedenti.

## Tipi di Stream

Si trovano nel package `java.io` e possono essere di due tipi:
1) Stream di caratteri
2) Stream di byte

Nel primo caso le classi a cui facciamo riferimento sono `Reader` e `Writer`, nel secondo `InputStream` e `OutputStream`.

Tutte le eccezioni generate da questi stream rientrano nella classe delle `IOException`

Supponiamo di avere a disposizione il numero esadecimale C3A8, ovvero 50088 in decimale. Questo può essere intrepretato come sequenza di byte, ovvero come [C3] [A8], oppure come un carattere unicode, che nella codifica standard UTF-8 ha dimensione 16 bit, ovvero 2 byte, dunque come singola entità [C3A8]. A seconda del tipo di stream i dati verranno inviati un byte per volta oppure verranno effettuate delle operazioni di codifica / decodifica per raggruppare gli elementi due byte alla volta a tradurli nei simboli che vediamo a schermo.

## Reader

È di fatto una classe astratta, un'interfaccia, ma la libreria standard Java ci fornisce diverse sue implementazioni, ciascuna adatta a specifiche circostanze. Alcuni tra questi sono:

1) `BufferedReader`, che legge da un buffer
2) `CharArrayReader`, che legge da un array di caratteri
3) `FilterReader`, che fornisce operazioni di lettura più avanzate, come quella di leggere una serie di caratteri e riposizionari nella sorgente immediatamente dopo la lettura (utile ad esempio per implementare il metodo `.find` della classe `Matcher` per le espressioni regolari)
4) `InputStreamReader` permette di leggere da input. A sua volta è generalizzazione di un'altra classe, ovvero `FileReader`, che permette la lettura da file
5) `PipedReader`, che legge da pipe
6) `StringReader`, che legge da stringa

Ciascuno di questi metodi implementa alcuni metodi fondamentali, ovvero:

- `void close()`
- `int read()`
- `int read(char[] buff)`
- `int read(char[] buff, int start, int end)`
- `boolean ready()` (serve a verificare che lo stream, potenzialmente stabilitosi in una rete di calcolatori, sia pronto alla lettura)
- `void reset()`, fa ricominciare lo stream da capo
- `long skip(long n)`, salta i prossimi `n` caratteri

la read, come ci si poteva aspettare, ritorna il **numero di caratteri** letti dallo stream.


## Esempio di lettura da file

È riportato un codice esempio di codice, visualizzabile per esteso nel file `I_O.java`, che effettua una lettura di file. È riportata sotto di questo la sua analisi

```
StringBuilder S = new StringBuilder(); 


try (Reader r = new FileReader(filename)) {
    char[] buffer = new char[8];
    int n;

    while ( (n = r.read(buffer)) != EOF) {
        
        S.append(buffer,0, n);

    }
}

return S.toString();

```


Partiamo dalla prima istruzione di interesse: la `try`. Normalmente ci aspettiamo un costrutto del tipo:

```
try {
    ...
}

catch (Exception e) {
    ...
}
```

Ad ogni modo è possibile utilizzare quella che è detta "`try-with-resources`", vale a dire una try che contiene al suo interno la generazione di un oggetto di una classe che implementa l'interfaccia `Closeable` (questo perché la classe `FileReader`, oltre ad implementare la classe astratta `Reader`, implementa anche l'interaccia `Closeable`). Questa particolare try non richiede alcun catch perché il catch implicito è:

```
catch (FileNotFoundException e) {
    r.close();
}
```

in quanto l'interfaccia `Closeable` è funzionale e il suo unico metodo è `void close()`.

Dopo di che avviene la creazione di un buffer nel quale salvare ciò che viene letto, affinché si possa riportare il risultato della lettura sotto forma di stringa al chiamante. Il buffer per la lettura è stato fatto (arbitrariamente) di 8 caratteri. Ad ogni modo la concatenazione di quanto letto una `read` alla volta è in mano ad un oggetto di classe `StringBuilder`, che continua ad `append`-ere il buffer ad ogni lettura andata a buon fine.

Difatto l'operazione di read ritorna un intero, salvato nella variabile `n`, che rappresenta il numero di caratteri letti. Quando, inevitabilmente, si arriva a fine file il numero di caratteri letti è precisamente `-1`, costante da noi nominata `EOF`. Nell'attesa di arrivare a quel punto vengono letti `n` caratteri che poi sono concatenati allo `StringBuilder` tramite il metodo `append` che può ricevere 1 o 3 parametri:
1) il buffer, che è l'unico parametro nel caso si opti per la versione a singolo parametro. Quando si opta per tale versione l'intero array viene concatenato alla stringa, anche se dalla lettura precedente solo alcuni degli elementi sono cambiati:

supponiamo di aver letto: `egno di `, 8 caratteri all'ultima riga, e di aver appeso ciò allo `StringBuilder`. La successiva lettura permetterà di leggere solo `Winx`, 4 caratteri, gli ultimi del file. Ciò significa che il buffer assumerà questo contenuto: `Winx di `, poiché i restanti 4 caratteri non sono stati sovrascritti. La variante a singolo parametro della `append` riporterà nello `StringBuilder` complessivamente `egno di Winx di `, e non `egno di Winx`, come ci auguravamo.

2) l'indice (incluso) dal quale cominciare a copiare i caratteri dell'array
3) l'indice (escluso) col quale terminare la copiatura

Ovviamente per correttezza logica nella codice è stata utilizzata la versione a 3 parametri.


## Writer
