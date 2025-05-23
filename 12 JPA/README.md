# Persistence

Si intende la permanenza dei dati raccolti in runtime all'interno della memoria.
La metodologia più efficiente per fare ciò, soprattutto data la natura intrinsecamente 
relazionale della programmazione ad oggetti, è quella di utilizzare un database.

Con "ORM" (= Object-Relational Mapping) si intende la gestione di basi di dati attraverso
la programmazione ad oggetti.

## JPA

JPA sta per Jakartal Persistance API, è dunque un'interfaccia che permetterà a i nostri
codici Java di (indirettamente) costruire un database e dunque rendere persistenti i dati.

## Entity

Non staremo qui a ripetere quanto già detto nel corso di basi di dati. Ci concentreremo 
prevalentemente sugli aspetti implementativi.

Definiamo una entità con l'annotazione `@Entity` prima di una classe:

```
@Entity
public class Student {
    //code
}
```

Quando una classe viene "proclamata" entità in Java questa deve necessariamente implementare un costruttore privo di parametri (il costruttore per la classe di cui sopra sarà `public Student() {}`). È possibile aggiungere poi altri costruttori con parametri nel caso in cui ci fossero degli attributi della entità da impostare alla sua creazione. L'unico attributo che dovrà essere gestito con maggiore cautela è la chiave primaria, che tratteremo dopo.

Quando non si specifica manualmente il nome per la tabella corrispondente agli oggetti di quella classe
nel database, questo verrà automaticamente fatto coincidere col nome della classe, altrimenti il nome può essere specificato con l'annotazione `@Table(name = "nome_tabella")` subito dopo `@Entity`.

## Attributes

Gli attributi all'interno di una classe vengono automaticamente convertiti nei corrispondenti tipi di dato 
nell'ambito delle basi di dati. È importante **NON UTILIZZARE** tipi di dato primitivi (`int`, `float`, `char`)
in quanto per questi non è ammessa la "nullabilità" (anche se non inizializzati, i valori all'interno delle variabili di questi tipi sono scelti randomicamente).

Poiché nei database esistono anche attributi opzionali, concludiamo che in Java sarà possibile dichiarare
quando un attributo è tale o meno. Per fare ciò però la premessa fondamentale è che un valore possa essere `null`. È per questa ragione che la prassi è di utilizzare solo classi wrapper per i tipi di dato primitivi.

## Chiave primaria

Nella maggior parte dei casi la chiave primaria coindice con un singolo attributo. Quando ci troviamo in questa situazione è sufficiente utilizzare l'annotazione `@Id` prima dell'attributo stesso:

```
@Entity
public class Student {
    @Id
    private Integer matricola;

    //code
}
```

Nel fare ciò però sarà necessario introdurre una "strategia" di generazione del valore.

1) Vogliamo essere liberi di scegliere manualmente la chiave primaria da utilizzare (il numero di matricola)
2) Non ci interessa quale sia il numero e lasciamo che sia il DB a sceglierlo

### 1 - Selezione manuale

Il codice rimane invariato, eccezione fatta per il costruttore: oltre a riportare il costruttore privo di parametri, sarà necessario introdurre un costruttore che riceve almeno la chiave primaria come parametro.

Nel nostro caso avremo:

```
@Entity
public class Student {
    @Id
    private Integer matricola;


    public Student() {}

    public Student(Integer n) {
        this.matricola = n;
    }
}
```

**ATTENZIONE!**

1) Nel caso scegliessimo di operare così, quando un oggetto viene creato nel main, questo non potrà essere salvato nel DB fino a quando qualcuno non ne specificherà la chiave primaria.
2) È nostra premura assicurarci che NON vengano inserite due chiavi primarie uguali per oggetti diversi. Se ciò dovesse accadere il DB solleverà un'eccezione.

### 2 - Selezione automatica

Se non vogliamo dover gestire manualmente la selezione della chiave primaria, possiamo limitarci ad aggiungere una seconda annotazione dopo `@Id`, ovvero `@GeneratedValue(strategy = GenerationType.STRATEGY)` dove il valore di "STRATEGY" va scelto a seconda della strategia che si vuole seguire. I due valori più frequenti sono:

1) `AUTO`, che lascia al DB il compito di scegliere la tecnica più comoda
2) `IDENTITY`, che sfrutta l'autoincrement del database per generare incrementalmente una nuova chiave univoca.

È importante tenere presente che quando si sceglie di affidare al DB il compito di produrre una chiave primaria, questa NON può essere modificata in alcun modo durante il programma. Il tentare di farlo solleverà un'eccezione.

Il codice in questo caso risulterebbe il seguente:

```
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matricola;

    //code
}
```

quando non specifichiamo la strategia viene utilizzato il valore di default "AUTO".

### E se la chiave primaria non è composta da un unico attributo?

Non approfondiremo eccessivamente questo caso in quanto non è di nostro interesse, ma in soldoni la soluzione a questo problema è quella di creare una classe "wrapper" per poter specificare un'unica chiave primaria. Questa deve fornire il costruttore avente per parametri i diversi attributi da utilizzare per la chiave primaria e deve @Override-are i metodi `equals` e `hashCode` per poter verificare l'unicità delle chiavi primarie. La sintassi per fare ciò è la seguente (supponiamo che la chiave primaria sia data da numero di matricola + anno di immatricolazione):

```
@Embeddable
public class studentId implements Serializable {
    private Integer matricola;
    private Integer annoImmatricolazione;

    public studentId() {}

    public sutendId(Integer n, Integer a) {
        this.matricola = n;
        this.annoImmatricolazione = a;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || ! o istanceof studentId)
            return false;

        return this.matricola == o.matricola && this.annoImmatricolazione == o.annoImmatricolazione;
    }

    @Override
    public int hashCode() {
        return Object.hash(matricola, annoImmatricolazione);
    }
}
```

E nella entità avremo:

```
@Entity
public class Student {
    @EmbeddedId
    private studentId id;

    //code
}
```

## Nomi degli attributi

Ovviamente tutti gli attributi della classe che fa da entità verranno poi salvati nel database e a ciascuno di essi corrisponderà un nome. Inoltre, come già anticipato, ogni attributo può presentare dei comportamenti particolare (essere nullabile, essere unico, essere modificabile, ecc...)

Come è dunque possibile specificare tale comportamento per ogni attributo della classe? La risposta è: utilizzando l'annotazione @Column. la sintassi è
```
@Column (
    key1 = value1,
    key2 = value2,
    ...
)
```

elenchiamo quindi i diversi parametri che possono essere impostati:

1) `name = "Città"` -> identifica il nome che l'attributo avrà nella tabella del DB
2) `nullable = false` -> specifica se l'attributo è o non è nullabile
3) `unique = true` -> specifica se è necessario inserire un vincolo di unicità per i valori di questo attributo (ad esempio, pur non essendo questa la chiave primaria, non voglio avere più record aventi la stessa città di origine)
4) `length = 100` -> specifica la lunghezza massima dell'attributo, se questo è una stringa
5) `updatable = true` -> specifica se è possibile o meno modificare in futuro l'attributo
6) `insertable = true` -> specifica se l'attributo è o non è suscettibile all'operazione di INSERT nel database

Vediamo un esempio di utilizzo:

```
@Entity
public class Student {
    @Id
    @Column (
        name = "Matricola",
    )
    private Integer matricola;

    @Column (
        name = "Nome",
        length = 20,
        updatable = true
    )
    private String nome;


    //code
}
```

## @Transient

È un'annotazione utilizzata per specificare che un particolare attributo della classe, pur essendo parte di essa, non deve essere salvato in memoria nel database e dunque non occorre introdurre una colonna per questo

Vediamo un esempio:

```
@Entity
public class Student {
    @Id
    @Column (
        name = "Matricola",
    )
    private Integer matricola;

    @Column (
        name = "Nome",
        length = 20,
        updatable = true
    )
    private String nome;


    @Transient
    private Integer radiceQuadrataDelNumeroDiLettereNelNome

    //code
}
```

Quel `radiceQuadrataDelNumeroDiLettereNelNome` è un attributo probabilmente (?) utile per alleggerire alcune operazioni che verranno fatte con gli studenti, ma non è indispensabile venga salvato in memoria persistente: è facilmente ricavabile sul momento, e pertanto è transient.

## @Enumerated

Sicuramente sarà capitato a tutti di aver utilizzato un tipo `enum` all'interno di una nostra classe (ma quando mai...), ad ogni modo i dati `enum` sono diversi rispetto agli altri e non esiste un modo "standard" per salvarli nel database.

Possiamo quindi "istruire" la nostra entità su come trattare questi tipi di dati nel seguento modo:

```
public enum Posizione {
    Primo, Secondo, Terzo
}
```


```
@Entity
public class Student {
    @Id
    @Column (
        name = "Matricola",
    )
    private Integer matricola;

    @Column (
        name = "Nome",
        length = 20,
        updatable = true
    )
    private String nome;


    @Transient
    private Integer radiceQuadrataDelNumeroDiLettereNelNome

    @Enumerated(EnumType.STRING)
    private Posizione posizionamentoAlleOlimpiadiDiYapping;
    //code
}
```

In questo modo l'annotazione `@Enumerated(EnumType.STRING)` ha illustrato all'entità che di quell'enum va salvato il nome corrispondente al numero, e non il numero stesso. Questo approccio è più solido perché ci permette di modificare eventualmente l'ordine in cui si trovano gli enum oppure di aggiungere nuovi nomi in mezzo agli altri senza problemi di compatibilità.

L'alternativa sarebbe stata usare `@Enumerated(EnumType.ORDINAL)`, che avrebbe salvato il numero associato al dato.


## Relazioni

Dopo quello di entità, il concetto chiave dei database è la relazione. È possibile gestire la presenza di relazioni tra più entità in Java. Per farlo occorrerà identificare il tipo di entità e chiarire come questa andrà resa persistente in memoria.

### "Possedere" la relazione

In una relazione che coinvolge due entità è necessario stabilire quale delle due "possegga la relazione". L'entità che possiede la relazione è quella nella quale definiamo alcuni aspetti fondamentali della stessa.

Le relazioni possono essere di 4 tipi:

1) `OneToOne`
2) `OneToMany`
3) `ManyToOne`
4) `ManyToMany`

In ciascuna delle entità coinvolte nella relazione saranno presenti degli attributi appositamente creati per la relazione stessa, entrambi preceduti dal tipo di relazioni di cui l'entità fa parte.

Per esplorare nel dettaglio tutte le caratteristiche di una relazione cominciamo con il vederle la sintassi e le regole fonamentali.

### Relazioni unidirezionali e bidirezionali

Una relazione è unidirezionale se solo l'entità che la possiede gestisce i riferimenti agli oggetti con cui è in relazione. Generalmente noi lavoriamo con relazioni bidirezionali.

Una relazione è bidirezionale se sia l'entità che la possiede sia quella che non la possiede hanno un qualche tipo di riferimento reciproco. L'entità che non possiede la relazione fa ciò con il campo `mappedBy`, trattato in seguito.

### OneToOne

In questa relazione ogni oggetto di una classe è legato ad un singolo oggetto di un'altra classe, e viceversa, proprio come un individuo è legato ad uno ed un solo documento di identità che a sua volta è valido per un singolo individuo.

Possiamo realizzare questo in Java come:

```
@Entity
public class Individuo {
    @Id
    @GeneratedValue
    private String CF;


    @OneToOne
    @JoinColumn(
        name = "documento_id"
    )
    private Documento carta_identita;


    public Individuo() {}
}

@Entity
public class Documento {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(mappedBy = "carta_identita")
    private Individuo persona;



    public Documento() {}
}
```

N.B: Ovviamente non ha senso che la chiave primaria di entrambe sia generata automaticamente quando questa riguarda l'entità stessa. Il codice fiscale dell'inidividuo non sarebbe né impostabile né modificabile perché scelto dal database. La scelta è stata fatta (e verrà ripetuta) solo per ragioni di leggerezza implementativa.

In questo esempio possiamo osservare i primi tratti fondamentali della creazione di una relazione.

L'entità scelta come "proprietaria" della relazione è `Individuo`, e deduciamo ciò da due fattori:
1) presenta il campo @JoinColumn

    Per comprendere il senso di questo campo dobbiamo capire come la relazione effettivamente sarà salvata all'interno del nostro programma Java. L'idea è quella di aggiungere un ulteriore attributo alla classe `Individuo` che tenga traccia del documento a cui questo è associato. Ciò però a livello di database coincide con l'aggiungere una nuova colonna alla tabella `Individuo`, contenente una foreign-key, ovvero la chiave primaria del `Documento` a cui facciamo riferimento. Il campo fondamentale all'interno di `@JoinColumn` è proprio il campo "`name`", che definisce che nome dare a questa colonna extra nel db.

    Solo il proprietario della relazione può definire questa informazione

2) l'altra entità, `Documento`, presenta il parametro `mappedBy`

    Questo parametro permette di spiegare al database come -retroattivamente- è associato un `Documento` ad un `Individuo`. Una possibile interpretazione è: "il mio contributo nella relazione è tracciato dall'attributo `carta_identita` all'interno dell'altra entità coinvolta nella relazione"

    Solo il non-proprietario della relazione specifica il mappaggio.

È importante ricordare che **ENTRAMBE** le entità conservano delle informazioni sulla clase con cui sono in realzione: sia `Individuo` che `Documento` hanno un attributo coincidente con un oggetto dell'altra classe.


### OneToMany

A differenza di quanto visto prima, in questo tipo di relazione non entrambe le entità presentano la stessa annotazione per definire la relazione, questo perché le due entità non hanno lo stesso **ruolo**.

Se prima era tutto sommato arbitrario decidere chi fosse il proprietario della relazione, adesso la scelta è forzata: delle due entità, **solo quella con cardinalità 1** dovrà gestire la relazione, poiché questa è legata al più ad una sola istanza dell'altra entità, e dunque tutto può essere risolto con un solo attributo.

L'entità che partecipa con cardinalità N invece sarà mappata.

Questa differenza di ruolo introduce un'altra regola: **nella classe relativa all'entità che partecipa con cardinalità N leggeremo l'annotazione `@OneToMany` (One -entità corrente- possiede Many -altra entità nella relazione-), mentre dall'altra parte leggeremo l'annotazione `@ManyToOne`**.

Resta comunque l'imperativo di avere da entrambe le parti un attributo relativo alla relazione. poiché una delle due entità può essere associata a più istanze dell'altra, l'attributo utilizzato per questo scopo dovrà essere una `List` (o una qualsiasi `Collection`). Consideriamo come esempio quello di un ricercatore e dei premi da lui vinti

```
@Entity
public class Ricercatore {
    @Id
    @GeneratedValue
    private String CF;

    //un ricercatore può avere diversi premi -> OneToMany
    @OneToMany(mappedBy = "vinc")
    private List<Premio> premi_ottenuti = new ArrayList<>();



    public Ricercatore() {}
}


public class Premio {
    @Id
    @GeneratedValue
    private String nome_premio;

    @ManyToOne
    @JoinColumn(
        name = "vincitore"
    )
    private Ricercatore vinc;

    
    
    
    public Premio() {}
}
```

Come possiamo vedere in questo esempio, l'entità proprietaria della relazione è `Premio`, in quanto un premio può essere associato solo ad un `Ricercatore`. Il `Premio` dichiara la relazione come `@ManyToOne`, mentre il `Ricercatore` la dichiara come `@OneToMany`, e il `mappedBy` riportato coincide con l'attributo designato a gestire la relazione nella classe `Premio`.

L'attributo presente in Ricercatore per tenere traccia della relazione invece è una `List<Premio>` che viene inizializzata vuota.


### ManyToMany

Nelle relazioni many to many torna ad essere arbitraria la scelta del proprietario della relazione. Riportiamo un esempio per poi commentare le differenze rispetto agli altri tipi di relazioni visti fin ora:

```
@Entity
public class Studente {
    @Id
    @GeneratedValue
    private Integer matricola;



    @ManyToMany
    @JoinTable(
        name = "studente_corso",
        joinColumns = @JoinColumn(name = "studente_id"),
        inverseJoinColumns = @JoinColumn(name = "corso_id")
    )
    private List<Corso> corsiSeguiti = new ArrayList<>();



    public Studente() {}
}



@Entity
public class Corso {
    @Id
    @GeneratedValue
    private String codice;



    @ManyToMany(mappedBy = "corsi")
    private List<Studente> studentiSeguenti = new ArrayList<>();



    public Corso () {}
}
```

Le differenze fondamentali con gli esempi precedenti sono tre:
1) Entrambe le entità hanno come attributo designato alla relazione una `List`

2) Entrambi allocano lo spazio per una `List` 

3) L'entità proprietaria della relazione specifica l'annotazione `@JoinTable` anziché `@JoinColumn`

    Questa differenza è dovuta al fatto che in questo tipo di relazione non è mai sufficiente aggiungere una sola colonna in una delle due entità, proprio perché le colonne dovrebbero essere N da entrambe le parti. Piuttosto il database decide di costruire un'altra tabella a parte contenente le informazioni su questa relazione. I parametri da specificare per il `@JoinTable` sono:

    1) `name = "studente_corso"`, che è il nome della tabella che gestirà la relazione nel database
    2) `joinColumns = @JoinColumn(name = "studente_id")`
    
        La tabella che gestisce la relazione avrà, verosimilmente, due colonne: la prima dedicata allo studente e la seconda dedicata al corso. Entrambe vanno specificate nell'annotazione `@JoinTable` e la prima delle due è definita dal parametro `joinColumns`. 
        
        Per farlo ci serviamo della stessa sintassi vista per le relazioni `OneToMany` e `ManyToOne`: `@JoinColumn(name = "studente_id")`

        In questo modo la prima colonna della tabella, contenente la chiave primaria di uno `Studente`, avrà il nome "studente_id"

    3) `inverseJoinColumns = @JoinColumn(name = "corso_id")`

        Analogamente, il nome della seconda colonna della tabella, quella contenente la chiave primaria di un `Corso`, avrà il nome "corso_id"


### cascade e fetch

Questi sono due parametri da impostare all'interno dell'annotazione relativa alla relazione (`@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany`) che definiscono come il database gestisce la cancellazione di entità e come vengono recuperate le informazioni sulle entità dalla memoria.

1) cascade: definisce come i cambiamenti di stato di una entità coinvolta in una relazione si propagano su tutte le altre entità in relazione con questa. Esistono 6 tipi di valori per questo parametro:

    1) `PERSIST`

        Non abbiamo ancora illustrato come indicare a JPA di salvare definitivamente in memoria una entità, per ora abbiamo solo illustrato come definirla. Ad ogni modo esiste il metodo .persist che vedremo più avanti che si occupa di questo.

        Quando impostiamo il `CascadeType` `'PERSIST'` in una relazione, al momento del salvataggio in memoria di una entità verranno salvate in memoria automaticamente tutte le istanze di classi che sono in relazione con questa entità (ovviamente solo in riferimento alla relazione per cui è stato specificato questo `CascadeType`)

    2) `MERGE`

        Anche in questo caso, ci manca del contesto: una entità resa persistente può essere richiamata dalla memoria di massa e caricata in quella primaria. Possiamo poi decidere di renderla `detached`, ovvero "scollegata" dal database: in questo modo tutte le modifiche che apportiamo ai suoi attributi in questo momento non si propagheranno nella memoria secondaria, che continuerà a salvare la copia originale dell'entità. Potremo poi decidere di effettuare una operazione di `merge`, ovvero di aggiornare la versione salvata in memoria secondaria a quella attualmente presente in memoria primaria.

        Quando impostiamo il `CascadeType` `MERGE` in una relazione, stiamo specificando che nella operazione di `merge` della entità i cambiamenti devono essere riportati anche nelle entità con cui questa è in relazione e che hanno impostato nella relazione questo `CascadeType`.

    3) `REMOVE`

        Quando l'entità viene rimossa dalla memoria, viene fatto lo stesso con tutte le entità con cui questa era in relazione.

        Non viene semplicemente rotta la relazione, ma cancellate tutte le altre entità

    4) `REFRESH`

        Quando una entità viene "ricaricata" (cioè si controlla se la versione in memoria primaria è consistente con quella salvata nel db, che nel frattempo potrebbe essere cambiata in seguito ad operazioni fatte da terzi), vengono ricaricate anche tutte le entità in memoria primaria che sono in relazione con la prima.

    5) `DETACH`

        Quando l'entità viene `detach`-ata dal db, lo stesso viene fatto per le entità in relazione con questa attualmente caricate in memoria primaria

    6) `ALL`

        È un modo rapido per specificare che vogliamo che tutto ciò che abbiamo descritto accada

    
    Un esempio potrebbe essere:

    ```
    ...


    @OneToMany(
        mappedBy = "vinc",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<Premio> premi_ottenuti = new ArrayList<>();
    ```

    Specificando che quando un ricercatore viene caricato nel db, lo stesso va fatto per i premi a lui attualmente associati, e che quando finiamo di modificare un entità `Ricercatore` che era stata `detach`-ata, dobbiamo propagare le modifiche effettuate alle entità `Premio` in relazione con essa.

2) fetch: al caricamento di una entità dal db alla memoria primaria, definisce cosa fare con le entità in relazione con questa. Esistono due tipi di valori:

    1) `LAZY`

        al caricamento di una entità in memoria primaria, non vengono caricate immediatamente le liste di oggetti relative alle relazioni che hanno questo parametro. Queste verrano caricate solo quando sarà necessario utilizzarle

    2) `EAGER`

        al caricamenteo di una entità in memoria primaria, vengono caricate immediatamente le liste di oggetti relative alle relazioni che hanno questo parametro, a prescindere che servano immediatamente o no.


    Un esempio potrebbe essere:
    ```
    ...


    @OneToMany(
        mappedBy = "vinc",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        fetch = FetchType.LAZY
    )
    private List<Premio> premi_ottenuti = new ArrayList<>();
    ```

    stabilisce, oltre a quanto detto per il parametro cascade, che quando un autore viene caricato in memoria primaria **NON** verranno caricati automaticamente anche i suoi premi


Gli attributi fetch e cascade **NON** devono essere **necessariamente** simmetrici per le entità coinvolte nella relazione.

# JPQL

Sta per Java Persistence Query Language, è il "linguaggio" utilizzato per fare delle query nell'ambito ad oggetti di Java. Permette di creare delle "Named Queries", ovvero delle query pre-compilate all'interno delle entità che possono essere invocate senza la necessità di riscriverle da zero.

## Query

Possiamo definire all'interno del codice un oggetto di tipo `TypedQuery<T>`. Per farlo sarà necessario creare un oggetto di classe `EntityManager`, la stessa classe che ci permetterà di iniziare e chiudere transazioni con il database. Possiamo creare una query con i seguenti metodi:

1) `.createQuery(String qlString)` che permette di creare una query in linguaggio JPQL
2) `.createNamedQuery(String name)` che permette di caricare una named query precedentemente definita
3) `.createNativeQuery(String sqlString)` che permette di creare una query in linguaggio SQL
4) `.createNativeQuery(String sqlString, String resultSetMapping)` che permette di eseguire una query SQL mappandone il valore di ritorno su qualcosa che Java è in grado di trattare (entità)

Nella creazione di una query possiamo prenderci la libertà di lasciare in "sospeso" qualche parametro della stessa. Prima di poter eseguire la query dunque sarà necessario impostare univocamente il parametro e per farlo ci serviamo del metodo `.setParameter`, esistente in più versioni (che differiscono per gli argomenti che prendono)

Ad esempio:

```
TypedQuery<Ricercatore> query = em.createQuery("SELECT r FROM Ricercatore r WHERE r.nome = :parametro", Ricercatore.class);

//code

query.setParameter("parametro", "Enrico");

```

In questo modo abbiamo creato una query che cerca i ricercatori aventi un nome specifico, il quale è stato impostato a posteriori tramite il metodo `.setParameter`. Osserviamo che il metodo `.createQuery` ha un secondo parametro oltre alla query stessa, che è `Ricercatore.class`. Questo secondo parametro serve ad indicare al db come convertire il risultato della query in qualcosa che Java possa maneggiare.


Eseguiamo poi la query con i metodi

1) `.getSingleResult()`, da utilizzare se ricerchiamo per chiave primaria e dunque sappiamo che il valore di ritorno sarà unico

2) `.getResultList()`, che ritorna una lista di oggetti del tipo specificato nella definizione della query

3) `.getResultStream()`, che ritorna uno stream di oggetti, piuttosto che una lista

4) `.executeUpdate()`, serve per le query di UPDATE e DELETE, che non hanon come obiettivo quello di ritornare dei dati. In questo caso il valore di ritorno è un intero: il numero di record affetti dall'update/deletion

