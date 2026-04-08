# JAVA VERERBUNG - SPICKZETTEL (DIN A4, Vorder- + Rueckseite)

============================================================
## VORDERSEITE
============================================================

### 1. ABSTRACT CLASS + ABSTRACT METHODS
```java
public abstract class Animal {                  // Kann NICHT instanziiert werden!
    protected String name;                      // protected = Kindklassen koennen zugreifen

    public Animal(String name) {                // Konstruktor: wird von Kindern via super() gerufen
        this.name = name;
    }

    public abstract String getSpecies();        // Kein Body! Kind MUSS implementieren
    public abstract double calcFood();          // Kein Body! Kind MUSS implementieren

    public String getName() { return name; }    // Konkrete Methode: wird vererbt
    public final String getId() {               // final: Kind kann NICHT ueberschreiben
        return getSpecies() + "-" + name;       // Ruft abstrakte Methode auf -> Polymorphismus!
    }
}
```

### 2. EXTENDS + SUPER() + @OVERRIDE
```java
public class Dog extends Animal {               // Dog IST-EIN Animal, erbt alles
    private int walkMin;

    public Dog(String name, int walkMin) {
        super(name);                            // MUSS ERSTE ZEILE sein! Ruft Animal(name) auf
        if (walkMin <= 0) throw new IllegalArgumentException();
        this.walkMin = walkMin;
    }

    @Override                                   // Compiler prueft: existiert in Animal?
    public String getSpecies() { return "Hund"; }

    @Override
    public double calcFood() { return weight * 0.025; }  // Eigene Formel

    @Override
    public String toString() { return "Hund: " + name; } // Ueberschreibt Object.toString()

    public int getWalkMin() { return walkMin; } // Eigene Methode, NUR ueber Dog-Referenz!
}
```

### 3. POLYMORPHISMUS (Das Wichtigste!)
```java
Animal a = new Dog("Rex", 60);     // Elterntyp-Variable, Kind-Objekt
a.getSpecies();    // -> "Hund"    // Ruft DOG.getSpecies() auf! (Runtime-Typ zaehlt)
a.calcFood();      // -> Dog-Formel
a.getId();         // -> "Hund-Rex" (final, aber nutzt getSpecies() polymorph)
a.getWalkMin();    // KOMPILIERUNGSFEHLER! Compiler kennt nur Animal-Methoden!
```
**Compiler-Typ** (Animal) bestimmt WELCHE Methoden aufrufbar.
**Runtime-Typ** (Dog) bestimmt WELCHE Implementierung laeuft.

### 4. INSTANCEOF + TYPE CASTING
```java
Animal a = new Dog("Rex", 60);

// IMMER erst instanceof pruefen, DANN casten!
if (a instanceof Dog) {
    Dog d = (Dog) a;               // Downcast: Animal -> Dog (explizit)
    d.getWalkMin();                // Jetzt Dog-Methoden nutzbar!
}

// OHNE Check -> ClassCastException zur Laufzeit:
// Cat c = (Cat) a;                // Kompiliert, aber LAUFZEITFEHLER!

// instanceof Wahrheitstabelle:
// new Dog() instanceof Dog    -> true
// new Dog() instanceof Animal -> true   (Dog IST-EIN Animal)
// new Dog() instanceof Cat    -> false
// new Dog() instanceof Object -> true   (ALLES erbt von Object)
// null instanceof Dog         -> false  (null ist nichts)
```

### 5. EQUALS MIT VERERBUNG
```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Animal)) return false;   // instanceof, NICHT getClass()!
    Animal other = (Animal) o;
    return Objects.equals(name, other.name)
        && Objects.equals(getSpecies(), other.getSpecies());  // Polymorph!
}
// -> Hund "Rex" != Katze "Rex" (verschiedene Species)

@Override
public int hashCode() {
    return Objects.hash(name, getSpecies());    // MUSS konsistent mit equals sein!
}
```

### 6. EXCEPTION-REIHENFOLGE (Konstruktor-Pattern)
```java
public Animal(String name, int age, double weight) {
    if (name == null)  throw new NullPointerException("...");       // 1. null
    if (name.isEmpty()) throw new IllegalArgumentException("...");  // 2. Wertebereich
    if (age < 0)       throw new IllegalArgumentException("...");
    if (weight <= 0)   throw new IllegalArgumentException("...");
    // ... dann Zuweisungen
}
// IllegalStateException -> fuer ungueltige Zustandsuebergaenge (z.B. doppelt adoptieren)
```

============================================================
## RUECKSEITE
============================================================

### 7. COLLECTIONS MIT POLYMORPHISMUS
```java
Map<String, Animal> tiere = new HashMap<>();    // Speichert Dogs UND Cats!
tiere.put(dog.getId(), dog);                    // Upcast: Dog -> Animal (implizit)
tiere.put(cat.getId(), cat);

// Polymorphe Berechnung:
double total = 0;
for (Animal a : tiere.values()) {
    total += a.calcFood();                      // Dog-Formel ODER Cat-Formel!
}

// Filtern mit instanceof:
Set<Dog> dogs = new HashSet<>();
for (Animal a : tiere.values()) {
    if (a instanceof Dog) dogs.add((Dog) a);
}

// Gruppieren nach polymorpher Methode:
Map<String, List<Animal>> groups = new HashMap<>();
for (Animal a : tiere.values()) {
    groups.computeIfAbsent(a.getSpecies(), k -> new ArrayList<>()).add(a);
}
// -> {"Hund": [Rex, Bello], "Katze": [Mimi, Luna]}
```

### 8. SORTIEREN + OPTIONAL
```java
// Sortieren nach polymorpher Methode (absteigend):
List<Animal> sorted = new ArrayList<>(animals);
sorted.sort((a, b) -> Double.compare(b.calcFood(), a.calcFood()));

// Oder mit Comparator:
sorted.sort(Comparator.comparingDouble(Animal::calcFood).reversed());

// Optional:
public Optional<Animal> findById(String id) {
    return Optional.ofNullable(map.get(id));     // null -> Optional.empty()
}
// Nutzung: opt.isPresent(), opt.get(), opt.isEmpty()

// Max mit Stream:
Optional<Animal> max = animals.stream()
    .max(Comparator.comparingDouble(Animal::calcFood));
```

### 9. TOSTRING()-PATTERN
```java
@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Tierheim (").append(count).append(" verfuegbar)");
    sb.append(System.lineSeparator());          // NIEMALS "\n" hardcoden!
    sb.append("Tiere:").append(System.lineSeparator());
    for (Animal a : sorted) {
        sb.append("\t").append(a.toString());    // \t fuer Einrueckung
        sb.append(System.lineSeparator());       // Polymorphes toString()!
    }
    return sb.toString();
}
```

### 10. UEBERBLICK: OVERRIDING vs OVERLOADING

| | Overriding (Ueberschreiben) | Overloading (Ueberladen) |
|---|---|---|
| Wo? | Kind ueberschreibt Eltern-Methode | Gleiche Klasse, mehrere Varianten |
| Name | GLEICH | GLEICH |
| Parameter | GLEICH | VERSCHIEDEN |
| Return | Gleich oder spezieller | Egal |
| @Override | Ja | Nein |
| Dispatch | Runtime (dynamisch) | Compile-Time (statisch) |

### 11. KONSTRUKTOR-VERKETTUNG
```
new Dog("Rex", 3, 10.0, 60, true)
  |
  +-> Dog-Konstruktor startet
       |
       +-> super("Rex", 3, 10.0)    // ERSTE ZEILE!
            |
            +-> Animal-Konstruktor   // Validierung + Felder setzen
            |
       +-> Dog-eigene Felder setzen  // NACH super()
```

### 12. ZUGRIFFSMODIFIKATOREN
```
             | Klasse | Paket | Kindklasse | Ueberall
private      |   Ja   |  Nein |    Nein    |   Nein
(default)    |   Ja   |   Ja  |    Nein    |   Nein
protected    |   Ja   |   Ja  |     Ja     |   Nein
public       |   Ja   |   Ja  |     Ja     |    Ja
```

### 13. HAEUFIGE FEHLER (Dont's!)
```
FALSCH: Animal a = new Animal();          // Abstrakt = nicht instanziierbar!
FALSCH: this.x = 5; super(name);          // super() MUSS erste Zeile sein!
FALSCH: Dog d = (Dog) cat;                // ClassCastException!
FALSCH: a.getWalkMin();                   // Animal kennt getWalkMin() nicht!
FALSCH: "\n" in toString                  // System.lineSeparator() nutzen!
FALSCH: if (o.getClass() == ...)          // instanceof nutzen in equals!

RICHTIG: Animal a = new Dog(...);         // Polymorphismus
RICHTIG: super(name, age, weight);        // Erste Zeile im Konstruktor
RICHTIG: if (a instanceof Dog) (Dog) a    // Erst pruefen, dann casten
RICHTIG: ((Dog) a).getWalkMin();          // Cast fuer Kind-Methoden
```
