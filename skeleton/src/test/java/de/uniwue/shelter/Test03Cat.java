package de.uniwue.shelter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests fuer Aufgabe 3: Cat (12 Punkte)
 */
public class Test03Cat {

    // === test01Constructor (1.5 Punkte) ===
    @Test
    public void test01Constructor() {
        Cat cat = new Cat("Mimi", 3, 4.0, true);
        assertNotNull(cat);
        assertEquals("Mimi", cat.getName());
        assertEquals(3, cat.getAge());
        assertEquals(4.0, cat.getWeight());
        assertTrue(cat.isIndoor());
        assertEquals(9, cat.getLivesRemaining());
        assertFalse(cat.isAdopted());

        // Outdoor-Katze
        Cat outdoor = new Cat("Luna", 5, 5.0, false);
        assertFalse(outdoor.isIndoor());
        assertEquals(9, outdoor.getLivesRemaining());
    }

    // === test02ConstructorCallsSuper (1.5 Punkte) ===
    @Test
    public void test02ConstructorCallsSuper() {
        // Gleiche Validierungen wie bei Dog -- beweist super()-Aufruf

        assertThrows(NullPointerException.class,
                () -> new Cat(null, 3, 4.0, true));

        assertThrows(IllegalArgumentException.class,
                () -> new Cat("", 3, 4.0, true));

        assertThrows(IllegalArgumentException.class,
                () -> new Cat("Mimi", -1, 4.0, true));

        assertThrows(IllegalArgumentException.class,
                () -> new Cat("Mimi", 3, 0, true));
    }

    // === test03GetSpeciesName (1 Punkt) ===
    @Test
    public void test03GetSpeciesName() {
        Cat cat = new Cat("Mimi", 3, 4.0, true);
        assertEquals("Katze", cat.getSpeciesName());
    }

    // === test04CalculateFoodPerDay (2 Punkte) ===
    @Test
    public void test04CalculateFoodPerDay() {
        // Indoor-Katze: weight * 0.03
        Cat indoor = new Cat("Mimi", 3, 4.0, true);
        assertEquals(4.0 * 0.03, indoor.calculateFoodPerDay(), 0.001);

        // Outdoor-Katze: weight * 0.04
        Cat outdoor = new Cat("Luna", 5, 5.0, false);
        assertEquals(5.0 * 0.04, outdoor.calculateFoodPerDay(), 0.001);

        // Schwerere Katze -> mehr Futter
        Cat heavy = new Cat("Garfield", 8, 10.0, true);
        assertEquals(10.0 * 0.03, heavy.calculateFoodPerDay(), 0.001);
    }

    // === test05GettersAndLoseLife (2 Punkte) ===
    @Test
    public void test05GettersAndLoseLife() {
        Cat cat = new Cat("Mimi", 3, 4.0, true);

        assertEquals(9, cat.getLivesRemaining());
        assertTrue(cat.isIndoor());

        // Leben verlieren
        cat.loseLife();
        assertEquals(8, cat.getLivesRemaining());

        cat.loseLife();
        assertEquals(7, cat.getLivesRemaining());

        // Geerbte Methoden testen
        assertFalse(cat.isAdopted());
        cat.adopt();
        assertTrue(cat.isAdopted());
    }

    // === test06LoseLifeException (1 Punkt) ===
    @Test
    public void test06LoseLifeException() {
        Cat cat = new Cat("Mimi", 3, 4.0, true);

        // 9 Leben verlieren
        for (int i = 0; i < 9; i++) {
            cat.loseLife();
        }
        assertEquals(0, cat.getLivesRemaining());

        // 10. Mal -> IllegalStateException
        assertThrows(IllegalStateException.class, () -> cat.loseLife());
    }

    // === test07ToString (1.5 Punkte) ===
    @Test
    public void test07ToString() {
        Cat indoor = new Cat("Mimi", 3, 4.0, true);
        assertEquals("Katze: Mimi (Alter: 3, Gewicht: 4.0kg, Indoor: ja, Leben: 9)",
                indoor.toString());

        Cat outdoor = new Cat("Luna", 5, 5.5, false);
        assertEquals("Katze: Luna (Alter: 5, Gewicht: 5.5kg, Indoor: nein, Leben: 9)",
                outdoor.toString());

        // Nach Leben verlieren
        indoor.loseLife();
        assertEquals("Katze: Mimi (Alter: 3, Gewicht: 4.0kg, Indoor: ja, Leben: 8)",
                indoor.toString());
    }

    // === test08PolymorphicBehavior (1.5 Punkte) ===
    @Test
    public void test08PolymorphicBehavior() {
        // POLYMORPHISMUS-TEST:
        // Wir erstellen eine Cat, speichern sie aber als Animal-Referenz.
        // Trotzdem muessen die Cat-spezifischen Implementierungen aufgerufen werden!

        Animal animal = new Cat("Mimi", 3, 4.0, true);  // Animal-Referenz, Cat-Objekt

        // getSpeciesName() muss "Katze" zurueckgeben, nicht irgendwas anderes
        assertEquals("Katze", animal.getSpeciesName());

        // calculateFoodPerDay() muss Cat-Formel nutzen (weight * 0.03)
        assertEquals(4.0 * 0.03, animal.calculateFoodPerDay(), 0.001);

        // toString() muss Cat.toString() sein
        assertEquals("Katze: Mimi (Alter: 3, Gewicht: 4.0kg, Indoor: ja, Leben: 9)",
                animal.toString());

        // getAnimalId() nutzt getSpeciesName() -> "Katze-Mimi-3"
        assertEquals("Katze-Mimi-3", animal.getAnimalId());

        // animal.isIndoor() wuerde NICHT kompilieren! (Animal kennt isIndoor() nicht)
        // Man braeuchte einen Cast: ((Cat) animal).isIndoor()
        assertTrue(((Cat) animal).isIndoor());
    }
}
