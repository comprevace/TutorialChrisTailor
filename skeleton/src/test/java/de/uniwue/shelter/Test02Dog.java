package de.uniwue.shelter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests fuer Aufgabe 2: Dog (12 Punkte)
 */
public class Test02Dog {

    // === test01Constructor (1.5 Punkte) ===
    @Test
    public void test01Constructor() {
        Dog dog = new Dog("Rex", 3, 10.0, 60, true);
        assertNotNull(dog);
        assertEquals("Rex", dog.getName());
        assertEquals(3, dog.getAge());
        assertEquals(10.0, dog.getWeight());
        assertEquals(60, dog.getWalkMinutesPerDay());
        assertTrue(dog.isTrained());
        assertFalse(dog.isAdopted());

        // Untrainierter Hund
        Dog dog2 = new Dog("Bello", 1, 5.0, 30, false);
        assertFalse(dog2.isTrained());

        // walkMinutesPerDay <= 0 -> IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> new Dog("Rex", 3, 10.0, 0, true));
        assertThrows(IllegalArgumentException.class,
                () -> new Dog("Rex", 3, 10.0, -10, true));
    }

    // === test02ConstructorCallsSuper (1.5 Punkte) ===
    @Test
    public void test02ConstructorCallsSuper() {
        // Validierungen aus Animal-Konstruktor muessen auch fuer Dog gelten!
        // Das beweist, dass super() korrekt aufgerufen wird.

        // null Name -> NullPointerException (aus Animal-Konstruktor)
        assertThrows(NullPointerException.class,
                () -> new Dog(null, 3, 10.0, 60, true));

        // Leerer Name -> IllegalArgumentException (aus Animal-Konstruktor)
        assertThrows(IllegalArgumentException.class,
                () -> new Dog("", 3, 10.0, 60, true));

        // Negatives Alter -> IllegalArgumentException (aus Animal-Konstruktor)
        assertThrows(IllegalArgumentException.class,
                () -> new Dog("Rex", -1, 10.0, 60, true));

        // Gewicht <= 0 -> IllegalArgumentException (aus Animal-Konstruktor)
        assertThrows(IllegalArgumentException.class,
                () -> new Dog("Rex", 3, 0, 60, true));
    }

    // === test03GetSpeciesName (1 Punkt) ===
    @Test
    public void test03GetSpeciesName() {
        Dog dog = new Dog("Rex", 3, 10.0, 60, true);
        assertEquals("Hund", dog.getSpeciesName());
    }

    // === test04CalculateFoodPerDay (2 Punkte) ===
    @Test
    public void test04CalculateFoodPerDay() {
        // Trainierter Hund: weight * 0.025 * 1.0
        Dog trained = new Dog("Rex", 3, 10.0, 60, true);
        assertEquals(10.0 * 0.025 * 1.0, trained.calculateFoodPerDay(), 0.001);

        // Untrainierter Hund: weight * 0.025 * 1.2
        Dog untrained = new Dog("Bello", 1, 20.0, 30, false);
        assertEquals(20.0 * 0.025 * 1.2, untrained.calculateFoodPerDay(), 0.001);

        // Nach Training aendert sich der Futterbedarf
        untrained.train();
        assertEquals(20.0 * 0.025 * 1.0, untrained.calculateFoodPerDay(), 0.001);
    }

    // === test05GettersAndTrain (2 Punkte) ===
    @Test
    public void test05GettersAndTrain() {
        Dog dog = new Dog("Rex", 3, 10.0, 60, false);

        assertEquals(60, dog.getWalkMinutesPerDay());
        assertFalse(dog.isTrained());

        // Trainieren
        dog.train();
        assertTrue(dog.isTrained());

        // Geerbte Methoden testen (zeigt dass Vererbung funktioniert)
        assertFalse(dog.isAdopted());
        dog.adopt();
        assertTrue(dog.isAdopted());
        dog.returnToShelter();
        assertFalse(dog.isAdopted());
    }

    // === test06TrainException (1 Punkt) ===
    @Test
    public void test06TrainException() {
        // Bereits trainierter Hund
        Dog trained = new Dog("Rex", 3, 10.0, 60, true);
        assertThrows(IllegalStateException.class, () -> trained.train());

        // Hund trainieren, dann nochmal -> Exception
        Dog untrained = new Dog("Bello", 1, 5.0, 30, false);
        untrained.train();
        assertThrows(IllegalStateException.class, () -> untrained.train());
    }

    // === test07ToString (1.5 Punkte) ===
    @Test
    public void test07ToString() {
        Dog dog = new Dog("Rex", 3, 10.0, 60, true);
        assertEquals("Hund: Rex (Alter: 3, Gewicht: 10.0kg, Auslauf: 60min/Tag)",
                dog.toString());

        Dog dog2 = new Dog("Bello", 1, 5.5, 30, false);
        assertEquals("Hund: Bello (Alter: 1, Gewicht: 5.5kg, Auslauf: 30min/Tag)",
                dog2.toString());
    }

    // === test08EqualsWithAnimal (1.5 Punkte) ===
    @Test
    public void test08EqualsWithAnimal() {
        Dog dog = new Dog("Rex", 3, 10.0, 60, true);
        Cat cat = new Cat("Rex", 3, 4.0, true);

        // Gleicher Name, aber verschiedene Art -> NICHT gleich!
        // Das funktioniert, weil equals() getSpeciesName() vergleicht
        assertNotEquals(dog, cat);
        assertNotEquals(cat, dog);

        // Zwei Hunde mit gleichem Namen -> gleich (unabhaengig von Alter etc.)
        Dog dog2 = new Dog("Rex", 5, 20.0, 120, false);
        assertEquals(dog, dog2);

        // getAnimalId() nutzt getSpeciesName() -> verschiedene IDs
        assertNotEquals(dog.getAnimalId(), cat.getAnimalId());
        assertEquals("Hund-Rex-3", dog.getAnimalId());
        assertEquals("Katze-Rex-3", cat.getAnimalId());
    }
}
