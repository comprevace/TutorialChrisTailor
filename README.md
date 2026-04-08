# S.H.E.L.T.E.R. - Java Vererbungs-Tutorial

**Safe Haven for Every Living Thing, Emergency Rescue**

> Klausurvorbereitung fuer Java Vererbung (Abstrakte Klassen & Kindklassen)
> Aufgebaut im Stil echter Uni-Klausuren | 90 Punkte | 46 JUnit-Tests | IntelliJ-ready

---

## Was ist das?

Ein vollstaendiges, klausurnahes Tutorial zum Thema **Vererbung in Java**. Du baust ein Tierheim-Verwaltungssystem, in dem verschiedene Tierarten (`Dog`, `Cat`) von einer abstrakten Klasse `Animal` erben -- und lernst dabei alle Vererbungsmechanismen, die Java bietet.

```
            +-------------------+
            |  abstract Animal  |   <- Aufgabe 1 (15P)
            |  name, age, weight|
            |  getSpeciesName() |   (abstrakt)
            |  calculateFood()  |   (abstrakt)
            |  getAnimalId()    |   (final!)
            +--------+----------+
                     |
          +----------+----------+
          |                     |
  +-------+-------+    +-------+-------+
  |      Dog      |    |      Cat      |   <- Aufgabe 2+3 (je 12P)
  |  walkMinutes  |    |    indoor     |
  |   trained     |    | livesRemaining|
  +---------------+    +---------------+
          |                     |
          +----------+----------+
                     |
          +----------+----------+
          |   ShelterManager    |   <- Aufgabe 4 (51P)
          |  Polymorphismus     |
          |  instanceof + Cast  |
          |  Collections        |
          +---------------------+
```

## Abgedeckte Konzepte

| # | Konzept | Wo gelernt |
|---|---------|------------|
| 1 | `abstract` Klassen & Methoden | Animal.java |
| 2 | `extends` (Vererbung herstellen) | Dog.java, Cat.java |
| 3 | `super()` Konstruktor-Verkettung | Dog.java, Cat.java |
| 4 | `@Override` | Dog.java, Cat.java |
| 5 | `protected` Zugriffsmodifikator | Animal.java |
| 6 | `final` Methoden | Animal.getAnimalId() |
| 7 | Polymorphismus | ShelterManager.java |
| 8 | `instanceof` + Type Casting | ShelterManager.java |
| 9 | Compile-Time vs Runtime Typ | Tutorial Kapitel 11 |
| 10 | Upcasting vs Downcasting | Tutorial Kapitel 12 |
| 11 | Overriding vs Overloading | Tutorial Kapitel 13 |
| 12 | Object als Wurzelklasse | Tutorial Kapitel 14 |

## Projektstruktur

```
TutorialChrisTailor/
│
├── Tutorial_Vererbung.md             Theorie (14 Kapitel) + Aufgabenstellung + Loesungen
├── Spickzettel_DIN_A4.md             Kompakt-Referenz fuer 1 DIN-A4-Blatt
│
├── src/main/java/.../shelter/        LOESUNG mit ausfuehrlichen Kommentaren
│   ├── Animal.java                     Abstrakte Elternklasse
│   ├── Dog.java                        Kindklasse #1
│   ├── Cat.java                        Kindklasse #2
│   └── ShelterManager.java             Verwaltungsklasse (Polymorphismus!)
│
├── src/test/java/.../shelter/        46 JUNIT-TESTS
│   ├── Test01Animal.java               10 Tests (inkl. Reflection fuer abstract/final)
│   ├── Test02Dog.java                  8 Tests (inkl. super()-Validierung)
│   ├── Test03Cat.java                  8 Tests (inkl. Polymorphismus-Beweis)
│   └── Test04ShelterManager.java       20 Tests (inkl. Integrationstest)
│
├── skeleton/                         UEBUNGSPROJEKT (leere Skelette + gleiche Tests)
│   ├── src/main/java/.../shelter/      Dateien mit TODOs statt Loesungen
│   └── src/test/java/.../shelter/      Gleiche 46 Tests zum Selbsttesten
│
└── *.pdf                             4 Altklausuren als Referenz
```

## Setup: Alles von Null installieren

Falls du auf einem frischen Rechner startest -- hier ist alles Schritt fuer Schritt.

### 1. Git installieren

Git wird benoetigt um das Repo herunterzuladen.

**Windows:**
```powershell
# Option A: Ueber winget (Windows 10/11, empfohlen)
winget install Git.Git

# Option B: Manuell herunterladen
# https://git-scm.com/download/win -> Installer ausfuehren
```

**Mac:**
```bash
# Ueber Homebrew
brew install git

# Oder: Xcode Command Line Tools (wird automatisch gefragt beim ersten git-Aufruf)
xcode-select --install
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update && sudo apt install git
```

Nach der Installation Terminal/PowerShell **neu oeffnen** und pruefen:
```bash
git --version
# -> git version 2.x.x
```

### 2. Java 21 (JDK) installieren

**Windows:**
```powershell
winget install EclipseAdoptium.Temurin.21.JDK
```

**Mac:**
```bash
brew install --cask temurin@21
```

**Linux:**
```bash
sudo apt install temurin-21-jdk
# Oder: https://adoptium.net/de/ -> Installer herunterladen
```

Pruefen:
```bash
java --version
# -> openjdk 21.x.x
```

### 3. IntelliJ IDEA installieren

**Alle Plattformen:**
- Gehe zu https://www.jetbrains.com/idea/download/
- **Community Edition** (kostenlos) reicht voellig aus
- Installer ausfuehren, Standardeinstellungen beibehalten

Oder per winget (Windows):
```powershell
winget install JetBrains.IntelliJIDEA.Community
```

### 4. Dieses Tutorial herunterladen

```bash
# Terminal / PowerShell oeffnen und ausfuehren:
git clone https://github.com/comprevace/TutorialChrisTailor.git
```

Das erstellt einen Ordner `TutorialChrisTailor/` mit dem kompletten Projekt.

### 5. In IntelliJ oeffnen

1. IntelliJ starten
2. **File -> Open**
3. Zum Ordner `TutorialChrisTailor/skeleton/` navigieren (zum Ueben) oder `TutorialChrisTailor/` (fuer die Loesung)
4. **"Open as Project"** klicken
5. Gradle-Import bestaetigen wenn gefragt (IntelliJ erkennt `build.gradle` automatisch)
6. Warten bis IntelliJ alles indexiert hat (Fortschrittsbalken unten rechts)

### 6. Tests ausfuehren

**In IntelliJ:**
- Rechtsklick auf `src/test/java` -> **"Run All Tests"**
- Oder: Einzelne Testklasse oeffnen -> Gruener Play-Button neben der Klasse

**Im Terminal:**
```bash
cd TutorialChrisTailor
./gradlew test          # Mac/Linux
gradlew.bat test        # Windows
```

---

## Quick Start (fuer Eilige)

```bash
# Alles in 4 Befehlen (Windows mit winget):
winget install Git.Git
winget install EclipseAdoptium.Temurin.21.JDK
winget install JetBrains.IntelliJIDEA.Community
git clone https://github.com/comprevace/TutorialChrisTailor.git

# Dann: IntelliJ -> File -> Open -> skeleton/ Ordner -> Open as Project
```

## Testergebnis

```
Test01Animal > test01ConstructorValid()         PASSED
Test01Animal > test02ConstructorExceptions()     PASSED
Test01Animal > test03Getters()                   PASSED
Test01Animal > test04AdoptAndReturn()            PASSED
Test01Animal > test05AdoptExceptions()           PASSED
Test01Animal > test06GetAnimalIdFinal()          PASSED
Test01Animal > test07Equals()                    PASSED
Test01Animal > test08HashCode()                  PASSED
Test01Animal > test09ToString()                  PASSED
Test01Animal > test10AbstractMethods()           PASSED
Test02Dog > test01-08                            PASSED (8/8)
Test03Cat > test01-08                            PASSED (8/8)
Test04ShelterManager > test01-20                 PASSED (20/20)

BUILD SUCCESSFUL -- 46/46 tests passed
```

## Punkteverteilung

| Aufgabe | Klasse | Punkte | Konzept-Fokus |
|---------|--------|--------|---------------|
| 1 | Animal (abstract) | 15 | abstract, protected, final, equals |
| 2 | Dog | 12 | extends, super(), @Override |
| 3 | Cat | 12 | Geschwisterklasse, andere Implementierung |
| 4 | ShelterManager | 51 | Polymorphismus, instanceof, Collections |
| **Gesamt** | | **90** | |

## Lernplan (2 Tage)

**Tag 1:**
1. `Tutorial_Vererbung.md` lesen (Teil 1: Theorie, alle 14 Kapitel)
2. `skeleton/` in IntelliJ oeffnen
3. Aufgaben 1-3 selbst implementieren, Tests laufen lassen
4. Bei Problemen: Loesung in `src/main/java/` vergleichen

**Tag 2:**
1. Aufgabe 4 (ShelterManager) implementieren
2. `Spickzettel_DIN_A4.md` ausdrucken und ggf. anpassen
3. Alles nochmal von Null implementieren (Geschwindigkeit ueben)
4. Fokus: instanceof-Pattern, toString()-Formatierung, Exception-Reihenfolge

---

*Erstellt mit Claude Code zur Klausurvorbereitung.*
