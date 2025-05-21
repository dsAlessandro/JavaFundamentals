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

Quando una classe viene "proclamata" entità in Java questa non può ammettere costruttori
con parametri (il costruttore per la classe di cui sopra sarà `public Student() {}`) e può,
eventualmente, essere omesso se non indispensabile.

Quando non si specifica manualmente il nome per la tabella corrispondente agli oggetti di quella classe
nel database, questo verrà automaticamente fatto coincidere col nome della classe.

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

Il codice rimane invariato, eccezione fatta per il metodo setter che imposterà il valore della chiave primaria:

```
@Entity
public class Student {
    @Id
    private Integer matricola;



    public void setMatricola(Integer n) {
        this.matricola = n;
    }
}
```

**ATTENZIONE!**

1) Quando scegliamo di operare così, quando un oggetto viene creato nel main, questo non verrà salvato nel DB fino a quando qualcuno non ne specificherà la chiave primaria.
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

Dopo quello di entità, il concetto chiave dei database è la relazione. È possibile gestire la presenza di relazioni tra più entità in Java. Per farlo occorrerà identificare il tipo di entità e chiarire come questa andrà resta persistente in memoria.

