# Poročilo o Testiranju - RIS Projekt

**Datum:** 14. december 2024  
**Projekt:** Todo Aplikacija (RIS_PROJEKT)  
**Komponenta:** UserService Unit Testi

---

## 📋 Pregled

Ta dokument vsebuje podrobno poročilo o unit testiranju `UserService` komponente v našem Todo Backend sistemu. Testiranje je bilo izvedeno s ciljem zagotavljanja zanesljivosti, varnosti in pravilnega delovanja funkcionalnosti uporabniške registracije, iskanja in validacije gesel.

---

## 👥 Člani Ekipe in Odgovornosti

| Član | Odgovornost | Testi |
|:-----|:------------|:------|
| **Boris Sajlović** | Testiranje registracije uporabnikov | Test 1, Test 2 |
| **Petar Kojadinović** | Testiranje iskanja uporabnikov | Test 3, Test 4 |
| **Filip Sekulović** | Testiranje validacije in šifriranja gesel | Test 5, Test 6 |

---

## 🧪 Opis Testov

### ČLAN 1: Boris Sajlović - Registracija Uporabnikov

#### **Test 1 - POZITIVEN: Uspešna Registracija Novega Uporabnika**

**Datoteka:** `UserServiceTest.java`

**Kaj test preizkuša:**
- Uspešno registracijo novega uporabnika z veljavnim email naslovom
- Pravilno šifriranje gesla pred shranjevanjem v bazo
- Klice vseh potrebnih repository in encoder metod

**Zakaj je pomemben:**
- Zagotavlja, da osnovna funkcionalnost registracije deluje pravilno
- Preverja, da se gesla nikoli ne shranjujejo v plain text obliki
- Potrjuje, da sistem pravilno komunicira z bazo podatkov

**Scenarij:**
1. Nov uporabnik se poskuša registrirati z emailom "newuser@example.com"
2. Email še ne obstaja v bazi
3. Geslo se šifrira z PasswordEncoder
4. Uporabnik se shrani v bazo

**Pričakovan rezultat:** ✅ Uporabnik uspešno registriran, geslo šifrirano

---

#### **Test 2 - NEGATIVEN: Registracija z Obstoječim Emailom**

**Datoteka:** `UserServiceTest.java`

**Kaj test preizkuša:**
- Preprečevanje dupliciranih email naslovov
- Pravilno vrženje izjeme `IllegalArgumentException`
- Da sistem NE šifrira gesla in NE shrani uporabnika če email že obstaja

**Zakaj je pomemben:**
- Zagotavlja unikatnost email naslovov v sistemu
- Preprečuje napake pri avtentikaciji
- Varuje integriteto podatkov v bazi

**Scenarij:**
1. Uporabnik se poskuša registrirati z emailom "existing@example.com"
2. Email že obstaja v bazi
3. Sistem zavrne registracijo
4. Vrže `IllegalArgumentException` s sporočilom "Email already exists"

**Pričakovan rezultat:** ✅ Izjema pravilno vržena, uporabnik NI shranjen

---

### ČLAN 2: Petar Kojadinović - Iskanje Uporabnikov

#### **Test 3 - POZITIVEN: Uspešno Iskanje Uporabnika po Emailu**

**Datoteka:** `UserServiceSearchTest.java`

**Kaj test preizkuša:**
- Iskanje obstoječega uporabnika po email naslovu
- Pravilno vračanje `Optional<User>` objekta
- Ujemanje ID in email podatkov

**Zakaj je pomemben:**
- Zagotavlja, da funkcionalnost prijave/avtentikacije deluje
- Potrjuje, da se podatki pravilno pridobivajo iz baze
- Testira scenarij uspešnega iskanja (happy path)

**Scenarij:**
1. Kličemo `findByEmail()` z emailom "test@example.com"
2. Uporabnik obstaja v bazi
3. Repository vrne `Optional` z uporabnikom

**Pričakovan rezultat:** ✅ Optional vsebuje pravilnega uporabnika

---

#### **Test 4 - NEGATIVEN: Iskanje Neobstoječega Uporabnika**

**Datoteka:** `UserServiceSearchTest.java`

**Kaj test preizkuša:**
- Obravnava primera, ko uporabnik ne obstaja
- Pravilno vračanje praznega `Optional` objekta
- Da NE pride do napak ali izjem

**Zakaj je pomemben:**
- Zagotavlja, da sistem elegantno obravnava neobstoječe uporabnike
- Preprečuje NullPointerException napake
- Testira mejni primer (edge case)

**Scenarij:**
1. Kličemo `findByEmail()` z emailom "nonexistent@example.com"
2. Uporabnik NE obstaja v bazi
3. Repository vrne prazen `Optional`

**Pričakovan rezultat:** ✅ Prazen Optional, brez napak

---

### ČLAN 3: Filip Sekulović - Validacija in Šifriranje Gesel

#### **Test 5 - POZITIVEN: Šifriranje Dolge Kompleksne Lozinke**

**Datoteka:** `PasswordValidationTest.java`

**Kaj test preizkuša:**
- Šifriranje dolge (30+ znakov) kompleksne lozinke
- Obravnava posebnih znakov (!@#$%^&*)
- Da se geslo NE shrani v plain text obliki

**Zakaj je pomemben:**
- Zagotavlja varnost uporabniških računov
- Potrjuje, da sistem podpira močna gesla
- Testira robustnost šifrirnega algoritma

**Scenarij:**
1. Uporabnik se registrira z geslom "VeryL0ng&C0mpl3x!P@ssw0rd#2024$%^"
2. Geslo vsebuje 34 znakov in posebne simbole
3. PasswordEncoder šifrira geslo
4. Uporabnik se shrani z šifriranim geslom

**Pričakovan rezultat:** ✅ Kompleksno geslo pravilno šifrirano

---

#### **Test 6 - NEGATIVEN: Registracija s Praznim Geslom**

**Datoteka:** `PasswordValidationTest.java`

**Kaj test preizkuša:**
- Obnaša sistema pri praznem geslu
- Ali sistem še vedno šifrira prazno geslo (varnostna pomanjkljivost)
- Ali se uporabnik lahko registrira brez gesla

**Zakaj je pomemben:**
- **ODKRIVA VARNOSTNO POMANJKLJIVOST** v sistemu
- Identificira potrebo po dodatni validaciji
- Dokumentira obstoječe obnašanje za prihodnje izboljšave

**Scenarij:**
1. Uporabnik se poskuša registrirati s praznim geslom ""
2. Sistem še vedno procesuira registracijo
3. Prazno geslo se šifrira
4. Uporabnik je registriran (kar je problematično)

**Pričakovan rezultat:** ⚠️ Uporabnik registriran s praznim geslom (VARNOSTNA POMANJKLJIVOST ODKRITA!)

---

## 📊 Analiza Uspešnosti Testov

### ✅ Uspešni Testi (5/6)

Vsi testi so tehnično prešli uspešno, kar pomeni, da:
- Koda deluje tako, kot je implementirana
- Ni syntax napak ali runtime izjem
- Vsi mock objekti delujejo pravilno
- Assert stavki so vsi zadovoljeni

### 🔍 Odkrite Pomanjkljivosti

#### **Kritična Varnostna Pomanjkljivost - Prazna Gesla**

**Odkril:** Filip Sekulović (Test 6)

**Problem:**
- Sistem trenutno dovoli registracijo uporabnika s **popolnoma praznim geslom**
- To je **kritična varnostna pomanjkljivost**, saj:
  - Kdorkoli lahko ustvari račun brez gesla
  - Račun je popolnoma nezaščiten
  - Ni minimalne dolžine gesla

**Lokacija napake:**
- `UserService.register()` metoda nima validacije gesla pred šifriranjem

**Kako smo jo odkrili:**
```java
@Test
void testRegisterWithEmptyPassword_ShouldEncodeButNotRecommended() {
    String emptyPassword = "";
    User result = userService.register("weak@example.com", emptyPassword);
    
    // Test pokaže, da je registracija uspela!
    assertNotNull(result); // ✅ PASSED (ampak to je PROBLEM!)
}
```

**Predlog rešitve:**
```java
public User register(String email, String password) {
    // DODAJ TO VALIDACIJO:
    if (password == null || password.trim().isEmpty()) {
        throw new IllegalArgumentException("Password cannot be empty");
    }
    
    if (password.length() < 8) {
        throw new IllegalArgumentException("Password must be at least 8 characters long");
    }
    
    // Obstoječa koda...
    if (userRepository.existsByEmail(email)) {
        throw new IllegalArgumentException("Email already exists");
    }
    
    // Ostalo...
}
```

---

### 🛠️ Kako Smo Odpravili Napake

**Status:** ⏳ V TEKU

**Načrt odprave:**

1. **Faza 1 - Dodajanje Validacije (Prioriteta: VISOKA)**
   - Dodati validacijo dolžine gesla (min. 8 znakov)
   - Dodati preverjanje za prazna gesla
   - Dodati testi za nove validacije

2. **Faza 2 - Dodatne Varnostne Izboljšave (Prioriteta: SREDNJA)**
   - Zahtevati vsaj eno veliko črko
   - Zahtevati vsaj eno številko
   - Zahtevati vsaj en poseben znak

3. **Faza 3 - Dokumentacija (Prioriteta: NIZKA)**
   - Posodobiti API dokumentacijo z zahtevami za gesla
   - Dodati uporabniška sporočila o zahtevah za gesla

**Odgovoren:** Filip Sekulović  
**Rok:** Do naslednje iteracije

---

## 📈 Statistika Testiranja

| Metrika | Vrednost |
|:--------|:---------|
| **Skupno testov** | 6 |
| **Uspešnih testov** | 6 (100%) |
| **Neuspešnih testov** | 0 (0%) |
| **Odkritih pomanjkljivosti** | 1 (kritična) |
| **Code coverage** | ~75% (UserService) |
| **Trajanje testov** | ~0.8s |

---

## 🎯 Zaključek

### Kaj smo dosegli:

✅ **Implementirali 6 celovitih unit testov** za UserService  
✅ **100% uspešnost testov** - vsa koda deluje kot implementirana  
✅ **Odkrili kritično varnostno pomanjkljivost** - prazna gesla  
✅ **Pokritost ~75%** UserService funkcionalnosti  
✅ **Dokumentirali vse teste** s podrobnimi opisi  

### Kaj je potrebno izboljšati:

⚠️ **Dodati validacijo gesel** (kritično)  
⚠️ **Povečati code coverage** na 90%+  
⚠️ **Dodati integracijske teste** z resničo bazo  
⚠️ **Implementirati teste za AuthController**  

### Naslednji koraki:

1. Implementacija validacije gesel (Filip)
2. Dodajanje testov za nove validacije
3. Code review in merge v main branch
4. Integracija testov v CI/CD pipeline

---

## 🔧 Tehnične Informacije

**Testing Framework:** JUnit 5  
**Mocking Framework:** Mockito  
**Build Tool:** Maven  
**Spring Boot:** 3.x  
**Java Version:** 21  

**Ukaz za zagon testov:**
```bash
mvn test
```

**Ukaz za poročilo o pokritosti:**
```bash
mvn jacoco:report
```
