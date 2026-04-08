package de.uniwue.shelter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AUFGABE 4: Die Klasse ShelterManager (51 Punkte)
 *
 * VERERBUNGSKONZEPTE IN DIESER KLASSE:
 *
 * 1. POLYMORPHISMUS IN COLLECTIONS:
 *    Die Map speichert Animal-Referenzen, aber die tatsaechlichen Objekte
 *    sind Dog oder Cat. Wenn wir calculateFoodPerDay() aufrufen, wird
 *    automatisch die richtige Implementierung gewaehlt!
 *
 * 2. INSTANCEOF + TYPE CASTING:
 *    Um alle Hunde zu finden, muessen wir jedes Animal pruefen:
 *    if (animal instanceof Dog) { Dog d = (Dog) animal; ... }
 *
 * 3. POLYMORPHE METHODEN ALS MAP-KEYS:
 *    groupBySpecies() nutzt getSpeciesName() als Schluessel.
 *    Obwohl wir Animal-Referenzen haben, gibt jedes Objekt den
 *    richtigen Artnamen zurueck.
 */
public class ShelterManager {

    // =========================================================================
    // INSTANZVARIABLEN
    // animals speichert ALLE Tiere (als Animal-Referenzen!)
    // adoptedAnimals ist ein separates Set fuer schnellen Zugriff
    // =========================================================================
    private Map<String, Animal> animals;       // animalId -> Animal
    private Set<Animal> adoptedAnimals;

    public ShelterManager() {
        this.animals = new HashMap<>();
        this.adoptedAnimals = new HashSet<>();
    }

    // =========================================================================
    // TIER HINZUFUEGEN
    // Der Parameter ist vom Typ Animal -- das ist Polymorphismus!
    // Man kann addAnimal(new Dog(...)) oder addAnimal(new Cat(...)) aufrufen.
    // =========================================================================
    public void addAnimal(Animal animal) {
        if (animal == null) {
            throw new NullPointerException("Tier darf nicht null sein");
        }
        String id = animal.getAnimalId();
        if (animals.containsKey(id)) {
            throw new IllegalArgumentException("Tier mit ID " + id + " existiert bereits");
        }
        if (animal.isAdopted()) {
            throw new IllegalArgumentException("Tier ist bereits adoptiert");
        }
        animals.put(id, animal);
    }

    // =========================================================================
    // ABFRAGEN
    // getAllAnimals() gibt ein Set<Animal> zurueck -- die tatsaechlichen
    // Objekte sind Dog/Cat, aber der Rueckgabetyp ist Animal.
    // =========================================================================
    public Set<Animal> getAllAnimals() {
        return new HashSet<>(animals.values());
    }

    public Set<Animal> getAvailableAnimals() {
        Set<Animal> available = new HashSet<>();
        for (Animal animal : animals.values()) {
            if (!animal.isAdopted()) {
                available.add(animal);
            }
        }
        return available;
    }

    public Set<Animal> getAdoptedAnimals() {
        return new HashSet<>(adoptedAnimals);
    }

    public Optional<Animal> getAnimalById(String animalId) {
        return Optional.ofNullable(animals.get(animalId));
    }

    public int getAnimalCount() {
        return animals.size();
    }

    public int getAvailableCount() {
        return getAvailableAnimals().size();
    }

    // =========================================================================
    // INSTANCEOF + TYPE CASTING
    // Hier wird es spannend! Wir haben eine Map<String, Animal>, aber
    // wollen nur die Hunde zurueckgeben.
    //
    // Problem: Animal hat keine Methode getWalkMinutesPerDay().
    //          Nur Dog hat diese Methode.
    //
    // Loesung: 1. Pruefen ob das Animal ein Dog ist: animal instanceof Dog
    //          2. Casten: Dog dog = (Dog) animal;
    //          3. Jetzt koennen wir Dog-spezifische Methoden aufrufen!
    // =========================================================================

    /**
     * Gibt alle Hunde im Tierheim zurueck.
     * Nutzt instanceof um aus der Animal-Collection nur Dogs zu filtern.
     */
    public Set<Dog> getAllDogs() {
        Set<Dog> dogs = new HashSet<>();
        for (Animal animal : animals.values()) {
            // instanceof prueft den TATSAECHLICHEN Typ des Objekts zur Laufzeit
            if (animal instanceof Dog) {
                // Type Cast: Animal -> Dog
                // Das ist sicher, weil wir vorher mit instanceof geprueft haben
                Dog dog = (Dog) animal;
                dogs.add(dog);
            }
        }
        return dogs;
    }

    /**
     * Gibt alle Katzen im Tierheim zurueck.
     * Gleiche Logik wie getAllDogs(), aber fuer Cat.
     */
    public Set<Cat> getAllCats() {
        Set<Cat> cats = new HashSet<>();
        for (Animal animal : animals.values()) {
            if (animal instanceof Cat) {
                Cat cat = (Cat) animal;
                cats.add(cat);
            }
        }
        return cats;
    }

    // =========================================================================
    // POLYMORPHE BERECHNUNGEN
    // calculateTotalFoodPerDay() ruft calculateFoodPerDay() auf jeder
    // Animal-Referenz auf. Dank Polymorphismus wird automatisch die
    // RICHTIGE Implementierung aufgerufen:
    //   - Fuer Dog: weight * 0.025 * (trained ? 1.0 : 1.2)
    //   - Fuer Cat: weight * 0.03 (indoor) oder weight * 0.04 (outdoor)
    //
    // WIR muessen nicht wissen, welcher Typ das Tier ist!
    // Java waehlt zur LAUFZEIT die richtige Methode.
    // =========================================================================

    /**
     * Berechnet den gesamten taeglichen Futterbedarf aller verfuegbaren Tiere.
     * POLYMORPHISMUS: calculateFoodPerDay() dispatcht automatisch zur richtigen
     * Implementierung, je nachdem ob das Tier ein Dog oder Cat ist.
     */
    public double calculateTotalFoodPerDay() {
        double total = 0;
        for (Animal animal : animals.values()) {
            if (!animal.isAdopted()) {
                // Hier passiert Polymorphismus!
                // animal.calculateFoodPerDay() ruft Dog.calculateFoodPerDay()
                // oder Cat.calculateFoodPerDay() auf, je nach echtem Typ
                total += animal.calculateFoodPerDay();
            }
        }
        return total;
    }

    /**
     * Berechnet die gesamte taegliche Auslaufzeit aller verfuegbaren Hunde.
     * Hier brauchen wir instanceof + Cast, weil getWalkMinutesPerDay()
     * NUR in Dog existiert, nicht in Animal.
     */
    public int calculateTotalWalkMinutes() {
        int total = 0;
        for (Animal animal : animals.values()) {
            if (!animal.isAdopted() && animal instanceof Dog) {
                Dog dog = (Dog) animal;
                total += dog.getWalkMinutesPerDay();
            }
        }
        return total;
    }

    // =========================================================================
    // ADOPTION
    // adoptAnimal() findet ein Tier per ID und ruft adopt() auf.
    // adopt() ist in Animal definiert und wird vererbt --
    // Dog und Cat haben sie automatisch.
    // =========================================================================

    public void adoptAnimal(String animalId) {
        Animal animal = animals.get(animalId);
        if (animal == null) {
            throw new IllegalArgumentException("Kein Tier mit ID " + animalId + " gefunden");
        }
        // adopt() wirft IllegalStateException wenn bereits adoptiert
        animal.adopt();
        adoptedAnimals.add(animal);
    }

    public void returnAnimal(String animalId) {
        Animal animal = animals.get(animalId);
        if (animal == null) {
            throw new IllegalArgumentException("Kein Tier mit ID " + animalId + " gefunden");
        }
        // returnToShelter() wirft IllegalStateException wenn nicht adoptiert
        animal.returnToShelter();
        adoptedAnimals.remove(animal);
    }

    // =========================================================================
    // ANALYSE-METHODEN (Polymorphismus + Collections)
    // =========================================================================

    /**
     * Gruppiert alle Tiere nach Artname.
     * POLYMORPHISMUS: getSpeciesName() gibt fuer jeden Tiertyp den richtigen
     * Artnamen zurueck, obwohl wir nur Animal-Referenzen haben.
     *
     * Ergebnis z.B.: {"Hund" -> [Rex, Bello], "Katze" -> [Mimi, Luna]}
     */
    public Map<String, List<Animal>> groupBySpecies() {
        Map<String, List<Animal>> groups = new HashMap<>();
        for (Animal animal : animals.values()) {
            // getSpeciesName() wird polymorph aufgeloest!
            String species = animal.getSpeciesName();
            groups.computeIfAbsent(species, k -> new ArrayList<>()).add(animal);
        }
        return groups;
    }

    /**
     * Gibt alle verfuegbaren Tiere sortiert nach Futterbedarf (absteigend) zurueck.
     * POLYMORPHISMUS: calculateFoodPerDay() wird fuer jedes Tier korrekt aufgeloest.
     */
    public List<Animal> getAnimalsSortedByFood() {
        List<Animal> available = new ArrayList<>(getAvailableAnimals());
        available.sort((a, b) -> Double.compare(b.calculateFoodPerDay(), a.calculateFoodPerDay()));
        return available;
    }

    /**
     * Gibt das verfuegbare Tier mit dem hoechsten Futterbedarf zurueck.
     */
    public Optional<Animal> getHungriestAnimal() {
        return getAvailableAnimals().stream()
                .max(Comparator.comparingDouble(Animal::calculateFoodPerDay));
    }

    // =========================================================================
    // TOSTRING
    // Auch hier wird Polymorphismus genutzt: animal.toString() gibt fuer
    // jeden Tiertyp die richtige Darstellung zurueck.
    // =========================================================================
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int availableCount = getAvailableCount();
        int adoptedCount = adoptedAnimals.size();

        sb.append("Tierheim (").append(availableCount).append(" verfuegbar, ")
                .append(adoptedCount).append(" adoptiert)");

        sb.append(System.lineSeparator()).append("Verfuegbare Tiere:");
        List<Animal> available = getAvailableAnimals().stream()
                .sorted(Comparator.comparing(Animal::getAnimalId))
                .collect(Collectors.toList());
        if (available.isEmpty()) {
            sb.append(System.lineSeparator()).append("\t-");
        } else {
            for (Animal animal : available) {
                sb.append(System.lineSeparator()).append("\t").append(animal.toString());
            }
        }

        sb.append(System.lineSeparator()).append("Adoptierte Tiere:");
        List<Animal> adopted = adoptedAnimals.stream()
                .sorted(Comparator.comparing(Animal::getAnimalId))
                .collect(Collectors.toList());
        if (adopted.isEmpty()) {
            sb.append(System.lineSeparator()).append("\t-");
        } else {
            for (Animal animal : adopted) {
                sb.append(System.lineSeparator()).append("\t").append(animal.toString());
            }
        }

        return sb.toString();
    }
}
