# Date/Time

Sono delle API Java contenute nei file: `java.lang.System`, `java.util.Date`, `java.util.Calendar`, `java.time`

## System

I metodi relativi alla gestione del tempo nella libreria `System` di Java sono
1) `currentTimeMillis()`, che fornisce il tempo trascorso (in millisecondi) tra oggi e l'1 Gennaio 1970
2) `nanoTime()`, fornisce il tempo 'attuale' in nanosecondi con alta precisione, ma il riferimento non è assoluto (ha senso usarlo solo per calcolare intervalli temporali)

## Date

È una classe deprecata a causa della scomodità nel definire le date. Il 6 Maggio 2015 (ovvero il giorno in cui ho compiuto 12 anni) può essere ottenuto con l'istruzione;

```
Date d = new Date(115, 4, 6);
```

1) 115: differenza tra l'anno 1900 (riferimento) e il 2015
2) 4: differenza di mesi tra Gennaio e Maggio (per indicare Gennaio usiamo 0)
3) 6: differenza di giorni tra l'1 e il 6 (per indicare l'1 usiamo 0)

Decisamente controintuitivo.

## Calendar

È una classe astratta, una delle sue implementazioni è la classe `GregorianCalendar`, che però non è l'unica in quanto non tutti seguono il calendario gregoriano

## java.time

È una API considerata standard. Come per le stringhe, gli oggetti di questa API sono immutabili ("modificare" una data o un'ora significa di fatto crearne una nuova)

Per quanto riguarda gli istanti di tempo la classe generale `Temporal`, implementata nelle 'sotto-classi':

- `Instant` <- tempo (UTC)
- `LocalDate` <- data, rispetto alla nostra time-zone
- `LocalDateTime` <- data e ora locale
- `LocalTime` <- tempo locale
- `ZonedTime` <- tempo locale, rispetto ad un'altra time-zone

Instant si affida sistema operativo, ignorando eventuali time-zone geografiche.

Mentre per quanto riguarda gli intervalli di tempo abbiamo la classe generale `TemporalAmount`, implementata nelle 'sotto-classi':

- `Duration` <- intervallo valutato rispetto a degli istanti di tempo
- `Period` <- intervallo valutato rispetto a degli intervalli di date


Le classi derivanti da `Temporal` ammettono i seguenti factory methods:

- `of()`, fornisce un'istanza di tempo generata attraverso i parametri che riceve
- `from()`, fornisce un'istanza di tempo ottenuta convertendo i dati di un'altra (eventualmente perdendo informazioni)
- `parse()`, fornisce un'istanza di tempo generata a partire da una stringa ricevuta come parametro
- `now()`, fornisc un'istanza di tempo che indica l'istante attuale. Accetta evenutalmente una `ZoneId` come parametro

## Changing

È possibile modificare delle date o degli istanti di tempo salvati all'interno di variabili tramite i seguenti metodi:

1) `.minus()`, rimuove la quantità di tempo specificata come parametro dall'istante su cui viene chiamato
2) `.plus()`, aggiunge la quantità di tempo specificata come parametro dall'istante su cui viene chiamato
2) `.with()`, riceve come parametro un "temporal adjuster" che modifica la data opportunamente

`TemporalAdjusters` è un'interfaccia funzionale contenente come unico metodo il metodo:

```
Temporal adjustInto(Temporal t)
```

che riceve un oggetto di classe `Temporal` e ne ritorna un'altro modificando quello ricevuto come parametro. Possiamo quindi pensare di implementarla attraverso una lambda function.

include alcuni metodi fondamentali, come:
- `firstDayofMonth()`
- `firstDayOfNextMonth()`
- `firstInMonth(DayOfWeek dayOfWeek)`
ecc..

## Utilizzo di stream

Supponiamo di voler contare tutte le date tra il 2025-12-01 e il 2025-12-25 che sono giorni infrasettimanali.

Sfruttiamo il metodo `.datesUntill()` della classe `LocalDate` per ottenere uno stream di `LocalDate`, contenente tutte le date a partire da quella su cui si applica il metodo (inclusa) fino a quella che riceve come parametro (esclusa).

Il codice per ottenere il risultato è il seguente:

```
LocalDate xmas = LocalDate.of(2025, 12, 25);
LocalDate firstOfxmasMonth = xmas.with(TemporalAdjusters.firstDayOfMonth());

long n_infra = 
firstOfxmasMonth.datesUntil(xmas.plusDays(1))
.filter((D) -> D.getDayOfWeek() != DayOfWeek.SATURDAY)
.filter((D) -> D.getDayOfWeek() != DayOfWeek.SUNDAY)
.count();
```