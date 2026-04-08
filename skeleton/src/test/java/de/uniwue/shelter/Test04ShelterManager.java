package de.uniwue.shelter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests fuer Aufgabe 4: ShelterManager (51 Punkte)
 */
public class Test04ShelterManager {

    private ShelterManager manager;
    private Dog rex;
    private Dog bello;
    private Cat mimi;
    private Cat luna;

    @BeforeEach
    public void setUp() {
        manager = new ShelterManager();
        rex = new Dog("Rex", 3, 10.0, 60, true);       // Futter: 10*0.025*1.0 = 0.25
        bello = new Dog("Bello", 1, 20.0, 90, false);   // Futter: 20*0.025*1.2 = 0.60
        mimi = new Cat("Mimi", 3, 4.0, true);           // Futter: 4*0.03 = 0.12
        luna = new Cat("Luna", 5, 5.0, false);           // Futter: 5*0.04 = 0.20
    }

    // === test01Constructor (1 Punkt) ===
    @Test
    public void test01Constructor() {
        ShelterManager sm = new ShelterManager();
        assertEquals(0, sm.getAnimalCount());
        assertEquals(0, sm.getAvailableCount());
        assertTrue(sm.getAllAnimals().isEmpty());
    }

    // === test02AddAnimal (2 Punkte) ===
    @Test
    public void test02AddAnimal() {
        manager.addAnimal(rex);
        assertEquals(1, manager.getAnimalCount());

        manager.addAnimal(mimi);
        assertEquals(2, manager.getAnimalCount());

        manager.addAnimal(bello);
        manager.addAnimal(luna);
        assertEquals(4, manager.getAnimalCount());
    }

    // === test03AddAnimalExceptions (2 Punkte) ===
    @Test
    public void test03AddAnimalExceptions() {
        // null -> NullPointerException
        assertThrows(NullPointerException.class, () -> manager.addAnimal(null));

        manager.addAnimal(rex);

        // Doppelte ID -> IllegalArgumentException
        Dog rexClone = new Dog("Rex", 3, 15.0, 45, false);  // Gleiche ID: Hund-Rex-3
        assertThrows(IllegalArgumentException.class, () -> manager.addAnimal(rexClone));

        // Bereits adoptiertes Tier -> IllegalArgumentException
        Dog adoptedDog = new Dog("Fido", 2, 8.0, 40, true);
        adoptedDog.adopt();
        assertThrows(IllegalArgumentException.class, () -> manager.addAnimal(adoptedDog));
    }

    // === test04GetAllAnimals (2 Punkte) ===
    @Test
    public void test04GetAllAnimals() {
        manager.addAnimal(rex);
        manager.addAnimal(mimi);
        manager.addAnimal(bello);

        Set<Animal> all = manager.getAllAnimals();
        assertEquals(3, all.size());
        assertTrue(all.contains(rex));
        assertTrue(all.contains(mimi));
        assertTrue(all.contains(bello));
    }

    // === test05GetAvailableAnimals (2 Punkte) ===
    @Test
    public void test05GetAvailableAnimals() {
        manager.addAnimal(rex);
        manager.addAnimal(mimi);
        manager.addAnimal(bello);

        // Alle 3 verfuegbar
        assertEquals(3, manager.getAvailableAnimals().size());

        // Rex adoptieren
        manager.adoptAnimal("Hund-Rex-3");
        assertEquals(2, manager.getAvailableAnimals().size());
        assertFalse(manager.getAvailableAnimals().contains(rex));

        // Adoptierte pruefen
        assertEquals(1, manager.getAdoptedAnimals().size());
        assertTrue(manager.getAdoptedAnimals().contains(rex));
    }

    // === test06GetAnimalById (2 Punkte) ===
    @Test
    public void test06GetAnimalById() {
        manager.addAnimal(rex);
        manager.addAnimal(mimi);

        // Existierende IDs
        assertTrue(manager.getAnimalById("Hund-Rex-3").isPresent());
        assertEquals(rex, manager.getAnimalById("Hund-Rex-3").get());

        assertTrue(manager.getAnimalById("Katze-Mimi-3").isPresent());
        assertEquals(mimi, manager.getAnimalById("Katze-Mimi-3").get());

        // Nicht existierende ID
        assertFalse(manager.getAnimalById("Hund-Fido-1").isPresent());
        assertTrue(manager.getAnimalById("Hund-Fido-1").isEmpty());
    }

    // === test07GetAnimalCount (1.5 Punkte) ===
    @Test
    public void test07GetAnimalCount() {
        assertEquals(0, manager.getAnimalCount());
        assertEquals(0, manager.getAvailableCount());

        manager.addAnimal(rex);
        manager.addAnimal(mimi);
        assertEquals(2, manager.getAnimalCount());
        assertEquals(2, manager.getAvailableCount());

        manager.adoptAnimal("Hund-Rex-3");
        assertEquals(2, manager.getAnimalCount());      // Gesamtzahl bleibt
        assertEquals(1, manager.getAvailableCount());    // Verfuegbare sinkt
    }

    // === test08GetAllDogs (3.5 Punkte) ===
    @Test
    public void test08GetAllDogs() {
        manager.addAnimal(rex);
        manager.addAnimal(bello);
        manager.addAnimal(mimi);
        manager.addAnimal(luna);

        // getAllDogs() muss instanceof nutzen um nur Hunde zu filtern
        Set<Dog> dogs = manager.getAllDogs();
        assertEquals(2, dogs.size());
        assertTrue(dogs.contains(rex));
        assertTrue(dogs.contains(bello));

        // Katzen duerfen NICHT enthalten sein
        for (Dog dog : dogs) {
            assertInstanceOf(Dog.class, dog);
        }

        // Leeres Tierheim -> leeres Set
        ShelterManager empty = new ShelterManager();
        assertTrue(empty.getAllDogs().isEmpty());

        // Nur Katzen -> leeres Dog-Set
        ShelterManager catsOnly = new ShelterManager();
        catsOnly.addAnimal(new Cat("Felix", 2, 3.0, true));
        assertTrue(catsOnly.getAllDogs().isEmpty());
    }

    // === test09GetAllCats (3.5 Punkte) ===
    @Test
    public void test09GetAllCats() {
        manager.addAnimal(rex);
        manager.addAnimal(bello);
        manager.addAnimal(mimi);
        manager.addAnimal(luna);

        Set<Cat> cats = manager.getAllCats();
        assertEquals(2, cats.size());
        assertTrue(cats.contains(mimi));
        assertTrue(cats.contains(luna));

        for (Cat cat : cats) {
            assertInstanceOf(Cat.class, cat);
        }
    }

    // === test10CalculateTotalFoodPerDay (4 Punkte) ===
    @Test
    public void test10CalculateTotalFoodPerDay() {
        // POLYMORPHISMUS-TEST:
        // calculateFoodPerDay() muss fuer Dogs und Cats unterschiedlich berechnet werden,
        // obwohl der Manager nur Animal-Referenzen kennt!

        manager.addAnimal(rex);     // 10*0.025*1.0 = 0.25
        manager.addAnimal(bello);   // 20*0.025*1.2 = 0.60
        manager.addAnimal(mimi);    // 4*0.03 = 0.12
        manager.addAnimal(luna);    // 5*0.04 = 0.20

        double expected = 0.25 + 0.60 + 0.12 + 0.20;  // = 1.17
        assertEquals(expected, manager.calculateTotalFoodPerDay(), 0.001);

        // Nach Adoption: adoptiertes Tier wird nicht mitgezaehlt
        manager.adoptAnimal("Hund-Rex-3");
        double expectedAfterAdopt = 0.60 + 0.12 + 0.20;  // = 0.92
        assertEquals(expectedAfterAdopt, manager.calculateTotalFoodPerDay(), 0.001);

        // Leeres Tierheim
        ShelterManager empty = new ShelterManager();
        assertEquals(0.0, empty.calculateTotalFoodPerDay(), 0.001);
    }

    // === test11CalculateTotalWalkMinutes (3.5 Punkte) ===
    @Test
    public void test11CalculateTotalWalkMinutes() {
        // INSTANCEOF-TEST:
        // Nur Hunde haben walkMinutesPerDay, daher braucht man instanceof + Cast

        manager.addAnimal(rex);     // 60 min
        manager.addAnimal(bello);   // 90 min
        manager.addAnimal(mimi);    // Katze, zaehlt nicht
        manager.addAnimal(luna);    // Katze, zaehlt nicht

        assertEquals(60 + 90, manager.calculateTotalWalkMinutes());

        // Nach Adoption eines Hundes
        manager.adoptAnimal("Hund-Rex-3");
        assertEquals(90, manager.calculateTotalWalkMinutes());

        // Alle Hunde adoptiert
        manager.adoptAnimal("Hund-Bello-1");
        assertEquals(0, manager.calculateTotalWalkMinutes());
    }

    // === test12AdoptAnimal (3 Punkte) ===
    @Test
    public void test12AdoptAnimal() {
        manager.addAnimal(rex);
        manager.addAnimal(mimi);

        assertFalse(rex.isAdopted());
        manager.adoptAnimal("Hund-Rex-3");
        assertTrue(rex.isAdopted());

        // Adoptierte in adoptedAnimals
        assertTrue(manager.getAdoptedAnimals().contains(rex));
        assertFalse(manager.getAvailableAnimals().contains(rex));

        // Mimi immer noch verfuegbar
        assertFalse(mimi.isAdopted());
        assertTrue(manager.getAvailableAnimals().contains(mimi));
    }

    // === test13AdoptAnimalExceptions (2 Punkte) ===
    @Test
    public void test13AdoptAnimalExceptions() {
        manager.addAnimal(rex);

        // Nicht existierende ID
        assertThrows(IllegalArgumentException.class,
                () -> manager.adoptAnimal("Hund-Fido-1"));

        // Bereits adoptiert
        manager.adoptAnimal("Hund-Rex-3");
        assertThrows(IllegalStateException.class,
                () -> manager.adoptAnimal("Hund-Rex-3"));
    }

    // === test14ReturnAnimal (3 Punkte) ===
    @Test
    public void test14ReturnAnimal() {
        manager.addAnimal(rex);

        manager.adoptAnimal("Hund-Rex-3");
        assertTrue(rex.isAdopted());

        manager.returnAnimal("Hund-Rex-3");
        assertFalse(rex.isAdopted());
        assertTrue(manager.getAvailableAnimals().contains(rex));
        assertFalse(manager.getAdoptedAnimals().contains(rex));
    }

    // === test15ReturnAnimalExceptions (2 Punkte) ===
    @Test
    public void test15ReturnAnimalExceptions() {
        manager.addAnimal(rex);

        // Nicht existierende ID
        assertThrows(IllegalArgumentException.class,
                () -> manager.returnAnimal("Hund-Fido-1"));

        // Nicht adoptiertes Tier zurueckgeben
        assertThrows(IllegalStateException.class,
                () -> manager.returnAnimal("Hund-Rex-3"));
    }

    // === test16GroupBySpecies (4 Punkte) ===
    @Test
    public void test16GroupBySpecies() {
        manager.addAnimal(rex);
        manager.addAnimal(bello);
        manager.addAnimal(mimi);
        manager.addAnimal(luna);

        Map<String, List<Animal>> groups = manager.groupBySpecies();

        // Zwei Gruppen: "Hund" und "Katze"
        assertEquals(2, groups.size());
        assertTrue(groups.containsKey("Hund"));
        assertTrue(groups.containsKey("Katze"));

        // 2 Hunde, 2 Katzen
        assertEquals(2, groups.get("Hund").size());
        assertEquals(2, groups.get("Katze").size());

        // Richtige Tiere in richtigen Gruppen
        assertTrue(groups.get("Hund").contains(rex));
        assertTrue(groups.get("Hund").contains(bello));
        assertTrue(groups.get("Katze").contains(mimi));
        assertTrue(groups.get("Katze").contains(luna));
    }

    // === test17GetAnimalsSortedByFood (3.5 Punkte) ===
    @Test
    public void test17GetAnimalsSortedByFood() {
        manager.addAnimal(rex);     // 0.25
        manager.addAnimal(bello);   // 0.60
        manager.addAnimal(mimi);    // 0.12
        manager.addAnimal(luna);    // 0.20

        List<Animal> sorted = manager.getAnimalsSortedByFood();
        assertEquals(4, sorted.size());

        // Absteigend sortiert: bello(0.60), rex(0.25), luna(0.20), mimi(0.12)
        assertEquals(bello, sorted.get(0));
        assertEquals(rex, sorted.get(1));
        assertEquals(luna, sorted.get(2));
        assertEquals(mimi, sorted.get(3));

        // Nach Adoption: adoptiertes Tier nicht in der Liste
        manager.adoptAnimal("Hund-Bello-1");
        sorted = manager.getAnimalsSortedByFood();
        assertEquals(3, sorted.size());
        assertEquals(rex, sorted.get(0));  // rex(0.25) jetzt groesster
    }

    // === test18GetHungriestAnimal (3 Punkte) ===
    @Test
    public void test18GetHungriestAnimal() {
        // Leeres Tierheim -> empty Optional
        assertTrue(manager.getHungriestAnimal().isEmpty());

        manager.addAnimal(rex);     // 0.25
        manager.addAnimal(mimi);    // 0.12

        assertEquals(rex, manager.getHungriestAnimal().get());

        manager.addAnimal(bello);   // 0.60 -> neuer Hungrigster
        assertEquals(bello, manager.getHungriestAnimal().get());

        // Bello adoptieren -> Rex ist jetzt der Hungrigste
        manager.adoptAnimal("Hund-Bello-1");
        assertEquals(rex, manager.getHungriestAnimal().get());
    }

    // === test19ToString (3 Punkte) ===
    @Test
    public void test19ToString() {
        manager.addAnimal(rex);
        manager.addAnimal(mimi);

        String ls = System.lineSeparator();
        String expected = "Tierheim (2 verfuegbar, 0 adoptiert)" + ls
                + "Verfuegbare Tiere:" + ls
                + "\tHund: Rex (Alter: 3, Gewicht: 10.0kg, Auslauf: 60min/Tag)" + ls
                + "\tKatze: Mimi (Alter: 3, Gewicht: 4.0kg, Indoor: ja, Leben: 9)" + ls
                + "Adoptierte Tiere:" + ls
                + "\t-";

        assertEquals(expected, manager.toString());

        // Nach Adoption
        manager.adoptAnimal("Hund-Rex-3");
        String expectedAdopted = "Tierheim (1 verfuegbar, 1 adoptiert)" + ls
                + "Verfuegbare Tiere:" + ls
                + "\tKatze: Mimi (Alter: 3, Gewicht: 4.0kg, Indoor: ja, Leben: 9)" + ls
                + "Adoptierte Tiere:" + ls
                + "\tHund: Rex (Alter: 3, Gewicht: 10.0kg, Auslauf: 60min/Tag)";

        assertEquals(expectedAdopted, manager.toString());
    }

    // === test20AdoptAndReturnIntegration (5 Punkte) ===
    @Test
    public void test20AdoptAndReturnIntegration() {
        // Grosser Integrationstest: Alles zusammen

        manager.addAnimal(rex);     // Hund, trained, 60min walk
        manager.addAnimal(bello);   // Hund, untrained, 90min walk
        manager.addAnimal(mimi);    // Katze, indoor
        manager.addAnimal(luna);    // Katze, outdoor

        // Anfangszustand
        assertEquals(4, manager.getAnimalCount());
        assertEquals(4, manager.getAvailableCount());
        assertEquals(2, manager.getAllDogs().size());
        assertEquals(2, manager.getAllCats().size());
        assertEquals(60 + 90, manager.calculateTotalWalkMinutes());

        // Rex adoptieren
        manager.adoptAnimal("Hund-Rex-3");
        assertEquals(4, manager.getAnimalCount());        // Gesamt bleibt 4
        assertEquals(3, manager.getAvailableCount());     // Verfuegbar: 3
        assertEquals(90, manager.calculateTotalWalkMinutes()); // Nur noch Bello

        // Polymorphismus: Futter wird korrekt berechnet
        double foodWithoutRex = bello.calculateFoodPerDay()
                + mimi.calculateFoodPerDay()
                + luna.calculateFoodPerDay();
        assertEquals(foodWithoutRex, manager.calculateTotalFoodPerDay(), 0.001);

        // Mimi auch adoptieren
        manager.adoptAnimal("Katze-Mimi-3");
        assertEquals(2, manager.getAvailableCount());
        assertEquals(2, manager.getAdoptedAnimals().size());

        // groupBySpecies() enthaelt ALLE Tiere (auch adoptierte)
        Map<String, List<Animal>> groups = manager.groupBySpecies();
        assertEquals(2, groups.get("Hund").size());   // Rex + Bello
        assertEquals(2, groups.get("Katze").size());  // Mimi + Luna

        // Rex zurueckgeben
        manager.returnAnimal("Hund-Rex-3");
        assertEquals(3, manager.getAvailableCount());
        assertEquals(1, manager.getAdoptedAnimals().size());
        assertEquals(60 + 90, manager.calculateTotalWalkMinutes()); // Beide Hunde wieder da

        // getHungriestAnimal() sollte korrekt sein
        // Verfuegbar: Rex(0.25), Bello(0.60), Luna(0.20) -> Bello
        assertEquals(bello, manager.getHungriestAnimal().get());

        // Sortierte Liste: Bello(0.60), Rex(0.25), Luna(0.20)
        List<Animal> sorted = manager.getAnimalsSortedByFood();
        assertEquals(3, sorted.size());
        assertEquals(bello, sorted.get(0));
        assertEquals(rex, sorted.get(1));
        assertEquals(luna, sorted.get(2));
    }
}
