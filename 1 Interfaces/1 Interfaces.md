# Interfacce

Un'interfaccia è una classe **priva di attributi** contenente una serie di metodi **non implementati**

Nel package `Interfaces` sono riportate due interfacce, chiamate rispettivamente
- `twoDimAlgebra`
- `threeDimAlgebra`

I metodi al loro interno (`distance`, `scalarProduct`, ecc...) rappresentano operazioni o grandezze fondamentali definite per i punti in due o tre dimensioni, ad ogni modo per nessuno dei metodi in questione è riportata l'implementazione.

Questo perché in un algebra **devono essere definite** queste operazioni, ma il modo in cui queste sono definite può dipendere dal nostro volere.

# Implementazioni di interfacce

Per illustrare questa dualità vengono poi proposte due classi, `AllAlgebraStd` e `AllAlgebraCustom`, che, come possiamo leggere nei loro rispettivi file, "implementano" le due interfacce:


`public class AllAlgebraStd implements twoDimAlgebra, threeDimAlgebra`

`public class AllAlgebraCustom implements twoDimAlgebra, threeDimAlgebra`

entrambe queste classi dunque implementano tutti i metodi definiti nelle due interfacce, seppure in maniera differente.

Nel caso di `AllAlgebraStd` tutte le operazioni fondamentali sono implementati come nell'algebra standard (la distanza è quella euclidea, il prodotto scalare è quello calcolato termine a termine, e così via).

Nel caso di `AllAlgebraCustom` invece tutte le operazioni sono implementate in maniera totalmente diversa e arbitraria (la distanza tra due punti in due dimensioni è sempre pari ad 1, indipendentemente dai due punti).


In questa maniera è possibile creare dei programmi che effettuino operazioni algebriche in maniera 'dinamica'. Verranno istanziati degli oggetti di classi che implementano l'algebra in due o tre dimensioni, e di questi verranno utilizzati i metodi di somma, distanza e stampa dei punti, ma senza stabilire a priori come queste operazioni vengano fatte.

## Esempio

Diamo dunque un'occhiata al file Interfaces.java:

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

vengono inizialmente creati due punti in 2D e due punti in 3D.

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

per tutti questi punti vengono dunque impostati i valori delle rispettive coordinate (i metodi di set sono forniti dalla classe punti, di cui questi sono istanze).

```
...
System.out.println("2D stuff!");
twoDimAlgebra Algebra1 = new AllAlgebraStd();
twoDimAlgebra Algebra2 = new AllAlgebraCustom();


System.out.println(Algebra1.distance2D(a2D, b2D));
System.out.println(Algebra2.distance2D(a2D, b2D));
...
```

vengono poi create due algebre in 2D, la prima come istanza di `AllAlgebraStd`, che implementa l'algebra tradizionale, l'altra come istanza di `AllAlgebraCustom`, che implementa un'algebra personalizzata.

entrambe le algebre poi forniscono indicazioni sulla distanza fra i due punti:
- L'algebra tradizionale stampa a schermo 28, la distanza tra i punti (2, 3) (12, 30)
- L'algebra personalizzata stampa a schermo 1 (valore totalmente arbitrario e specificato nell'implementazione di tale metodo nel file `AllAlgebraCustom`)


Le due variabili `Algebra1` e  `Algebra2` sono entrambe di tipo `twoDimAlgebra`, eppure forniscono due risultati completamente diversi. Questo perché `twoDimAlgebra` è un'interfaccia, che garantisce l'esistenza di determinati metodi, ma è poi la classe che viene istanziata a definire come operano suddetti metodi e siccome le due istanze appartengono a classe diverse -con metodi diversi- i risultati sono a loro volta distinti.