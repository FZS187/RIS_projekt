# RIS_PROJEKT (Aplikacija za opravljanje nalog)

**Kratek Opis:** Celovita full-stack aplikacija za upravljanje nalog (To-Do List), razvita z arhitekturo mikrostoritev. Aplikacija omogoča operacije **CRUD** (Ustvarjanje, Branje, Posodabljanje, Brisanje), z dodano podporo za **ročne datume** in **filtriranje/iskanje** po ključnih besedah in statusu. Celotna rešitev je kontejnerizirana z uporabo **Docker Compose**.

---

## 📑 Kazalo Vsebine

- [Vizija projekta](#vizija-projekta)
- [Besednjak](#-besednjak-slovar-ključnih-izrazov)
- [Podrobni opisi primerov uporabe](#podrobni-opisi-primerov-uporabe)
- [DPU Diagram](#dpu-diagram)
- [Razredni diagram](#razredni-diagram)
- [Dokumentacija za Razvijalce](#dokumentacija-za-razvijalce-dokumentacija-za-razvijalce)
- [Navodila za Zagon Aplikacije](#navodila-za-zagon-aplikacije-navodila-za-zagon-aplikacije)
- [Funkcionalnost](#funkcionalnost)
- [🧪 Testiranje](#-testiranje)
- [Navodila za Razvijalce](#navodila-za-razvijalce-navodila-za-prispevanjesodelovanj)

---

# Vizija projekta

## Namen aplikacije

V današnjem hitrem svetu imamo vsi ogromno nalog - za fakulteto, službo, gospodinjstvo. Težko je vse zapomniti in pogosto pozabimo pomembne stvari ali zamudimo roke. Naša ToDo aplikacija pomaga ljudem organizirati svoje naloge na enostaven in pregleden način, tako da lahko ostanejo produktivni in brez stresa.

## Problem, ki ga rešujemo

Mnogi ljudje uporabljajo navadne liste na papirju ali preproste aplikacije, ki ne omogočajo naprednega iskanja, filtriranja ali organizacije. Ko se nalog nabere veliko, postane kaotično. Naša aplikacija reši te težave z:

- **Hitrim iskanjem** po nalogah (ko imaš 50+ nalog, to postane ključno)
- **Filtriranjem po statusu** (videti samo dokončane ali nedokončane)
- **Preglednim vmesnikom** - vse na enem mestu
- **Zanesljivim shranjevanjem** - podatki so varno shranjeni v bazi

## Komu je aplikacija namenjena

### Primarna ciljna skupina

- **Študenti** - za organizacijo projektov, izpitov, seminarske naloge, rokov
- **Zaposleni** - za upravljanje delovnih nalog, sestankov, obveznosti
- **Freelancerji** - za sledenje projektom in naročilom

### Sekundarna ciljna skupina

- **Vsakdo**, ki želi biti bolj organiziran v vsakdanjem življenju
- **Ekipe** (v prihodnosti) - skupno upravljanje projektnih nalog

## Kaj želimo doseči

Naš cilj je ustvariti aplikacijo, kjer uporabnik:

- **Hitro doda** novo nalogo (brez zapletenih formularjev)
- **Enostavno označi**, kaj je že končano
- **Išče** po nalogah, tudi če jih ima na stotine
- **Filtrira** - vidi samo tisto, kar ga zanima (dokončane/nedokončane)
- **Uredi ali izbriše** naloge brez težav
- **Nastavi roke** - da ve, kdaj mora kaj narediti (prihodnja funkcionalnost)
- **Organizira po prioritetah** - kaj je nujno, kaj lahko počaka (prihodnja funkcionalnost)

Želimo, da uporabnik **prihrani čas** in **zmanjša stres** pri upravljanju svojih obveznosti.

## Zakaj je naša aplikacija posebna

### Tehnološka prednost

- **Arhitektura mikrostoritev** - profesionalen pristop, kot v resničnih podjetjih
- **Kontejnerizacija z Docker** - enostavna postavitev in skalabilnost
- **Full-stack** - popolna rešitev od baze do uporabniškega vmesnika
- **RESTful API** - omogoča prihodnje razširitve (mobilna aplikacija)

### Funkcionalna prednost

- **Napredno iskanje** - ne le po naslovu, tudi po opisu naloge
- **Dvojno filtriranje** - kombinacija iskanja in statusa
- **Hiter odziv** - optimizirane poizvedbe v bazo
- **Enostaven vmesnik** - vse najpomembnejše na enem zaslonu

## Primerjava z obstoječimi rešitvami

| Naša aplikacija | Papirnati listi | Preproste ToDo app |
| :--- | :--- | :--- |
| Iskanje po ključnih besedah | Ne | Omejeno |
| Filtriranje po statusu | Ne | Da |
| Varno shranjevanje | Ne | Da |
| Urejanje nalog | Ne | Da |
| Skalabilna arhitektura | Ne | Ne |

## Prihodnost projekta

### Kratkoročni cilji (naslednji 3 meseci)

- **Dodajanje rokov** - uporabnik lahko nastavi datum dokončanja
- **Kategorije nalog** - "Fakulteta", "Služba", "Osebno"
- **Prioritete** - označevanje nalog kot "Nujno", "Pomembno", "Lahko počaka"

### Srednjeročni cilji (6-12 mesecev)

- **Mobilna aplikacija** - dostop do nalog kjerkoli
- **Notifikacije** - opomniki za bližajoče se roke
- **Deljenje nalog** - sodelovanje med uporabniki (ekipni projekti)
- **Statistika** - koliko nalog si dokončal ta teden/mesec

### Dolgoročna vizija (1-2 leti)

- **AI priporočila** - predlogi, kdaj narediti katero nalogo
- **Integracije** - Google Calendar, Microsoft To Do
- **Večjezičnost** - podpora za več jezikov
- **Različne vloge uporabnikov** - admin, navaden uporabnik

## Pedagoška vrednost projekta

Ta projekt ni le praktična aplikacija, ampak tudi **učno orodje**. Z njim:

- Učimo se **moderne tehnologije** (React, Spring Boot, Docker)
- Razumemo **arhitekturo mikrostoritev**
- Delamo z **relacijskimi bazami** (MySQL)
- Obvladamo **REST API** komunikacijo
- Vadimo **timsko delo** z Git/GitHub
- Razvijamo **debugging** sposobnosti

## Zaključek

Naša ToDo aplikacija je več kot le seznam nalog - je **celovita rešitev** za organizacijo vsakdanjega življenja. Kombinira **preprostost uporabe** s **profesionalno tehnično izvedbo**.

Medtem ko uporabniku omogočamo, da ostane organiziran in produktiven, se mi kot razvijalci učimo tehnologij in pristopov, ki se uporabljajo v resničnih poslovnih okoljih.

To je projekt, ki **resnično pomaga ljudem** in hkrati **gradi naše znanje** za prihodnjo kariero v IT industriji.

---

## 📖 Besednjak (Slovar Ključnih Izrazov)

V tej tabeli so razloženi ključni izrazi, ki so specifični za funkcionalnosti aplikacije, s čimer se zagotavlja, da bo vsak uporabnik hitro razumel pomen in uporabo.

| Izraz (Termin) | Povezava z Vizijo Projekta | Razlaga v Kontekstu Aplikacije |
| :--- | :--- | :--- |
| **Seznam Nalog (Lista)** | **Namen Aplikacije** (Organizacija) | Osrednji prikaz vseh ustvarjenih opravil, ki jih uporabnik upravlja. |
| **Naloga (Todo)** | **Osnovna Funkcionalnost** (Enota dela) | Posamezen vnos v seznamu, ki vsebuje ime, status in morebiten rok. |
| **Rok (Due Date)** | **Kratkoročni Cilj** (Upravljanje z roki) | Datum, ki določa, kdaj je treba nalogo opraviti, s čimer se zmanjša zamujanje obveznosti. |
| **Status** | **Problem, ki ga Rešujemo** (Filtriranje) | Logična oznaka, ki določa, ali je naloga **Dokončana** (Completed) ali **Nedokončana** (Uncompleted/Active). |
| **Filtriranje** | **Problem, ki ga Rešujemo** (Preglednost) | Funkcija, ki omogoča prikaz nalog samo glede na njihov Status (npr. prikaži samo nedokončane). |
| **Iskanje** | **Problem, ki ga Rešujemo** (Hitro iskanje) | Funkcija, ki omogoča hitro lociranje nalog z uporabo **ključne besede** znotraj imena naloge. |
| **Urejanje** | **Kaj Želimo Doseči** (Sprememba podatkov) | Možnost, da uporabnik vstopi v 'Edit Mode' in popravi ali posodobi Ime in Rok obstoječe naloge. |
| **Preklop Statusa (Toggle)**| **Kaj Želimo Doseči** (Enostavno označevanje) | Hitra akcija (običajno s klikom na Checkbox), ki spremeni Status naloge iz aktivne v dokončano in obratno. |
| **CRUD** | **Urejanje Nalog** (Popoln nadzor) | Akronim za vse temeljne operacije (Ustvari, Beri, Posodobi, Izbriši), ki uporabniku omogočajo, da naloge ureja ali izbriše. |

---

## Podrobni opisi primerov uporabe
📄 [Primeri uporabe - podrobni opisi](./docs/Primeri_uporabe[1].docx)

---

## DPU Diagram

<img width="2230" height="1411" alt="DPU_drugaVerzija drawio" src="https://github.com/user-attachments/assets/6edb39f6-3650-42a5-9458-ff3aafe805b6" />

---

## Razredni diagram

<img width="1148" height="1845" alt="class diagram" src="https://github.com/user-attachments/assets/6d6e585a-c548-4644-9950-de8cce6ca6d1" />

---

## Dokumentacija za Razvijalce (Dokumentacija za Razvijalce)

Ta del je namenjen članom ekipe in bodočim razvijalcem.

### 1.1. Struktura Projekta (Opis Projektne Strukture)

Glavni projekt je razdeljen na dve pod-direktorija (Mikrostoritve):

| Direktorij/Datoteka | Vsebina in Namen |
| :--- | :--- |
| **`todo-backend/`** | Vsebuje vso **Java/Spring Boot** izvorno kodo, Dockerfile in `docker-compose.yml`. |
| **`todo-frontend/`** | Vsebuje vso **React/JavaScript** izvorno kodo (Vite). |
| **`testiranje/`** | Vsebuje **unit teste** in poročilo o testiranju za backend. |
| `README.md` | Glavna dokumentacija in navodila. |
| `docker-compose.yml` | **Glavna konfiguracijska datoteka za Docker**, določa storitve (backend, MySQL) in omrežje. |

### 1.3. Orodja, Okvirji in Različice (Informacije o Uporabljenih Orodjih, Frameworkih in Različicah)

- **Jezik (Zaledje):** **Java 21**
- **Okvir (Zaledje):** **Spring Boot 3.x** (s Spring Data JPA in REST)
- **Jezik (Sprednji del):** **JavaScript/JSX**
- **Okvir (Sprednji del):** **React 18** (z Vite)
- **Podatkovna Baza:** **MySQL 8.0**
- **Kontejnerizacija:** **Docker** in **Docker Compose**
- **Upravljanje Odvisnosti:** **Maven (za Java)** in **npm (za Node/React)**
- **Testing:** **JUnit 5** in **Mockito**

### 1.3. Standardi Kodiranja (Standardi Kodiranja)

- Uporabljamo standardne **CamelCase** konvencije za Java razrede in metode (`TodoController`).
- Uporabljamo **PascalCase** za React komponente (`TodoForm`, `FilterForm`).
- Uporabljena je struktura **Controller -> Service -> Repository** za ločevanje poslovne logike.
- Za formatiranje kode uporabljamo avtomatska orodja **IntelliJ IDEA/VS Code** (privzete nastavitve), da bi bila koda čitljiva.

---

## Navodila za Zagon Aplikacije (Navodila za Zagon Aplikacije)

Aplikacija je kontejnerizirana in se zažene z uporabo Docker Compose.

### Predpogoji

Pred zagonom aplikacije se prepričajte, da imate nameščeno naslednje:

- **Git**
- **Docker Desktop** (ki vključuje Docker in Docker Compose)
- **Node.js in npm** (za zagon sprednjega dela)
- **Maven** (za build in testiranje)

### Koraki za Razvijalce (Z Testiranjem)

1. **Kloniranje Repozitorija:**

    ```bash
    git clone https://github.com/PetarKojadinovic/RIS_projekt.git
    cd RIS_PROJEKT
    ```

2. **Zagon Unit Testov (Priporočeno):**
    Pred zagonom aplikacije priporočamo zagon testov za preverjanje funkcionalnosti:

    ```bash
    cd todo-backend
    mvn test
    ```
    
    *(Testi bi morali vsi uspešno preteči)*

3. **Zagon Zalednega Sklada (MySQL in Spring Boot):**
    Ta korak prevede Java kodo, ustvari Docker slike in zažene storitvi MySQL in Spring Boot na vratih **3307** in **8080**.

    ```bash
    docker compose up --build
    ```

    *(Pustite ta terminal odprt)*

4. **Zagon Sprednjega Dela (React):**
    Odprite nov terminal in zaženite sprednji del.
    
    ```bash
    cd ../todo-frontend
    npm install
    npm run dev
    ```
    
    Aplikacija bi se zdaj morala samodejno odpreti v brskalniku na naslovu **`http://localhost:5173`**.

---

## Funkcionalnost

Aplikacija podpira naslednje funkcionalnosti:

- **Ustvarjanje:** Dodajanje naloge z imenom in izbirnim rokom.
- **Branje:** Prikaz seznama vseh nalog.
- **Posodabljanje (Urejanje):** Popolno urejanje imena in roka obstoječe naloge.
- **Brisanje:** Odstranjevanje naloge iz baze.
- **Sprememba Statusa:** Označevanje naloge kot dokončane/nedokončane.
- **NAPREDNO FILTRIRANJE:**
    - Iskanje po ključni besedi (v imenu naloge).
    - Filtriranje po statusu (Vse, Dokončane, Nedokončane).

---

## 🧪 Testiranje

Projekt vključuje obsežno enoto testiranja (unit testing) za `UserService` komponento. Vsi testi so organizirani v ločeni mapi **`testiranje/`**.

### Struktura Testiranja

```
RIS_PROJEKT/
├── testiranje/
│   ├── porocilo_testiranja.md          # Podrobno poročilo o testiranju
│   ├── UserServiceTest.java            # Testi za registracijo (Boris Sajlović)
│   ├── UserServiceSearchTest.java      # Testi za iskanje (Petar Kojadinović)
│   └── PasswordValidationTest.java     # Testi za validacijo gesla (Filip Sekulović)
└── todo-backend/
    └── src/test/java/com/example/todobackend/service/
        ├── UserServiceTest.java
        ├── UserServiceSearchTest.java
        └── PasswordValidationTest.java
```

### Pregled Testov

Implementirali smo **6 unit testov**, ki pokrivajo ključne funkcionalnosti `UserService`:

| Test # | Član | Funkcionalnost | Tip |
|:-------|:-----|:---------------|:----|
| **Test 1** | Boris Sajlović | Uspešna registracija uporabnika | Pozitiven ✅ |
| **Test 2** | Boris Sajlović | Registracija z obstoječim emailom | Negativen ❌ |
| **Test 3** | Petar Kojadinović | Uspešno iskanje uporabnika po emailu | Pozitiven ✅ |
| **Test 4** | Petar Kojadinović | Iskanje neobstoječega uporabnika | Negativen ❌ |
| **Test 5** | Filip Sekulović | Šifriranje kompleksnega gesla | Pozitiven ✅ |
| **Test 6** | Filip Sekulović | Registracija s praznim geslom | Negativen ❌ |

### Člani Ekipe in Odgovornosti

| Član | Odgovornost | Testna Datoteka |
|:-----|:------------|:----------------|
| **Boris Sajlović** | Testiranje registracije uporabnikov | `UserServiceTest.java` |
| **Petar Kojadinović** | Testiranje iskanja uporabnikov | `UserServiceSearchTest.java` |
| **Filip Sekulović** | Testiranje validacije in šifriranja gesel | `PasswordValidationTest.java` |

### Pokritost Testov

Testi pokrivajo naslednje funkcionalnosti:

- ✅ **Registracija uporabnikov** - Validacija emaila in šifriranje gesla
- ✅ **Iskanje uporabnikov** - Iskanje po email naslovu in obravnava neobstoječih uporabnikov
- ✅ **Validacija gesel** - Šifriranje kompleksnih gesel in odkrivanje varnostnih pomanjkljivosti
- ✅ **Obravnava napak** - Preverjanje izjem in mejnih primerov

### Uporabljene Tehnologije za Testiranje

- **JUnit 5** - Testing framework za Java
- **Mockito** - Mocking framework za izolacijo odvisnosti
- **Spring Boot Test** - Integracija s Spring Boot okoljem
- **Maven Surefire Plugin** - Izvajanje testov pri build procesu

### Zagon Testov

Za zagon vseh testov uporabite naslednji ukaz:

```bash
cd todo-backend
mvn test
```

Za zagon specifičnega testa:

```bash
mvn test -Dtest=UserServiceTest
mvn test -Dtest=UserServiceSearchTest
mvn test -Dtest=PasswordValidationTest
```

Za generiranje poročila o code coverage:

```bash
mvn jacoco:report
```

### Rezultati Testiranja

**Statistika:**
- **Skupno testov:** 6
- **Uspešnih testov:** 6 (100%)
- **Neuspešnih testov:** 0 (0%)
- **Code coverage:** ~75% (UserService)
- **Trajanje testov:** ~0.8s

Podrobno poročilo o testiranju, vključno z:
- Opisom vsakega testa
- Odkritimi napakami in varnostnimi pomanjkljivostmi
- Predlogi za izboljšave
- Analizo uspešnosti testov

je na voljo v dokumentu: **[`testiranje/porocilo_testiranja.md`](./testiranje/porocilo_testiranja.md)**

### Odkrite Pomanjkljivosti

Med testiranjem smo odkrili naslednje varnostne pomanjkljivosti:

⚠️ **KRITIČNA VARNOSTNA POMANJKLJIVOST** (odkril Filip Sekulović, Test 6):

**Problem:** Sistem trenutno dovoli registracijo uporabnika s popolnoma **praznim geslom**, kar je resna varnostna ranljivost.

**Vpliv:**
- Kdorkoli lahko ustvari račun brez gesla
- Račun je popolnoma nezaščiten
- Ni minimalne dolžine gesla

**Predlog rešitve:**
```java
// Dodati validacijo v UserService.register():
if (password == null || password.trim().isEmpty()) {
    throw new IllegalArgumentException("Password cannot be empty");
}

if (password.length() < 8) {
    throw new IllegalArgumentException("Password must be at least 8 characters long");
}
```

**Status:** ⏳ V TEKU (Prioriteta: VISOKA)

**Odgovoren za odpravo:** Filip Sekulović

---

## Navodila za Razvijalce (Navodila za Prispevanje/Sodelovanje)

Naslednji koraki se nanašajo na Git potek dela znotraj vaše ekipe.

1. **Prenos Najnovejših Sprememb:**

    ```bash
    git pull origin main
    ```

2. **Ustvarjanje Nove Veje (Branch):**

    ```bash
    git checkout -b feature/ime-funkcionalnosti
    ```

3. **Pred Commit-om: Zagon Testov**

    ```bash
    cd todo-backend
    mvn test
    ```
    
    *(Prepričajte se, da vsi testi uspešno pretečejo)*

4. **Commits in Potisk (Push):**

    ```bash
    git add .
    git commit -m "feat: Dodana validacija uporabniškega vnosa"
    git push origin feature/ime-funkcionalnosti
    ```

5. **Ustvarjanje Pull Requesta (PR):**
    - Na GitHubu ustvarite **Pull Request** (Zahtevek za združitev) iz vaše veje (`feature/...`) v glavno vejo (`main`).
    - Počakajte, da **vsaj en član ekipe/asistent pregleda (review)** vašo kodo in odobri združitev.
    - Prepričajte se, da so vsi testi uspešno pretekli pred združitvijo.

### Best Practices za Testiranje

- **Vedno zaženite teste pred commit-om** - `mvn test`
- **Pišite teste za novo funkcionalnost** - pokrijte tako pozitivne kot negativne scenarije
- **Vzdržujte visoko code coverage** - cilj je vsaj 80%
- **Dokumentirajte teste** - vsak test naj ima jasen opis namena
- **Poročajte o odkritih napakah** - posodobite `testiranje/porocilo_testiranja.md`

---

# RIS_PROJEKT (Aplikacija za opravljanje nalog)

**Kratek Opis:** Celovita full-stack aplikacija za upravljanje nalog (To-Do List), razvita z arhitekturo mikrostoritev. Aplikacija omogoča operacije **CRUD** (Ustvarjanje, Branje, Posodabljanje, Brisanje), z dodano podporo za **ročne datume** in **filtriranje/iskanje** po ključnih besedah in statusu. Celotna rešitev je kontejnerizirana z uporabo **Docker Compose**.

---

## 📑 Kazalo Vsebine

- [Vizija projekta](#vizija-projekta)
- [Besednjak](#-besednjak-slovar-ključnih-izrazov)
- [Podrobni opisi primerov uporabe](#podrobni-opisi-primerov-uporabe)
- [DPU Diagram](#dpu-diagram)
- [Razredni diagram](#razredni-diagram)
- [Dokumentacija za Razvijalce](#dokumentacija-za-razvijalce-dokumentacija-za-razvijalce)
- [Navodila za Zagon Aplikacije](#navodila-za-zagon-aplikacije-navodila-za-zagon-aplikacije)
- [Funkcionalnost](#funkcionalnost)
- [🧪 Testiranje](#-testiranje)
- [Navodila za Razvijalce](#navodila-za-razvijalce-navodila-za-prispevanjesodelovanj)

---

# Vizija projekta

## Namen aplikacije

V današnjem hitrem svetu imamo vsi ogromno nalog - za fakulteto, službo, gospodinjstvo. Težko je vse zapomniti in pogosto pozabimo pomembne stvari ali zamudimo roke. Naša ToDo aplikacija pomaga ljudem organizirati svoje naloge na enostaven in pregleden način, tako da lahko ostanejo produktivni in brez stresa.

## Problem, ki ga rešujemo

Mnogi ljudje uporabljajo navadne liste na papirju ali preproste aplikacije, ki ne omogočajo naprednega iskanja, filtriranja ali organizacije. Ko se nalog nabere veliko, postane kaotično. Naša aplikacija reši te težave z:

- **Hitrim iskanjem** po nalogah (ko imaš 50+ nalog, to postane ključno)
- **Filtriranjem po statusu** (videti samo dokončane ali nedokončane)
- **Preglednim vmesnikom** - vse na enem mestu
- **Zanesljivim shranjevanjem** - podatki so varno shranjeni v bazi

## Komu je aplikacija namenjena

### Primarna ciljna skupina

- **Študenti** - za organizacijo projektov, izpitov, seminarske naloge, rokov
- **Zaposleni** - za upravljanje delovnih nalog, sestankov, obveznosti
- **Freelancerji** - za sledenje projektom in naročilom

### Sekundarna ciljna skupina

- **Vsakdo**, ki želi biti bolj organiziran v vsakdanjem življenju
- **Ekipe** (v prihodnosti) - skupno upravljanje projektnih nalog

## Kaj želimo doseči

Naš cilj je ustvariti aplikacijo, kjer uporabnik:

- **Hitro doda** novo nalogo (brez zapletenih formularjev)
- **Enostavno označi**, kaj je že končano
- **Išče** po nalogah, tudi če jih ima na stotine
- **Filtrira** - vidi samo tisto, kar ga zanima (dokončane/nedokončane)
- **Uredi ali izbriše** naloge brez težav
- **Nastavi roke** - da ve, kdaj mora kaj narediti (prihodnja funkcionalnost)
- **Organizira po prioritetah** - kaj je nujno, kaj lahko počaka (prihodnja funkcionalnost)

Želimo, da uporabnik **prihrani čas** in **zmanjša stres** pri upravljanju svojih obveznosti.

## Zakaj je naša aplikacija posebna

### Tehnološka prednost

- **Arhitektura mikrostoritev** - profesionalen pristop, kot v resničnih podjetjih
- **Kontejnerizacija z Docker** - enostavna postavitev in skalabilnost
- **Full-stack** - popolna rešitev od baze do uporabniškega vmesnika
- **RESTful API** - omogoča prihodnje razširitve (mobilna aplikacija)

### Funkcionalna prednost

- **Napredno iskanje** - ne le po naslovu, tudi po opisu naloge
- **Dvojno filtriranje** - kombinacija iskanja in statusa
- **Hiter odziv** - optimizirane poizvedbe v bazo
- **Enostaven vmesnik** - vse najpomembnejše na enem zaslonu

## Primerjava z obstoječimi rešitvami

| Naša aplikacija | Papirnati listi | Preproste ToDo app |
| :--- | :--- | :--- |
| Iskanje po ključnih besedah | Ne | Omejeno |
| Filtriranje po statusu | Ne | Da |
| Varno shranjevanje | Ne | Da |
| Urejanje nalog | Ne | Da |
| Skalabilna arhitektura | Ne | Ne |

## Prihodnost projekta

### Kratkoročni cilji (naslednji 3 meseci)

- **Dodajanje rokov** - uporabnik lahko nastavi datum dokončanja
- **Kategorije nalog** - "Fakulteta", "Služba", "Osebno"
- **Prioritete** - označevanje nalog kot "Nujno", "Pomembno", "Lahko počaka"

### Srednjeročni cilji (6-12 mesecev)

- **Mobilna aplikacija** - dostop do nalog kjerkoli
- **Notifikacije** - opomniki za bližajoče se roke
- **Deljenje nalog** - sodelovanje med uporabniki (ekipni projekti)
- **Statistika** - koliko nalog si dokončal ta teden/mesec

### Dolgoročna vizija (1-2 leti)

- **AI priporočila** - predlogi, kdaj narediti katero nalogo
- **Integracije** - Google Calendar, Microsoft To Do
- **Večjezičnost** - podpora za več jezikov
- **Različne vloge uporabnikov** - admin, navaden uporabnik

## Pedagoška vrednost projekta

Ta projekt ni le praktična aplikacija, ampak tudi **učno orodje**. Z njim:

- Učimo se **moderne tehnologije** (React, Spring Boot, Docker)
- Razumemo **arhitekturo mikrostoritev**
- Delamo z **relacijskimi bazami** (MySQL)
- Obvladamo **REST API** komunikacijo
- Vadimo **timsko delo** z Git/GitHub
- Razvijamo **debugging** sposobnosti

## Zaključek

Naša ToDo aplikacija je več kot le seznam nalog - je **celovita rešitev** za organizacijo vsakdanjega življenja. Kombinira **preprostost uporabe** s **profesionalno tehnično izvedbo**.

Medtem ko uporabniku omogočamo, da ostane organiziran in produktiven, se mi kot razvijalci učimo tehnologij in pristopov, ki se uporabljajo v resničnih poslovnih okoljih.

To je projekt, ki **resnično pomaga ljudem** in hkrati **gradi naše znanje** za prihodnjo kariero v IT industriji.

---

## 📖 Besednjak (Slovar Ključnih Izrazov)

V tej tabeli so razloženi ključni izrazi, ki so specifični za funkcionalnosti aplikacije, s čimer se zagotavlja, da bo vsak uporabnik hitro razumel pomen in uporabo.

| Izraz (Termin) | Povezava z Vizijo Projekta | Razlaga v Kontekstu Aplikacije |
| :--- | :--- | :--- |
| **Seznam Nalog (Lista)** | **Namen Aplikacije** (Organizacija) | Osrednji prikaz vseh ustvarjenih opravil, ki jih uporabnik upravlja. |
| **Naloga (Todo)** | **Osnovna Funkcionalnost** (Enota dela) | Posamezen vnos v seznamu, ki vsebuje ime, status in morebiten rok. |
| **Rok (Due Date)** | **Kratkoročni Cilj** (Upravljanje z roki) | Datum, ki določa, kdaj je treba nalogo opraviti, s čimer se zmanjša zamujanje obveznosti. |
| **Status** | **Problem, ki ga Rešujemo** (Filtriranje) | Logična oznaka, ki določa, ali je naloga **Dokončana** (Completed) ali **Nedokončana** (Uncompleted/Active). |
| **Filtriranje** | **Problem, ki ga Rešujemo** (Preglednost) | Funkcija, ki omogoča prikaz nalog samo glede na njihov Status (npr. prikaži samo nedokončane). |
| **Iskanje** | **Problem, ki ga Rešujemo** (Hitro iskanje) | Funkcija, ki omogoča hitro lociranje nalog z uporabo **ključne besede** znotraj imena naloge. |
| **Urejanje** | **Kaj Želimo Doseči** (Sprememba podatkov) | Možnost, da uporabnik vstopi v 'Edit Mode' in popravi ali posodobi Ime in Rok obstoječe naloge. |
| **Preklop Statusa (Toggle)**| **Kaj Želimo Doseči** (Enostavno označevanje) | Hitra akcija (običajno s klikom na Checkbox), ki spremeni Status naloge iz aktivne v dokončano in obratno. |
| **CRUD** | **Urejanje Nalog** (Popoln nadzor) | Akronim za vse temeljne operacije (Ustvari, Beri, Posodobi, Izbriši), ki uporabniku omogočajo, da naloge ureja ali izbriše. |

---

## Podrobni opisi primerov uporabe
📄 [Primeri uporabe - podrobni opisi](./docs/Primeri_uporabe[1].docx)

---

## DPU Diagram

<img width="2230" height="1411" alt="DPU_drugaVerzija drawio" src="https://github.com/user-attachments/assets/6edb39f6-3650-42a5-9458-ff3aafe805b6" />

---

## Razredni diagram

<img width="1148" height="1845" alt="class diagram" src="https://github.com/user-attachments/assets/6d6e585a-c548-4644-9950-de8cce6ca6d1" />

---

## Dokumentacija za Razvijalce (Dokumentacija za Razvijalce)

Ta del je namenjen članom ekipe in bodočim razvijalcem.

### 1.1. Struktura Projekta (Opis Projektne Strukture)

Glavni projekt je razdeljen na dve pod-direktorija (Mikrostoritve):

| Direktorij/Datoteka | Vsebina in Namen |
| :--- | :--- |
| **`todo-backend/`** | Vsebuje vso **Java/Spring Boot** izvorno kodo, Dockerfile in `docker-compose.yml`. |
| **`todo-frontend/`** | Vsebuje vso **React/JavaScript** izvorno kodo (Vite). |
| **`testiranje/`** | Vsebuje **unit teste** in poročilo o testiranju za backend. |
| `README.md` | Glavna dokumentacija in navodila. |
| `docker-compose.yml` | **Glavna konfiguracijska datoteka za Docker**, določa storitve (backend, MySQL) in omrežje. |

### 1.3. Orodja, Okvirji in Različice (Informacije o Uporabljenih Orodjih, Frameworkih in Različicah)

- **Jezik (Zaledje):** **Java 21**
- **Okvir (Zaledje):** **Spring Boot 3.x** (s Spring Data JPA in REST)
- **Jezik (Sprednji del):** **JavaScript/JSX**
- **Okvir (Sprednji del):** **React 18** (z Vite)
- **Podatkovna Baza:** **MySQL 8.0**
- **Kontejnerizacija:** **Docker** in **Docker Compose**
- **Upravljanje Odvisnosti:** **Maven (za Java)** in **npm (za Node/React)**
- **Testing:** **JUnit 5** in **Mockito**

### 1.3. Standardi Kodiranja (Standardi Kodiranja)

- Uporabljamo standardne **CamelCase** konvencije za Java razrede in metode (`TodoController`).
- Uporabljamo **PascalCase** za React komponente (`TodoForm`, `FilterForm`).
- Uporabljena je struktura **Controller -> Service -> Repository** za ločevanje poslovne logike.
- Za formatiranje kode uporabljamo avtomatska orodja **IntelliJ IDEA/VS Code** (privzete nastavitve), da bi bila koda čitljiva.

---

## Navodila za Zagon Aplikacije (Navodila za Zagon Aplikacije)

Aplikacija je kontejnerizirana in se zažene z uporabo Docker Compose.

### Predpogoji

Pred zagonom aplikacije se prepričajte, da imate nameščeno naslednje:

- **Git**
- **Docker Desktop** (ki vključuje Docker in Docker Compose)
- **Node.js in npm** (za zagon sprednjega dela)
- **Maven** (za build in testiranje)

### Koraki za Razvijalce (Z Testiranjem)

1. **Kloniranje Repozitorija:**

    ```bash
    git clone https://github.com/PetarKojadinovic/RIS_projekt.git
    cd RIS_PROJEKT
    ```

2. **Zagon Unit Testov (Priporočeno):**
    Pred zagonom aplikacije priporočamo zagon testov za preverjanje funkcionalnosti:

    ```bash
    cd todo-backend
    mvn test
    ```
    
    *(Testi bi morali vsi uspešno preteči)*

3. **Zagon Zalednega Sklada (MySQL in Spring Boot):**
    Ta korak prevede Java kodo, ustvari Docker slike in zažene storitvi MySQL in Spring Boot na vratih **3307** in **8080**.

    ```bash
    docker compose up --build
    ```

    *(Pustite ta terminal odprt)*

4. **Zagon Sprednjega Dela (React):**
    Odprite nov terminal in zaženite sprednji del.
    
    ```bash
    cd ../todo-frontend
    npm install
    npm run dev
    ```
    
    Aplikacija bi se zdaj morala samodejno odpreti v brskalniku na naslovu **`http://localhost:5173`**.

---

## Funkcionalnost

Aplikacija podpira naslednje funkcionalnosti:

- **Ustvarjanje:** Dodajanje naloge z imenom in izbirnim rokom.
- **Branje:** Prikaz seznama vseh nalog.
- **Posodabljanje (Urejanje):** Popolno urejanje imena in roka obstoječe naloge.
- **Brisanje:** Odstranjevanje naloge iz baze.
- **Sprememba Statusa:** Označevanje naloge kot dokončane/nedokončane.
- **NAPREDNO FILTRIRANJE:**
    - Iskanje po ključni besedi (v imenu naloge).
    - Filtriranje po statusu (Vse, Dokončane, Nedokončane).

---

## 🧪 Testiranje

Projekt vključuje obsežno enoto testiranja (unit testing) za `UserService` komponento. Vsi testi so organizirani v ločeni mapi **`testiranje/`**.

### Struktura Testiranja

```
RIS_PROJEKT/
├── testiranje/
│   ├── porocilo_testiranja.md          # Podrobno poročilo o testiranju
│   ├── UserServiceTest.java            # Testi za registracijo (Boris Sajlović)
│   ├── UserServiceSearchTest.java      # Testi za iskanje (Petar Kojadinović)
│   └── PasswordValidationTest.java     # Testi za validacijo gesla (Filip Sekulović)
└── todo-backend/
    └── src/test/java/com/example/todobackend/service/
        ├── UserServiceTest.java
        ├── UserServiceSearchTest.java
        └── PasswordValidationTest.java
```

### Pregled Testov

Implementirali smo **6 unit testov**, ki pokrivajo ključne funkcionalnosti `UserService`:

| Test # | Član | Funkcionalnost | Tip |
|:-------|:-----|:---------------|:----|
| **Test 1** | Boris Sajlović | Uspešna registracija uporabnika | Pozitiven ✅ |
| **Test 2** | Boris Sajlović | Registracija z obstoječim emailom | Negativen ❌ |
| **Test 3** | Petar Kojadinović | Uspešno iskanje uporabnika po emailu | Pozitiven ✅ |
| **Test 4** | Petar Kojadinović | Iskanje neobstoječega uporabnika | Negativen ❌ |
| **Test 5** | Filip Sekulović | Šifriranje kompleksnega gesla | Pozitiven ✅ |
| **Test 6** | Filip Sekulović | Registracija s praznim geslom | Negativen ❌ |

### Člani Ekipe in Odgovornosti

| Član | Odgovornost | Testna Datoteka |
|:-----|:------------|:----------------|
| **Boris Sajlović** | Testiranje registracije uporabnikov | `UserServiceTest.java` |
| **Petar Kojadinović** | Testiranje iskanja uporabnikov | `UserServiceSearchTest.java` |
| **Filip Sekulović** | Testiranje validacije in šifriranja gesel | `PasswordValidationTest.java` |

### Pokritost Testov

Testi pokrivajo naslednje funkcionalnosti:

- ✅ **Registracija uporabnikov** - Validacija emaila in šifriranje gesla
- ✅ **Iskanje uporabnikov** - Iskanje po email naslovu in obravnava neobstoječih uporabnikov
- ✅ **Validacija gesel** - Šifriranje kompleksnih gesel in odkrivanje varnostnih pomanjkljivosti
- ✅ **Obravnava napak** - Preverjanje izjem in mejnih primerov

### Uporabljene Tehnologije za Testiranje

- **JUnit 5** - Testing framework za Java
- **Mockito** - Mocking framework za izolacijo odvisnosti
- **Spring Boot Test** - Integracija s Spring Boot okoljem
- **Maven Surefire Plugin** - Izvajanje testov pri build procesu

### Zagon Testov

Za zagon vseh testov uporabite naslednji ukaz:

```bash
cd todo-backend
mvn test
```

Za zagon specifičnega testa:

```bash
mvn test -Dtest=UserServiceTest
mvn test -Dtest=UserServiceSearchTest
mvn test -Dtest=PasswordValidationTest
```

Za generiranje poročila o code coverage:

```bash
mvn jacoco:report
```

### Rezultati Testiranja

**Statistika:**
- **Skupno testov:** 6
- **Uspešnih testov:** 6 (100%)
- **Neuspešnih testov:** 0 (0%)
- **Code coverage:** ~75% (UserService)
- **Trajanje testov:** ~0.8s

Podrobno poročilo o testiranju, vključno z:
- Opisom vsakega testa
- Odkritimi napakami in varnostnimi pomanjkljivostmi
- Predlogi za izboljšave
- Analizo uspešnosti testov

je na voljo v dokumentu: **[`testiranje/porocilo_testiranja.md`](./testiranje/porocilo_testiranja.md)**

### Odkrite Pomanjkljivosti

Med testiranjem smo odkrili naslednje varnostne pomanjkljivosti:

⚠️ **KRITIČNA VARNOSTNA POMANJKLJIVOST** (odkril Filip Sekulović, Test 6):

**Problem:** Sistem trenutno dovoli registracijo uporabnika s popolnoma **praznim geslom**, kar je resna varnostna ranljivost.

**Vpliv:**
- Kdorkoli lahko ustvari račun brez gesla
- Račun je popolnoma nezaščiten
- Ni minimalne dolžine gesla

**Predlog rešitve:**
```java
// Dodati validacijo v UserService.register():
if (password == null || password.trim().isEmpty()) {
    throw new IllegalArgumentException("Password cannot be empty");
}

if (password.length() < 8) {
    throw new IllegalArgumentException("Password must be at least 8 characters long");
}
```

**Status:** ⏳ V TEKU (Prioriteta: VISOKA)

**Odgovoren za odpravo:** Filip Sekulović

---

## Navodila za Razvijalce (Navodila za Prispevanje/Sodelovanje)

Naslednji koraki se nanašajo na Git potek dela znotraj vaše ekipe.

1. **Prenos Najnovejših Sprememb:**

    ```bash
    git pull origin main
    ```

2. **Ustvarjanje Nove Veje (Branch):**

    ```bash
    git checkout -b feature/ime-funkcionalnosti
    ```

3. **Pred Commit-om: Zagon Testov**

    ```bash
    cd todo-backend
    mvn test
    ```
    
    *(Prepričajte se, da vsi testi uspešno pretečejo)*

4. **Commits in Potisk (Push):**

    ```bash
    git add .
    git commit -m "feat: Dodana validacija uporabniškega vnosa"
    git push origin feature/ime-funkcionalnosti
    ```

5. **Ustvarjanje Pull Requesta (PR):**
    - Na GitHubu ustvarite **Pull Request** (Zahtevek za združitev) iz vaše veje (`feature/...`) v glavno vejo (`main`).
    - Počakajte, da **vsaj en član ekipe/asistent pregleda (review)** vašo kodo in odobri združitev.
    - Prepričajte se, da so vsi testi uspešno pretekli pred združitvijo.

### Best Practices za Testiranje

- **Vedno zaženite teste pred commit-om** - `mvn test`
- **Pišite teste za novo funkcionalnost** - pokrijte tako pozitivne kot negativne scenarije
- **Vzdržujte visoko code coverage** - cilj je vsaj 80%
- **Dokumentirajte teste** - vsak test naj ima jasen opis namena
- **Poročajte o odkritih napakah** - posodobite `testiranje/porocilo_testiranja.md`

---

