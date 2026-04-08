# Java Tutorial: Vererbung (Inheritance)
## S.H.E.L.T.E.R. - Tierheim-Verwaltungssystem

**Klausurvorbereitung** | 90 Punkte | 4 Aufgaben

---

# TEIL 1: THEORIE - Vererbung in Java

## 1. Was ist Vererbung?

Vererbung ist ein Mechanismus, bei dem eine **Kindklasse** (Subklasse) Eigenschaften und Methoden von einer **Elternklasse** (Superklasse) erbt. Das vermeidet Code-Duplikation und ermoeglicht Polymorphismus.

```java
// Elternklasse
public class Tier {
    protected String name;

    public Tier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Kindklasse erbt von Tier
public class Hund extends Tier {
    private String rasse;

    public Hund(String name, String rasse) {
        super(name);          // Ruft den Konstruktor der Elternklasse auf
        this.rasse = rasse;
    }
}
```

**Wichtig:** Java unterstuetzt nur **Einfachvererbung** -- eine Klasse kann nur von EINER anderen Klasse erben.

---

## 2. Das `abstract` Keyword

### Abstrakte Klassen
Eine abstrakte Klasse **kann nicht instanziiert werden**. Sie dient als Vorlage fuer Kindklassen.

```java
public abstract class Fahrzeug {
    // Kann NICHT gemacht werden:
    // Fahrzeug f = new Fahrzeug();  // KOMPILIERUNGSFEHLER!
}
```

### Abstrakte Methoden
Eine abstrakte Methode hat **keinen Body** -- sie definiert nur die Signatur. Jede nicht-abstrakte Kindklasse **MUSS** sie implementieren.

```java
public abstract class Fahrzeug {
    // Abstrakte Methode: kein Body, nur Signatur
    public abstract double berechneVerbrauch();

    // Konkrete Methode: hat einen Body, wird vererbt
    public void starten() {
        System.out.println("Fahrzeug startet");
    }
}
```

**Merke:** Eine abstrakte Klasse kann SOWOHL abstrakte ALS AUCH konkrete Methoden haben!

---

## 3. `extends` - Vererbung herstellen

```java
public class Auto extends Fahrzeug {
    @Override
    public double berechneVerbrauch() {
        return 7.5;  // Liter pro 100km
    }
}
```

Die Kindklasse:
- Erbt alle `public` und `protected` Felder und Methoden
- **MUSS** alle abstrakten Methoden implementieren (oder selbst abstrakt sein)
- Kann eigene Felder und Methoden hinzufuegen
- Kann geerbte Methoden ueberschreiben

---

## 4. `super` - Zugriff auf die Elternklasse

### super() im Konstruktor
Der Konstruktor der Elternklasse wird mit `super()` aufgerufen. Dies **MUSS** die **erste Anweisung** im Konstruktor sein!

```java
public class Hund extends Tier {
    private boolean trainiert;

    public Hund(String name, boolean trainiert) {
        super(name);              // MUSS erste Zeile sein!
        this.trainiert = trainiert;
    }
}
```

**Wichtig:** Wenn die Elternklasse keinen parameterlosen Konstruktor hat, MUSS die Kindklasse explizit `super(...)` mit den richtigen Parametern aufrufen!

### super.methode() - Elternmethode aufrufen
```java
public class Hund extends Tier {
    @Override
    public String toString() {
        return super.toString() + " (Hund)";  // Ruft toString() der Elternklasse auf
    }
}
```

---

## 5. `@Override` - Methoden ueberschreiben

Die Annotation `@Override` markiert, dass eine Methode eine geerbte Methode ueberschreibt.

```java
public class Katze extends Tier {
    @Override                              // Optional, aber DRINGEND empfohlen
    public String toString() {
        return "Katze: " + name;
    }
}
```

**Warum @Override verwenden?**
- Der Compiler prueft, ob die Methode wirklich in der Elternklasse existiert
- Schuetzt vor Tippfehlern (z.B. `tostring()` statt `toString()`)

**Regeln fuer Ueberschreiben:**
- Gleicher Name, gleiche Parameter
- Rueckgabetyp muss gleich oder spezieller sein
- Sichtbarkeit darf nicht eingeschraenkt werden (public bleibt public)

---

## 6. `protected` - Der Vererbungs-Zugriffsmodifikator

| Modifier | Eigene Klasse | Gleiches Paket | Kindklassen | Ueberall |
|----------|:---:|:---:|:---:|:---:|
| `private` | Ja | Nein | Nein | Nein |
| *(default)* | Ja | Ja | Nein | Nein |
| `protected` | Ja | Ja | **Ja** | Nein |
| `public` | Ja | Ja | Ja | Ja |

`protected` ist ideal fuer Felder, auf die Kindklassen direkt zugreifen sollen:

```java
public abstract class Tier {
    protected String name;     // Kindklassen koennen direkt auf name zugreifen
    private int interneId;     // NUR in Tier selbst sichtbar
}

public class Hund extends Tier {
    public void belleMitName() {
        System.out.println(name + " bellt!");  // Geht, weil name protected ist
        // System.out.println(interneId);      // FEHLER! interneId ist private
    }
}
```

---

## 7. `final` - Vererbung/Ueberschreiben verhindern

### final auf Methoden
Eine `final` Methode **kann nicht ueberschrieben** werden:

```java
public abstract class Tier {
    public final String getId() {
        return getArt() + "-" + name;
    }
    // Kein Kind kann getId() ueberschreiben!
}
```

### final auf Klassen
Eine `final` Klasse **kann nicht beerbt** werden:

```java
public final class Wellensittich extends Tier {
    // Niemand kann von Wellensittich erben
}
```

---

## 8. Polymorphismus - Das Herzstuck der Vererbung

Polymorphismus bedeutet: Eine Variable vom Typ der Elternklasse kann Objekte jeder Kindklasse halten.

```java
// Alle diese Zuweisungen sind gueltig:
Tier tier1 = new Hund("Rex", true);
Tier tier2 = new Katze("Mimi", false);

// Die RICHTIGE Methode wird zur LAUFZEIT ausgewaehlt:
tier1.toString();  // Ruft Hund.toString() auf!
tier2.toString();  // Ruft Katze.toString() auf!

// In Collections:
List<Tier> tiere = new ArrayList<>();
tiere.add(new Hund("Rex", true));
tiere.add(new Katze("Mimi", false));

for (Tier t : tiere) {
    System.out.println(t.berechneVerbrauch());  // Jedes Tier berechnet anders!
}
```

**Wichtig:** Der Compiler kennt nur den **deklarierten Typ** (Tier), aber zur Laufzeit wird die Methode des **tatsaechlichen Typs** (Hund/Katze) aufgerufen!

---

## 9. `instanceof` und Type Casting

Wenn du wissen musst, welcher konkrete Typ ein Objekt hat:

```java
Tier tier = new Hund("Rex", true);

if (tier instanceof Hund) {
    Hund hund = (Hund) tier;           // Type Cast: Tier -> Hund
    hund.getWalkMinutesPerDay();       // Jetzt Hund-spezifische Methoden nutzbar
}

// Modernes Java (ab Java 16) - Pattern Matching:
if (tier instanceof Hund hund) {
    hund.getWalkMinutesPerDay();       // Direkt nutzbar, kein extra Cast noetig
}
```

**Wann brauchst du instanceof?**
- Wenn du auf kindspezifische Methoden zugreifen willst
- Wenn du eine Collection von Elterntypen filtern willst

```java
// Alle Hunde aus einer Tier-Liste filtern:
Set<Hund> hunde = new HashSet<>();
for (Tier t : alleTiere) {
    if (t instanceof Hund) {
        hunde.add((Hund) t);
    }
}
```

---

## 10. Konstruktor-Verkettung (Constructor Chaining)

In einer Vererbungshierarchie werden Konstruktoren **von oben nach unten** aufgerufen:

```java
public abstract class Tier {
    protected String name;
    public Tier(String name) {
        System.out.println("Tier-Konstruktor");
        this.name = name;
    }
}

public class Hund extends Tier {
    private boolean trainiert;
    public Hund(String name, boolean trainiert) {
        super(name);  // 1. Tier-Konstruktor wird ausgefuehrt
        System.out.println("Hund-Konstruktor");
        this.trainiert = trainiert;  // 2. Dann Hund-Konstruktor
    }
}

new Hund("Rex", true);
// Ausgabe:
// Tier-Konstruktor
// Hund-Konstruktor
```

---

## 11. Compile-Time-Typ vs. Runtime-Typ (KLAUSUR-FALLE!)

Das ist DER haeufigste Fehler in Klausuren. Java unterscheidet zwei Typen:

```java
Animal a = new Dog("Rex", 3, 10.0, 60, true);
//  ^               ^
//  |               |
//  Compile-Time-Typ    Runtime-Typ
//  (was der Compiler sieht)   (was tatsaechlich im Speicher ist)
```

**Der Compiler** kennt nur den deklarierten Typ (`Animal`):
```java
a.getName();           // OK - Animal hat getName()
a.calculateFoodPerDay(); // OK - Animal deklariert diese abstrakte Methode
a.getWalkMinutesPerDay(); // KOMPILIERUNGSFEHLER! Animal kennt diese Methode nicht!
```

**Die JVM** (zur Laufzeit) kennt den echten Typ (`Dog`):
```java
a.calculateFoodPerDay(); // Ruft Dog.calculateFoodPerDay() auf, NICHT Animal's
a.toString();            // Ruft Dog.toString() auf
```

**Loesung fuer kindspezifische Methoden:** instanceof + Cast
```java
if (a instanceof Dog) {
    Dog d = (Dog) a;                // Downcast: sicher, weil instanceof geprueft
    d.getWalkMinutesPerDay();       // Jetzt OK!
}
```

---

## 12. Upcasting vs. Downcasting

```java
// UPCASTING (Kind -> Eltern): immer sicher, implizit
Dog dog = new Dog("Rex", 3, 10.0, 60, true);
Animal a = dog;                    // Automatisch, kein Cast noetig

// DOWNCASTING (Eltern -> Kind): gefaehrlich, explizit
Animal a2 = new Dog("Rex", 3, 10.0, 60, true);
Dog d = (Dog) a2;                  // Expliziter Cast noetig

// GEFAHR: ClassCastException!
Animal a3 = new Cat("Mimi", 3, 4.0, true);
Dog d2 = (Dog) a3;                 // KOMPILIERT, aber LAUFZEITFEHLER!
// -> ClassCastException: Cat cannot be cast to Dog
```

**Regel:** Immer `instanceof` pruefen vor einem Downcast!

---

## 13. Overriding vs. Overloading (haeufige Verwechslung!)

| | **Overriding** (Ueberschreiben) | **Overloading** (Ueberladen) |
|---|---|---|
| **Was?** | Kindklasse ersetzt Elternmethode | Gleicher Name, andere Parameter |
| **Wo?** | In der Kindklasse | In derselben ODER Kindklasse |
| **Methodenname** | GLEICH | GLEICH |
| **Parameter** | GLEICH | VERSCHIEDEN |
| **@Override** | Ja | Nein |
| **Dispatch** | Zur LAUFZEIT (dynamisch) | Zur KOMPILIERZEIT (statisch) |

```java
public class Animal {
    public void feed(double amount) { ... }        // Original
}

public class Dog extends Animal {
    @Override
    public void feed(double amount) { ... }        // OVERRIDE: gleiche Parameter
    public void feed(double amount, String type) { ... } // OVERLOAD: andere Parameter!
}
```

---

## 14. Die Object-Klasse -- Wurzel aller Klassen

JEDE Klasse in Java erbt automatisch von `Object` (auch wenn kein `extends` geschrieben wird).

```java
public class Dog extends Animal { ... }
// Vererbungskette: Dog -> Animal -> Object
```

Von `Object` geerbte Methoden, die man oft ueberschreibt:
- `toString()` -- String-Darstellung des Objekts
- `equals(Object o)` -- Gleichheitsvergleich
- `hashCode()` -- Hash-Wert (muss konsistent mit equals sein!)

Deshalb funktioniert `instanceof Object` fuer ALLE Objekte:
```java
new Dog(...) instanceof Object  // immer true!
```

---

## Zusammenfassung: Checkliste fuer die Klausur

- [ ] `abstract class` erstellen mit abstrakten + konkreten Methoden
- [ ] `extends` nutzen um Kindklassen zu erstellen
- [ ] `super()` im Konstruktor der Kindklasse aufrufen (MUSS erste Zeile sein!)
- [ ] `@Override` bei ueberschriebenen Methoden verwenden
- [ ] `protected` Felder fuer Zugriff aus Kindklassen
- [ ] `final` Methoden die nicht ueberschrieben werden duerfen
- [ ] Polymorphismus: Elterntyp-Variable haelt Kindklassen-Objekt
- [ ] `instanceof` + Cast fuer Zugriff auf kindspezifische Methoden
- [ ] `equals()` mit instanceof pruefen (nicht getClass()!)
- [ ] Collections mit Elterntyp fuer gemischte Objekte
- [ ] Compile-Time-Typ vs Runtime-Typ unterscheiden koennen
- [ ] Upcasting (implizit) vs Downcasting (explizit + instanceof!)
- [ ] Overriding vs Overloading nicht verwechseln
- [ ] Object als Wurzel aller Klassen kennen

---
---

# TEIL 2: AUFGABENSTELLUNG

## Paket: `de.uniwue.shelter`

Sie implementieren ein Tierheim-Verwaltungssystem. Das Tierheim beherbergt verschiedene Tierarten (Hunde, Katzen), die gemeinsame Eigenschaften teilen, aber artspezifisches Verhalten haben. Sie arbeiten mit einer abstrakten Klasse `Animal` und ihren erbenden Kindklassen `Dog` und `Cat`, sowie einer Verwaltungsklasse `ShelterManager`.

**Hinweise:**
- Alle Klassen befinden sich im Paket `de.uniwue.shelter`
- Werfen Sie die jeweils passende Exception bei ungueltigen Parametern
- `null`-Parameter fuehren zu `NullPointerException`
- Ungueltige Wertebereiche fuehren zu `IllegalArgumentException`
- Ungueltige Zustandsuebergaenge fuehren zu `IllegalStateException`

---

## Aufgabe 1: Die abstrakte Klasse `Animal` (15 Punkte)

Implementieren Sie die abstrakte Klasse `Animal` im Paket `de.uniwue.shelter`.

### Instanzvariablen (alle `protected`):
- `name` (`String`) - Name des Tieres
- `age` (`int`) - Alter in Jahren
- `weight` (`double`) - Gewicht in kg
- `adopted` (`boolean`) - Adoptionsstatus

### Konstruktor:
```java
public Animal(String name, int age, double weight)
```
- `name` darf nicht `null` sein (`NullPointerException`) und nicht leer (`IllegalArgumentException`)
- `age` darf nicht negativ sein (`IllegalArgumentException`)
- `weight` muss groesser als 0 sein (`IllegalArgumentException`)
- `adopted` wird mit `false` initialisiert

### Abstrakte Methoden:
```java
public abstract String getSpeciesName();
public abstract double calculateFoodPerDay();
```

### Getter:
```java
public String getName()
public int getAge()
public double getWeight()
public boolean isAdopted()
```

### Adoptions-Methoden:
```java
public void adopt()
```
Setzt `adopted` auf `true`. Wirft `IllegalStateException` falls das Tier bereits adoptiert ist.

```java
public void returnToShelter()
```
Setzt `adopted` auf `false`. Wirft `IllegalStateException` falls das Tier nicht adoptiert ist.

### Final-Methode:
```java
public final String getAnimalId()
```
Gibt `"<SpeciesName>-<name>-<age>"` zurueck. Diese Methode ist `final` und darf von Kindklassen **nicht** ueberschrieben werden.

### equals, hashCode, toString:

**equals:** Zwei Animals sind gleich, wenn sie den gleichen `name` UND den gleichen `getSpeciesName()` haben.

**hashCode:** Konsistent mit `equals`.

**toString:** `"<SpeciesName>: <name> (Alter: <age>, Gewicht: <weight>kg)"`

### Punkte:

| Test | Punkte |
|------|--------|
| test01ConstructorValid | 1 |
| test02ConstructorExceptions | 1.5 |
| test03Getters | 1 |
| test04AdoptAndReturn | 2 |
| test05AdoptExceptions | 1.5 |
| test06GetAnimalIdFinal | 2 |
| test07Equals | 2 |
| test08HashCode | 1 |
| test09ToString | 1.5 |
| test10AbstractMethods | 1.5 |
| **Summe** | **15** |

---

## Aufgabe 2: Die Klasse `Dog` (12 Punkte)

Implementieren Sie die Klasse `Dog`, die von `Animal` erbt.

### Zusaetzliche Instanzvariablen (`private`):
- `walkMinutesPerDay` (`int`) - Benoetigte Auslaufzeit in Minuten pro Tag
- `trained` (`boolean`) - Ob der Hund trainiert ist

### Konstruktor:
```java
public Dog(String name, int age, double weight, int walkMinutesPerDay, boolean trained)
```
- Nutzt `super(name, age, weight)` fuer die Elternklasse
- `walkMinutesPerDay` muss groesser als 0 sein (`IllegalArgumentException`)

### Ueberschriebene Methoden:
```java
@Override
public String getSpeciesName()  // Gibt "Hund" zurueck

@Override
public double calculateFoodPerDay()
// Formel: weight * 0.025 * (trained ? 1.0 : 1.2)
// Trainierte Hunde fressen normal, untrainierte 20% mehr (Stress)
```

### Eigene Methoden:
```java
public int getWalkMinutesPerDay()
public boolean isTrained()
public void train()  // Setzt trained auf true. Wirft IllegalStateException falls bereits trainiert.
```

### toString:
```java
@Override
public String toString()
// "Hund: <name> (Alter: <age>, Gewicht: <weight>kg, Auslauf: <walkMinutesPerDay>min/Tag)"
```

### Punkte:

| Test | Punkte |
|------|--------|
| test01Constructor | 1.5 |
| test02ConstructorCallsSuper | 1.5 |
| test03GetSpeciesName | 1 |
| test04CalculateFoodPerDay | 2 |
| test05GettersAndTrain | 2 |
| test06TrainException | 1 |
| test07ToString | 1.5 |
| test08EqualsWithAnimal | 1.5 |
| **Summe** | **12** |

---

## Aufgabe 3: Die Klasse `Cat` (12 Punkte)

Implementieren Sie die Klasse `Cat`, die von `Animal` erbt.

### Zusaetzliche Instanzvariablen (`private`):
- `indoor` (`boolean`) - Ob die Katze eine reine Wohnungskatze ist
- `livesRemaining` (`int`) - Verbleibende Leben (startet bei 9)

### Konstruktor:
```java
public Cat(String name, int age, double weight, boolean indoor)
```
- Nutzt `super(name, age, weight)` fuer die Elternklasse
- `livesRemaining` wird immer mit 9 initialisiert

### Ueberschriebene Methoden:
```java
@Override
public String getSpeciesName()  // Gibt "Katze" zurueck

@Override
public double calculateFoodPerDay()
// Indoor-Katze: weight * 0.03
// Outdoor-Katze: weight * 0.04
```

### Eigene Methoden:
```java
public boolean isIndoor()
public int getLivesRemaining()
public void loseLife()
// Verringert livesRemaining um 1.
// Wirft IllegalStateException falls livesRemaining <= 0.
```

### toString:
```java
@Override
public String toString()
// "Katze: <name> (Alter: <age>, Gewicht: <weight>kg, Indoor: <ja/nein>, Leben: <livesRemaining>)"
```
Hinweis: `indoor == true` ergibt "ja", `indoor == false` ergibt "nein".

### Punkte:

| Test | Punkte |
|------|--------|
| test01Constructor | 1.5 |
| test02ConstructorCallsSuper | 1.5 |
| test03GetSpeciesName | 1 |
| test04CalculateFoodPerDay | 2 |
| test05GettersAndLoseLife | 2 |
| test06LoseLifeException | 1 |
| test07ToString | 1.5 |
| test08PolymorphicBehavior | 1.5 |
| **Summe** | **12** |

---

## Aufgabe 4: Die Klasse `ShelterManager` (51 Punkte)

Implementieren Sie die Klasse `ShelterManager`, die das Tierheim verwaltet.

### Empfohlene Instanzvariablen:
```java
private Map<String, Animal> animals;     // animalId -> Animal
private Set<Animal> adoptedAnimals;
```

### Konstruktor:
```java
public ShelterManager()
```
Initialisiert die Datenstrukturen.

### Tier hinzufuegen:
```java
public void addAnimal(Animal animal)
```
- `animal` darf nicht `null` sein (`NullPointerException`)
- Wirft `IllegalArgumentException` falls ein Tier mit gleicher `getAnimalId()` bereits existiert
- Wirft `IllegalArgumentException` falls das Tier bereits adoptiert ist

### Abfragen:
```java
public Set<Animal> getAllAnimals()
public Set<Animal> getAvailableAnimals()      // Nicht adoptierte Tiere
public Set<Animal> getAdoptedAnimals()
public Optional<Animal> getAnimalById(String animalId)
public int getAnimalCount()
public int getAvailableCount()
```

### Artspezifische Abfragen (instanceof + Casting):
```java
public Set<Dog> getAllDogs()
```
Gibt alle Tiere zurueck, die Hunde sind. Nutzen Sie `instanceof` und casten Sie zu `Dog`.

```java
public Set<Cat> getAllCats()
```
Gibt alle Tiere zurueck, die Katzen sind.

### Berechnungen (Polymorphismus):
```java
public double calculateTotalFoodPerDay()
```
Berechnet den gesamten taeglichen Futterbedarf aller **verfuegbaren** (nicht adoptierten) Tiere. Ruft `calculateFoodPerDay()` auf jedem Tier auf -- dank Polymorphismus wird automatisch die richtige Implementierung (Hund/Katze) aufgerufen.

```java
public int calculateTotalWalkMinutes()
```
Berechnet die gesamte taegliche Auslaufzeit aller **verfuegbaren** Hunde. Erfordert `instanceof Dog` Check und Cast.

### Adoption:
```java
public void adoptAnimal(String animalId)
```
Findet das Tier per ID, ruft `adopt()` auf, fuegt es zu `adoptedAnimals` hinzu.
- Wirft `IllegalArgumentException` falls kein Tier mit dieser ID existiert
- Wirft `IllegalStateException` falls das Tier bereits adoptiert ist

```java
public void returnAnimal(String animalId)
```
Findet das Tier per ID, ruft `returnToShelter()` auf, entfernt es aus `adoptedAnimals`.
- Wirft `IllegalArgumentException` falls kein Tier mit dieser ID existiert
- Wirft `IllegalStateException` falls das Tier nicht adoptiert ist

### Analyse (Polymorphismus + Collections):
```java
public Map<String, List<Animal>> groupBySpecies()
```
Gruppiert alle Tiere nach `getSpeciesName()`. Schluessel ist der Artname (z.B. "Hund", "Katze"), Wert ist die Liste der Tiere dieser Art.

```java
public List<Animal> getAnimalsSortedByFood()
```
Gibt alle **verfuegbaren** Tiere sortiert nach `calculateFoodPerDay()` in **absteigender** Reihenfolge zurueck.

```java
public Optional<Animal> getHungriestAnimal()
```
Gibt das verfuegbare Tier mit dem hoechsten `calculateFoodPerDay()` zurueck. Leeres Optional falls keine verfuegbaren Tiere vorhanden.

### toString:
```java
@Override
public String toString()
```
Format:
```
Tierheim (<availableCount> verfuegbar, <adoptedCount> adoptiert)
Verfuegbare Tiere:
	<toString von Tier1>
	<toString von Tier2>
Adoptierte Tiere:
	<toString von Tier3>
```
- Tiere jeweils alphabetisch nach `getAnimalId()` sortiert
- Falls keine Tiere in einer Kategorie: Zeile mit `\t-` statt Tierliste
- Nutzen Sie `System.lineSeparator()` und `\t` fuer Einrueckung

### Punkte:

| Test | Punkte |
|------|--------|
| test01Constructor | 1 |
| test02AddAnimal | 2 |
| test03AddAnimalExceptions | 2 |
| test04GetAllAnimals | 2 |
| test05GetAvailableAnimals | 2 |
| test06GetAnimalById | 2 |
| test07GetAnimalCount | 1.5 |
| test08GetAllDogs | 3.5 |
| test09GetAllCats | 3.5 |
| test10CalculateTotalFoodPerDay | 4 |
| test11CalculateTotalWalkMinutes | 3.5 |
| test12AdoptAnimal | 3 |
| test13AdoptAnimalExceptions | 2 |
| test14ReturnAnimal | 3 |
| test15ReturnAnimalExceptions | 2 |
| test16GroupBySpecies | 4 |
| test17GetAnimalsSortedByFood | 3.5 |
| test18GetHungriestAnimal | 3 |
| test19ToString | 3 |
| test20AdoptAndReturnIntegration | 5 |
| **Summe** | **51** |

---
---

# TEIL 3: LOESUNGEN MIT ERKLAERUNGEN

Die vollstaendigen Loesungen befinden sich in den Java-Dateien im `src/`-Verzeichnis.
Jede Datei enthaelt ausfuehrliche Kommentare, die erklaeren WARUM bestimmte
Vererbungsmechanismen eingesetzt werden.

## Lernpfad

1. Lies zuerst die Theorie (Teil 1)
2. Versuche die Aufgaben selbst zu loesen (Teil 2)
3. Vergleiche mit den Loesungen in den Java-Dateien
4. Fuehre die Tests aus um deine Loesung zu pruefen

## Schnell-Referenz: Welches Konzept wo?

| Datei | Zeilen | Konzepte |
|-------|--------|----------|
| Animal.java | Ganze Datei | abstract class, abstract methods, protected, final, equals mit Polymorphismus |
| Dog.java | Konstruktor | extends, super(), Konstruktor-Verkettung |
| Dog.java | Methoden | @Override, eigene Methoden |
| Cat.java | Konstruktor | extends, super() (Wiederholung) |
| Cat.java | Methoden | @Override mit anderer Implementierung als Dog |
| ShelterManager.java | getAllDogs/Cats | instanceof, Type Casting |
| ShelterManager.java | calculateTotal* | Polymorphismus in Collections |
| ShelterManager.java | groupBySpecies | Polymorphe Methoden als Map-Keys |
