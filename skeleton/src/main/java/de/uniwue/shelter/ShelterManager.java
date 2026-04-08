package de.uniwue.shelter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AUFGABE 4: Die Klasse ShelterManager (51 Punkte)
 *
 * Hier wendest du alle Vererbungskonzepte an:
 * - Polymorphismus: Animal-Referenzen die zu Dog/Cat dispatchen
 * - instanceof + Casting: Filtern nach Tierart
 * - Polymorphe Berechnungen: calculateFoodPerDay() auf gemischten Collections
 */
public class ShelterManager {

    // TODO: Datenstrukturen waehlen
    // TIPP: Map<String, Animal> fuer ID -> Tier Zuordnung
    // TIPP: Set<Animal> fuer adoptierte Tiere

    public ShelterManager() {
        throw new RuntimeException("not implemented");
    }

    public void addAnimal(Animal animal) {
        throw new RuntimeException("not implemented");
        // null -> NullPointerException
        // Doppelte ID -> IllegalArgumentException
        // Bereits adoptiert -> IllegalArgumentException
    }

    public Set<Animal> getAllAnimals() {
        throw new RuntimeException("not implemented");
    }

    public Set<Animal> getAvailableAnimals() {
        throw new RuntimeException("not implemented");
        // Alle nicht-adoptierten Tiere
    }

    public Set<Animal> getAdoptedAnimals() {
        throw new RuntimeException("not implemented");
    }

    public Optional<Animal> getAnimalById(String animalId) {
        throw new RuntimeException("not implemented");
        // TIPP: Optional.ofNullable(...)
    }

    public int getAnimalCount() {
        throw new RuntimeException("not implemented");
    }

    public int getAvailableCount() {
        throw new RuntimeException("not implemented");
    }

    // =================================================================
    // INSTANCEOF + CASTING
    // Hier musst du aus der Animal-Collection nur die Hunde filtern.
    // TIPP: for-Schleife, instanceof pruefen, dann casten
    // =================================================================

    public Set<Dog> getAllDogs() {
        throw new RuntimeException("not implemented");
    }

    public Set<Cat> getAllCats() {
        throw new RuntimeException("not implemented");
    }

    // =================================================================
    // POLYMORPHE BERECHNUNGEN
    // calculateFoodPerDay() wird auf Animal aufgerufen,
    // aber zur Laufzeit wird Dog.calculateFoodPerDay() oder
    // Cat.calculateFoodPerDay() ausgefuehrt!
    // =================================================================

    public double calculateTotalFoodPerDay() {
        throw new RuntimeException("not implemented");
        // Summe von calculateFoodPerDay() aller VERFUEGBAREN Tiere
    }

    public int calculateTotalWalkMinutes() {
        throw new RuntimeException("not implemented");
        // Summe von getWalkMinutesPerDay() aller VERFUEGBAREN HUNDE
        // TIPP: Du brauchst instanceof + Cast, weil nur Dog diese Methode hat!
    }

    // =================================================================
    // ADOPTION
    // =================================================================

    public void adoptAnimal(String animalId) {
        throw new RuntimeException("not implemented");
        // Tier per ID finden, adopt() aufrufen, zu adoptedAnimals hinzufuegen
        // IllegalArgumentException falls ID nicht existiert
    }

    public void returnAnimal(String animalId) {
        throw new RuntimeException("not implemented");
        // Tier per ID finden, returnToShelter() aufrufen, aus adoptedAnimals entfernen
        // IllegalArgumentException falls ID nicht existiert
    }

    // =================================================================
    // ANALYSE (Polymorphismus + Collections)
    // =================================================================

    public Map<String, List<Animal>> groupBySpecies() {
        throw new RuntimeException("not implemented");
        // Gruppiere nach getSpeciesName()
        // TIPP: computeIfAbsent() ist hilfreich
        // Ergebnis z.B.: {"Hund": [Rex, Bello], "Katze": [Mimi]}
    }

    public List<Animal> getAnimalsSortedByFood() {
        throw new RuntimeException("not implemented");
        // Alle VERFUEGBAREN Tiere sortiert nach calculateFoodPerDay() ABSTEIGEND
        // TIPP: sorted.sort((a, b) -> Double.compare(b.calcFood(), a.calcFood()))
    }

    public Optional<Animal> getHungriestAnimal() {
        throw new RuntimeException("not implemented");
        // Verfuegbares Tier mit hoechstem Futterbedarf
        // Leeres Optional falls keine verfuegbaren Tiere
    }

    @Override
    public String toString() {
        throw new RuntimeException("not implemented");
        // Format:
        // Tierheim (<availableCount> verfuegbar, <adoptedCount> adoptiert)
        // Verfuegbare Tiere:
        // \t<toString von Tier1>
        // Adoptierte Tiere:
        // \t<toString von Tier2>
        //
        // TIPPS:
        // - System.lineSeparator() fuer Zeilenumbruch
        // - \t fuer Einrueckung
        // - Tiere alphabetisch nach getAnimalId() sortieren
        // - Falls keine Tiere: \t- statt Tierliste
    }
}
