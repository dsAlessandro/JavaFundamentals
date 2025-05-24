# Interfacce

Un'interfaccia è una classe **priva di attributi** (a meno di costanti definite come `public static final`) contenente una serie di metodi **non implementati**


Nel package `Interfaces` sono riportate due interfacce, chiamate rispettivamente
- `twoDimAlgebra`
- `threeDimAlgebra`

I metodi al loro interno (`distance`, `scalarProduct`, ecc...) rappresentano operazioni o grandezze fondamentali definite per gli oggetti delle classi `twoDimPoint` e `threeDimPoint`, la cui implementazione è riportata nel package `Points`.

Ad ogni modo per nessuno dei metodi in questione è riportata l'implementazione: l'idea è quella di poter fornire molteplici implementazioni degli stessi e di non rendere il codice "hard-coded" (cioè privo di flessibilità)

# Implementazioni di interfacce

Per illustrare questa dualità vengono poi proposte nel package `Implementation` due classi, `AllAlgebraStd` e `AllAlgebraCustom`, che, come possiamo leggere nei loro rispettivi file, "implementano" entrambe le interfacce:


`public class AllAlgebraStd implements twoDimAlgebra, threeDimAlgebra`

`public class AllAlgebraCustom implements twoDimAlgebra, threeDimAlgebra`

una classe "implementa" un'interfaccia quando completa la definizione dei metodi che erano stati solo dichiarati nell'interfaccia.

Nel caso di `AllAlgebraStd` tutte le operazioni fondamentali sono implementate come ci aspetteremmo (la distanza è quella euclidea, il prodotto scalare è quello calcolato termine a termine, e così via).

Nel caso di `AllAlgebraCustom` invece tutte le operazioni sono state implementate in maniera totalmente diversa e arbitraria (ad esempio quando si prova a calcolare la distanza tra due punti il valore di ritorno è sempre 1, ed è indipendente dai punti stessi).


In questa maniera è possibile creare dei programmi che effettuino operazioni algebriche in maniera 'dinamica'. 

Concretamente verranno istanziati degli oggetti di classi che implementano l'algebra in due o tre dimensioni, e di questi verranno utilizzati i metodi di somma, distanza e stampa dei punti, ma senza stabilire a priori come queste operazioni vengano fatte.

## Esempio

Diamo dunque un'occhiata al file Interfaces.java, il nostro main:


- vengono inizialmente creati due punti in 2D e due punti in 3D.
```
public class Interfaces {
    public static void main(String[] args) throws Exception {
        twoDimPoint a2D = new twoDimPoint();
        twoDimPoint b2D = new twoDimPoint();

        threeDimPoint a3D = new threeDimPoint();
        threeDimPoint b3D = new threeDimPoint();

        ...


    }
}
```



- per tutti questi punti vengono dunque impostati i valori delle rispettive coordinate
```
...

a2D.setx(2);
a2D.sety(3);

b2D.setx(12);
b2D.sety(30);





a3D.setx(12);
a3D.sety(7);
a3D.setz(3);


b3D.setx(4);
b3D.sety(23);
b3D.setz(8);

...
```

- vengono poi create due algebre in 2D, la prima come istanza di `AllAlgebraStd`, che implementa l'algebra tradizionale, l'altra come istanza di `AllAlgebraCustom`, che implementa un'algebra personalizzata.


```
System.out.println("2D stuff!");
twoDimAlgebra Algebra1 = new AllAlgebraStd();
twoDimAlgebra Algebra2 = new AllAlgebraCustom();
```


- entrambe le algebre poi forniscono indicazioni sulla distanza fra i due punti:
    - L'algebra tradizionale stampa a schermo 28, la distanza tra i punti (2, 3) (12, 30)
    - L'algebra personalizzata stampa a schermo 1 (valore totalmente arbitrario e specificato nell'implementazione di tale metodo nel file `AllAlgebraCustom`)


```
System.out.println(Algebra1.distance2D(a2D, b2D));
System.out.println(Algebra2.distance2D(a2D, b2D));
...
```


Le due variabili `Algebra1` e  `Algebra2` sono entrambe di tipo `twoDimAlgebra`, eppure forniscono due risultati completamente diversi. Questo perché `twoDimAlgebra` è un'interfaccia, che garantisce l'esistenza di determinati metodi, ma è poi la classe che viene istanziata a definire come operano suddetti metodi e siccome le due istanze appartengono a classe diverse -con metodi diversi- i risultati sono a loro volta distinti.

Nel main è riportato il codice eseguibile che mostra lo stesso esempio insieme ad un esempio eseguito per i punti in 3D.