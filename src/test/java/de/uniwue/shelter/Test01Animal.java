package de.uniwue.shelter;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests fuer Aufgabe 1: Animal (15 Punkte)
 *
 * Da Animal abstrakt ist, nutzen wir fuer die Tests eine anonyme Unterklasse
 * oder Dog/Cat. Das ist ein gaengiges Muster in der Klausur!
 */
public class Test01Animal {

    // Hilfsmethode: Erstellt ein testbares Animal ueber eine anonyme Unterklasse
    private Animal createTestAnimal(String name, int age, double weight) {
        return new Animal(name, age, weight) {
            @Override
            public String getSpeciesName() {
                return "TestTier";
            }

            @Override
            public double calculateFoodPerDay() {
                return 1.0;
            }
        };
    }

    // === test01ConstructorValid (1 Punkt) ===
    @Test
    public void test01ConstructorValid() {
        Animal animal = createTestAnimal("Rex", 3, 10.0);
        assertNotNull(animal);
        assertEquals("Rex", animal.getName());
        assertEquals(3, animal.getAge());
        assertEquals(10.0, animal.getWeight());
        assertFalse(animal.isAdopted());
    }

    // === test02ConstructorExceptions (1.5 Punkte) ===
    @Test
    public void test02ConstructorExceptions() {
        // null Name -> NullPointerException
        assertThrows(NullPointerException.class,
                () -> createTestAnimal(null, 3, 10.0));

        // Leerer Name -> IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> createTestAnimal("", 3, 10.0));

        // Negatives Alter -> IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> createTestAnimal("Rex", -1, 10.0));

        // Gewicht <= 0 -> IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> createTestAnimal("Rex", 3, 0));
        assertThrows(IllegalArgumentException.class,
                () -> createTestAnimal("Rex", 3, -5.0));

        // Alter 0 ist erlaubt (Welpe!)
        assertDoesNotThrow(() -> createTestAnimal("Rex", 0, 10.0));
    }

    // === test03Getters (1 Punkt) ===
    @Test
    public void test03Getters() {
        Animal animal = createTestAnimal("Mimi", 5, 4.5);
        assertEquals("Mimi", animal.getName());
        assertEquals(5, animal.getAge());
        assertEquals(4.5, animal.getWeight());
        assertFalse(animal.isAdopted());
    }

    // === test04AdoptAndReturn (2 Punkte) ===
    @Test
    public void test04AdoptAndReturn() {
        Animal animal = createTestAnimal("Rex", 3, 10.0);

        // Anfang: nicht adoptiert
        assertFalse(animal.isAdopted());

        // Adoptieren
        animal.adopt();
        assertTrue(animal.isAdopted());

        // Zurueckgeben
        animal.returnToShelter();
        assertFalse(animal.isAdopted());

        // Nochmal adoptieren (soll funktionieren)
        animal.adopt();
        assertTrue(animal.isAdopted());
    }

    // === test05AdoptExceptions (1.5 Punkte) ===
    @Test
    public void test05AdoptExceptions() {
        Animal animal = createTestAnimal("Rex", 3, 10.0);

        // Nicht adoptiertes Tier zurueckgeben -> IllegalStateException
        assertThrows(IllegalStateException.class, () -> animal.returnToShelter());

        // Adoptieren
        animal.adopt();

        // Bereits adoptiertes Tier nochmal adoptieren -> IllegalStateException
        assertThrows(IllegalStateException.class, () -> animal.adopt());

        // Zurueckgeben
        animal.returnToShelter();

        // Nicht adoptiertes Tier nochmal zurueckgeben -> IllegalStateException
        assertThrows(IllegalStateException.class, () -> animal.returnToShelter());
    }

    // === test06GetAnimalIdFinal (2 Punkte) ===
    @Test
    public void test06GetAnimalIdFinal() {
        Animal animal = createTestAnimal("Rex", 3, 10.0);

        // ID Format pruefen
        assertEquals("TestTier-Rex-3", animal.getAnimalId());

        // Pruefen dass getAnimalId() final ist (per Reflection)
        try {
            Method method = Animal.class.getDeclaredMethod("getAnimalId");
            assertTrue(Modifier.isFinal(method.getModifiers()),
                    "getAnimalId() muss final sein!");
        } catch (NoSuchMethodException e) {
            fail("Methode getAnimalId() nicht gefunden");
        }

        // Mit echten Kindklassen testen
        Dog dog = new Dog("Bello", 5, 15.0, 60, true);
        assertEquals("Hund-Bello-5", dog.getAnimalId());

        Cat cat = new Cat("Luna", 2, 3.5, true);
        assertEquals("Katze-Luna-2", cat.getAnimalId());
    }

    // === test07Equals (2 Punkte) ===
    @Test
    public void test07Equals() {
        Dog dog1 = new Dog("Rex", 3, 10.0, 60, true);
        Dog dog2 = new Dog("Rex", 5, 20.0, 30, false);  // Gleicher Name, anderes Alter/Gewicht

        // Gleicher Name + gleiche Art -> equals
        assertEquals(dog1, dog2);

        // Gleicher Name, ANDERE Art -> NICHT equals
        Cat cat = new Cat("Rex", 3, 4.0, true);
        assertNotEquals(dog1, cat);  // Hund "Rex" != Katze "Rex"

        // Verschiedene Namen -> NICHT equals
        Dog dog3 = new Dog("Bello", 3, 10.0, 60, true);
        assertNotEquals(dog1, dog3);

        // null und andere Klasse
        assertNotEquals(dog1, null);
        assertNotEquals(dog1, "Rex");

        // Reflexiv
        assertEquals(dog1, dog1);
    }

    // === test08HashCode (1 Punkt) ===
    @Test
    public void test08HashCode() {
        Dog dog1 = new Dog("Rex", 3, 10.0, 60, true);
        Dog dog2 = new Dog("Rex", 5, 20.0, 30, false);

        // Gleiche Objekte (laut equals) muessen gleichen hashCode haben
        assertEquals(dog1.hashCode(), dog2.hashCode());

        // Verschiedene Art, gleicher Name -> wahrscheinlich anderer hashCode
        Cat cat = new Cat("Rex", 3, 4.0, true);
        // hashCode darf sich unterscheiden (muss aber nicht)
        // Wir pruefen nur den Vertrag: equals -> gleicher hashCode
    }

    // === test09ToString (1.5 Punkte) ===
    @Test
    public void test09ToString() {
        Animal animal = createTestAnimal("Rex", 3, 10.0);
        assertEquals("TestTier: Rex (Alter: 3, Gewicht: 10.0kg)", animal.toString());
    }

    // === test10AbstractMethods (1.5 Punkte) ===
    @Test
    public void test10AbstractMethods() {
        // Pruefen dass Animal abstrakt ist
        assertTrue(Modifier.isAbstract(Animal.class.getModifiers()),
                "Animal muss eine abstrakte Klasse sein!");

        // Pruefen dass getSpeciesName() abstrakt ist
        try {
            Method method = Animal.class.getDeclaredMethod("getSpeciesName");
            assertTrue(Modifier.isAbstract(method.getModifiers()),
                    "getSpeciesName() muss abstrakt sein!");
        } catch (NoSuchMethodException e) {
            fail("Methode getSpeciesName() nicht gefunden");
        }

        // Pruefen dass calculateFoodPerDay() abstrakt ist
        try {
            Method method = Animal.class.getDeclaredMethod("calculateFoodPerDay");
            assertTrue(Modifier.isAbstract(method.getModifiers()),
                    "calculateFoodPerDay() muss abstrakt sein!");
        } catch (NoSuchMethodException e) {
            fail("Methode calculateFoodPerDay() nicht gefunden");
        }
    }
}
