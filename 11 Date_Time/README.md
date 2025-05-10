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

- `Instant` <- tempo 
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