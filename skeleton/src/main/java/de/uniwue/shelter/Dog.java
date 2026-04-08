package de.uniwue.shelter;

/**
 * AUFGABE 2: Die Klasse Dog (12 Punkte)
 *
 * TODO: Lasse Dog von Animal erben (welches Keyword?)
 * TODO: Rufe im Konstruktor den Elternkonstruktor auf (welches Keyword? Welche Zeile?)
 * TODO: Implementiere die abstrakten Methoden aus Animal
 * TODO: Fuege eigene Felder und Methoden hinzu
 */
public class Dog {

    // TODO: Eigene Felder: walkMinutesPerDay (int), trained (boolean)
    //       Welcher Zugriffsmodifikator? (Erbt jemand von Dog?)

    // TODO: Konstruktor
    //       Parameter: String name, int age, double weight, int walkMinutesPerDay, boolean trained
    //       TIPP: Was muss in der ERSTEN Zeile stehen?
    public Dog(String name, int age, double weight, int walkMinutesPerDay, boolean trained) {
        throw new RuntimeException("not implemented");
    }

    // TODO: getSpeciesName() implementieren -> gibt "Hund" zurueck
    //       Welche Annotation brauchst du?

    // TODO: calculateFoodPerDay() implementieren
    //       Formel: weight * 0.025 * (trained ? 1.0 : 1.2)
    //       TIPP: weight ist protected in Animal, du kannst direkt darauf zugreifen!

    public int getWalkMinutesPerDay() {
        throw new RuntimeException("not implemented");
    }

    public boolean isTrained() {
        throw new RuntimeException("not implemented");
    }

    public void train() {
        throw new RuntimeException("not implemented");
        // Setzt trained auf true.
        // Wirft IllegalStateException falls bereits trainiert.
    }

    // TODO: toString() ueberschreiben
    // Format: "Hund: <name> (Alter: <age>, Gewicht: <weight>kg, Auslauf: <walkMinutesPerDay>min/Tag)"
}
