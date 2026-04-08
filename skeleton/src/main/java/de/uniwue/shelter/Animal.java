package de.uniwue.shelter;

import java.util.Objects;

/**
 * AUFGABE 1: Die abstrakte Klasse Animal (15 Punkte)
 *
 * TODO: Mache diese Klasse abstrakt
 * TODO: Erstelle protected Felder: name (String), age (int), weight (double), adopted (boolean)
 * TODO: Implementiere den Konstruktor mit Validierung
 * TODO: Erstelle abstrakte Methoden: getSpeciesName(), calculateFoodPerDay()
 * TODO: Erstelle Getter, adopt(), returnToShelter()
 * TODO: Erstelle eine FINAL Methode getAnimalId()
 * TODO: Ueberschreibe equals(), hashCode(), toString()
 */
public class Animal {

    // TODO: Felder hier (welcher Zugriffsmodifikator fuer Vererbung?)

    public Animal(String name, int age, double weight) {
        throw new RuntimeException("not implemented");
    }

    // TODO: Abstrakte Methoden hier
    // public ??? getSpeciesName();
    // public ??? calculateFoodPerDay();

    public String getName() {
        throw new RuntimeException("not implemented");
    }

    public int getAge() {
        throw new RuntimeException("not implemented");
    }

    public double getWeight() {
        throw new RuntimeException("not implemented");
    }

    public boolean isAdopted() {
        throw new RuntimeException("not implemented");
    }

    public void adopt() {
        throw new RuntimeException("not implemented");
    }

    public void returnToShelter() {
        throw new RuntimeException("not implemented");
    }

    // TODO: Wie muss diese Methode deklariert werden,
    //       damit Kindklassen sie NICHT ueberschreiben koennen?
    //       Format: "<SpeciesName>-<name>-<age>"
    public String getAnimalId() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public boolean equals(Object o) {
        throw new RuntimeException("not implemented");
        // TIPP: Vergleiche name UND getSpeciesName()
        // TIPP: Nutze instanceof, NICHT getClass()
    }

    @Override
    public int hashCode() {
        throw new RuntimeException("not implemented");
        // TIPP: Muss konsistent mit equals() sein
    }

    @Override
    public String toString() {
        throw new RuntimeException("not implemented");
        // Format: "<SpeciesName>: <name> (Alter: <age>, Gewicht: <weight>kg)"
    }
}
