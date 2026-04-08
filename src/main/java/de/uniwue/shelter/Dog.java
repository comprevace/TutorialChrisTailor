package de.uniwue.shelter;

/**
 * AUFGABE 2: Die Klasse Dog (12 Punkte)
 *
 * VERERBUNGSKONZEPTE IN DIESER KLASSE:
 * - "extends": Dog erbt von Animal. Dog IST-EIN Animal.
 *   Das bedeutet: Dog hat automatisch alle Felder und Methoden von Animal
 *   (name, age, weight, adopted, getName(), adopt(), etc.)
 *
 * - "super()": Im Konstruktor MUSS super(name, age, weight) aufgerufen werden.
 *   Das ist die ERSTE Zeile im Konstruktor! Damit wird der Animal-Konstruktor
 *   ausgefuehrt (inklusive aller Validierungen).
 *
 * - "@Override": Dog MUSS getSpeciesName() und calculateFoodPerDay() implementieren,
 *   weil diese in Animal als abstract deklariert sind.
 *   Dog KANN toString() ueberschreiben fuer eine detailliertere Ausgabe.
 *
 * - Eigene Felder: Dog hat zusaetzlich walkMinutesPerDay und trained,
 *   die Animal nicht hat. Diese sind private (nicht protected),
 *   weil keine Klasse von Dog erben muss.
 */
public class Dog extends Animal {

    // =========================================================================
    // EIGENE FELDER (private, nicht protected)
    // Diese Felder existieren NUR in Dog, nicht in Animal.
    // =========================================================================
    private int walkMinutesPerDay;
    private boolean trained;

    // =========================================================================
    // KONSTRUKTOR mit super()
    // super(name, age, weight) MUSS die erste Anweisung sein!
    // Dadurch wird der Animal-Konstruktor aufgerufen, der:
    // 1. name, age, weight validiert
    // 2. die Felder setzt
    // 3. adopted = false setzt
    // Danach koennen wir die Dog-spezifischen Felder setzen.
    // =========================================================================
    public Dog(String name, int age, double weight, int walkMinutesPerDay, boolean trained) {
        super(name, age, weight);  // <-- MUSS erste Zeile sein!

        if (walkMinutesPerDay <= 0) {
            throw new IllegalArgumentException("Auslaufzeit muss groesser als 0 sein");
        }

        this.walkMinutesPerDay = walkMinutesPerDay;
        this.trained = trained;
    }

    // =========================================================================
    // IMPLEMENTIERUNG DER ABSTRAKTEN METHODEN
    // Animal verlangt, dass jede Kindklasse diese Methoden implementiert.
    // Ohne @Override und Implementierung wuerde Dog NICHT kompilieren
    // (es sei denn, Dog waere selbst abstract).
    // =========================================================================

    @Override
    public String getSpeciesName() {
        return "Hund";
    }

    @Override
    public double calculateFoodPerDay() {
        // Trainierte Hunde fressen normal, untrainierte 20% mehr (Stressfressen)
        // weight ist protected in Animal, daher direkt zugreifbar!
        return weight * 0.025 * (trained ? 1.0 : 1.2);
    }

    // =========================================================================
    // EIGENE GETTER
    // Diese Methoden existieren NUR in Dog.
    // Man kann sie NUR aufrufen, wenn man eine Dog-Referenz hat:
    //   Dog d = new Dog(...);
    //   d.getWalkMinutesPerDay();  // OK
    //
    //   Animal a = new Dog(...);
    //   a.getWalkMinutesPerDay();  // KOMPILIERUNGSFEHLER!
    //   ((Dog) a).getWalkMinutesPerDay();  // OK nach Cast
    // =========================================================================
    public int getWalkMinutesPerDay() {
        return walkMinutesPerDay;
    }

    public boolean isTrained() {
        return trained;
    }

    /**
     * Trainiert den Hund. Kann nur einmal aufgerufen werden.
     */
    public void train() {
        if (trained) {
            throw new IllegalStateException("Hund ist bereits trainiert");
        }
        trained = true;
    }

    // =========================================================================
    // TOSTRING UEBERSCHREIBEN
    // Dog ueberschreibt toString() von Animal, um mehr Details anzuzeigen.
    // Wir koennten auch super.toString() aufrufen und ergaenzen,
    // aber hier schreiben wir einen komplett eigenen String.
    // =========================================================================
    @Override
    public String toString() {
        return "Hund: " + name + " (Alter: " + age + ", Gewicht: " + weight
                + "kg, Auslauf: " + walkMinutesPerDay + "min/Tag)";
    }
}
