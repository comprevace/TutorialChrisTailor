package de.uniwue.shelter;

import java.util.Objects;

/**
 * AUFGABE 1: Die abstrakte Klasse Animal (15 Punkte)
 *
 * VERERBUNGSKONZEPTE IN DIESER KLASSE:
 * - "abstract class": Diese Klasse kann NICHT direkt instanziiert werden.
 *   Man kann NICHT schreiben: Animal a = new Animal("Rex", 3, 10.0);
 *   Stattdessen muss man eine Kindklasse erstellen: Animal a = new Dog("Rex", 3, 10.0, 60, true);
 *
 * - "abstract" Methoden: getSpeciesName() und calculateFoodPerDay() haben KEINEN Body.
 *   Jede nicht-abstrakte Kindklasse MUSS diese Methoden implementieren.
 *
 * - "protected" Felder: name, age, weight, adopted sind protected, damit Kindklassen
 *   direkt darauf zugreifen koennen (ohne Getter).
 *
 * - "final" Methode: getAnimalId() ist final -- Kindklassen duerfen sie NICHT ueberschreiben.
 *   Interessant: Eine final-Methode kann intern eine abstrakte Methode aufrufen!
 */
public abstract class Animal {

    // =========================================================================
    // PROTECTED FELDER
    // "protected" bedeutet: sichtbar in dieser Klasse, im gleichen Paket,
    // und in ALLEN Kindklassen (auch in anderen Paketen).
    // Wir nutzen protected statt private, damit Dog und Cat direkt auf
    // diese Felder zugreifen koennen.
    // =========================================================================
    protected String name;
    protected int age;
    protected double weight;
    protected boolean adopted;

    // =========================================================================
    // KONSTRUKTOR
    // Auch abstrakte Klassen haben Konstruktoren! Sie werden von Kindklassen
    // ueber super() aufgerufen. Man kann sie aber NICHT direkt mit "new" nutzen.
    // =========================================================================
    public Animal(String name, int age, double weight) {
        if (name == null) {
            throw new NullPointerException("Name darf nicht null sein");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name darf nicht leer sein");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Alter darf nicht negativ sein");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Gewicht muss groesser als 0 sein");
        }
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.adopted = false;
    }

    // =========================================================================
    // ABSTRAKTE METHODEN
    // Diese Methoden haben KEINEN Body (kein { ... }).
    // Jede Kindklasse (Dog, Cat) MUSS sie implementieren.
    // Das ist der Vertrag: "Jedes Tier muss sagen koennen, welche Art es ist
    // und wie viel Futter es pro Tag braucht."
    // =========================================================================

    /**
     * Gibt den Artnamen zurueck (z.B. "Hund", "Katze").
     * MUSS von jeder Kindklasse implementiert werden.
     */
    public abstract String getSpeciesName();

    /**
     * Berechnet den taeglichen Futterbedarf in kg.
     * MUSS von jeder Kindklasse implementiert werden.
     * Jede Art hat eine andere Formel!
     */
    public abstract double calculateFoodPerDay();

    // =========================================================================
    // GETTER (konkrete Methoden, die vererbt werden)
    // =========================================================================
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isAdopted() {
        return adopted;
    }

    // =========================================================================
    // ADOPTIONS-METHODEN
    // Diese konkreten Methoden koennen von Kindklassen geerbt werden,
    // ohne sie ueberschreiben zu muessen.
    // =========================================================================
    public void adopt() {
        if (adopted) {
            throw new IllegalStateException("Tier ist bereits adoptiert");
        }
        adopted = true;
    }

    public void returnToShelter() {
        if (!adopted) {
            throw new IllegalStateException("Tier ist nicht adoptiert");
        }
        adopted = false;
    }

    // =========================================================================
    // FINAL-METHODE
    // "final" bedeutet: Kindklassen duerfen diese Methode NICHT ueberschreiben.
    // Das schuetzt die ID-Format-Logik -- alle Tiere muessen das gleiche
    // ID-Format verwenden.
    //
    // BEACHTE: getAnimalId() ruft getSpeciesName() auf, was abstrakt ist!
    // Zur Laufzeit wird die Implementierung der jeweiligen Kindklasse aufgerufen.
    // Ein Dog gibt "Hund-Rex-3" zurueck, eine Cat "Katze-Mimi-5".
    // =========================================================================
    public final String getAnimalId() {
        return getSpeciesName() + "-" + name + "-" + age;
    }

    // =========================================================================
    // EQUALS und HASHCODE
    // Hier wird Polymorphismus in equals genutzt:
    // Wir pruefen mit instanceof ob das andere Objekt ein Animal ist,
    // und vergleichen dann name UND getSpeciesName().
    // So ist ein Hund "Rex" NICHT gleich einer Katze "Rex".
    // =========================================================================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return Objects.equals(name, animal.name)
                && Objects.equals(getSpeciesName(), animal.getSpeciesName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, getSpeciesName());
    }

    // =========================================================================
    // TOSTRING
    // Auch diese Methode nutzt Polymorphismus: getSpeciesName() gibt zur
    // Laufzeit den richtigen Artnamen zurueck.
    // Kindklassen koennen toString() ueberschreiben fuer mehr Details.
    // =========================================================================
    @Override
    public String toString() {
        return getSpeciesName() + ": " + name + " (Alter: " + age + ", Gewicht: " + weight + "kg)";
    }
}
