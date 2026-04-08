package de.uniwue.shelter;

/**
 * AUFGABE 3: Die Klasse Cat (12 Punkte)
 *
 * VERERBUNGSKONZEPTE IN DIESER KLASSE:
 * - Gleiche Mechanismen wie Dog: extends, super(), @Override
 * - ABER: Andere Implementierung der abstrakten Methoden!
 *   Das ist der Kern von Polymorphismus: Gleiche Methode, anderes Verhalten.
 *
 * VERGLEICH Dog vs Cat:
 * - getSpeciesName(): Dog gibt "Hund" zurueck, Cat gibt "Katze" zurueck
 * - calculateFoodPerDay(): Dog nutzt weight*0.025, Cat nutzt weight*0.03 oder 0.04
 * - toString(): Beide ueberschreiben, aber mit unterschiedlichen Details
 *
 * Wenn man spaeter Animal-Referenzen nutzt:
 *   Animal a = new Cat("Mimi", 3, 4.0, true);
 *   a.calculateFoodPerDay();  // Ruft Cat.calculateFoodPerDay() auf! (Polymorphismus)
 */
public class Cat extends Animal {

    // =========================================================================
    // EIGENE FELDER
    // =========================================================================
    private boolean indoor;
    private int livesRemaining;

    // =========================================================================
    // KONSTRUKTOR mit super()
    // Gleich wie bei Dog: super() als erste Zeile.
    // livesRemaining wird IMMER mit 9 initialisiert (Katzen haben 9 Leben).
    // =========================================================================
    public Cat(String name, int age, double weight, boolean indoor) {
        super(name, age, weight);  // <-- Ruft Animal-Konstruktor auf
        this.indoor = indoor;
        this.livesRemaining = 9;
    }

    // =========================================================================
    // IMPLEMENTIERUNG DER ABSTRAKTEN METHODEN
    // Gleiche Methodensignaturen wie in Dog, aber ANDERE Implementierung!
    // =========================================================================

    @Override
    public String getSpeciesName() {
        return "Katze";
    }

    @Override
    public double calculateFoodPerDay() {
        // Indoor-Katzen brauchen weniger Futter als Outdoor-Katzen
        // weight ist protected in Animal -> direkt zugreifbar
        if (indoor) {
            return weight * 0.03;
        } else {
            return weight * 0.04;
        }
    }

    // =========================================================================
    // EIGENE METHODEN
    // Nur ueber Cat-Referenzen oder nach instanceof-Cast erreichbar.
    // =========================================================================
    public boolean isIndoor() {
        return indoor;
    }

    public int getLivesRemaining() {
        return livesRemaining;
    }

    /**
     * Die Katze verliert ein Leben.
     * Wirft IllegalStateException wenn keine Leben mehr uebrig sind.
     */
    public void loseLife() {
        if (livesRemaining <= 0) {
            throw new IllegalStateException("Katze hat keine Leben mehr uebrig");
        }
        livesRemaining--;
    }

    // =========================================================================
    // TOSTRING UEBERSCHREIBEN
    // Eigenes Format mit Indoor-Status und verbleibenden Leben.
    // =========================================================================
    @Override
    public String toString() {
        return "Katze: " + name + " (Alter: " + age + ", Gewicht: " + weight
                + "kg, Indoor: " + (indoor ? "ja" : "nein")
                + ", Leben: " + livesRemaining + ")";
    }
}
