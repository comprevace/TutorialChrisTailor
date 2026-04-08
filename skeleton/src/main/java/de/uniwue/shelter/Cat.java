package de.uniwue.shelter;

/**
 * AUFGABE 3: Die Klasse Cat (12 Punkte)
 *
 * TODO: Lasse Cat von Animal erben
 * TODO: Gleiche Mechanismen wie Dog, aber ANDERE Implementierung!
 */
public class Cat {

    // TODO: Eigene Felder: indoor (boolean), livesRemaining (int, startet bei 9)

    // TODO: Konstruktor
    //       Parameter: String name, int age, double weight, boolean indoor
    //       TIPP: super() nicht vergessen! livesRemaining immer 9.
    public Cat(String name, int age, double weight, boolean indoor) {
        throw new RuntimeException("not implemented");
    }

    // TODO: getSpeciesName() -> "Katze"

    // TODO: calculateFoodPerDay()
    //       Indoor: weight * 0.03
    //       Outdoor: weight * 0.04

    public boolean isIndoor() {
        throw new RuntimeException("not implemented");
    }

    public int getLivesRemaining() {
        throw new RuntimeException("not implemented");
    }

    public void loseLife() {
        throw new RuntimeException("not implemented");
        // livesRemaining um 1 verringern.
        // IllegalStateException falls livesRemaining <= 0.
    }

    // TODO: toString() ueberschreiben
    // Format: "Katze: <name> (Alter: <age>, Gewicht: <weight>kg, Indoor: <ja/nein>, Leben: <livesRemaining>)"
}
